package io.parrotsoftware.qatest.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.parrotsoftware.qatest.common.base.BaseViewModel
import io.parrotsoftware.qatest.data.repositories.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
   private val userRepository: UserRepository
) : BaseViewModel() {

    private val _emailMLD = MutableLiveData("android-challenge@parrotsoftware.io")
    val email: LiveData<String> = _emailMLD

    private val _passwordMLD = MutableLiveData("8mngDhoPcB3ckV7X")
    val password: LiveData<String> = _passwordMLD

    private val _viewStateMLD = MutableLiveData<LoginViewState>()
    val viewState: LiveData<LoginViewState> = _viewStateMLD

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {
        viewModelScope.launch {
            val response = userRepository.userExists()
            if (response.isError) {
                _viewStateMLD.value = LoginViewState.LoginError
                return@launch
            }

            if (response.requiredResult) {
                _viewStateMLD.value = LoginViewState.LoginSuccess
            }
        }
    }

    fun onLoginPortraitClicked() {
        viewModelScope.launch {
            val response = userRepository.login(email.value!!, password.value!!)
            if (response.isError) {
                _viewStateMLD.value = LoginViewState.LoginError
            } else {
                _viewStateMLD.value = LoginViewState.LoginSuccess
            }
        }
    }

    fun navigated() {
        _viewStateMLD.value = LoginViewState.Idle
    }

    /*private val viewState = MutableLiveData<LoginViewState>()
    fun getViewState() = viewState

    val email = MutableLiveData("android-challenge@parrotsoftware.io")
    val password = MutableLiveData("8mngDhoPcB3ckV7X")

    fun initView() {
        viewModelScope.launch {
            val response = userRepository.userExists()
            if (response.isError) {
                viewState.value = LoginViewState.LoginError
                return@launch
            }

            if (response.requiredResult) {
                viewState.value = LoginViewState.LoginSuccess
            }
        }
    }

    fun onLoginPortraitClicked() {
        viewModelScope.launch {
            val response = userRepository.login(email.value!!, password.value!!)
            if (response.isError) {
                viewState.value = LoginViewState.LoginError
            } else {
                viewState.value = LoginViewState.LoginSuccess
            }
        }
    }

    fun navigated() {
        viewState.value = LoginViewState.Idle
    }*/
}