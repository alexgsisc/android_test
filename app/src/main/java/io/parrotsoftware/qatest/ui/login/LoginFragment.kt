package io.parrotsoftware.qatest.ui.login


import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import io.parrotsoftware.qatest.common.base.BaseFragment
import io.parrotsoftware.qatest.common.base.deletagate.viewBinding
import io.parrotsoftware.qatest.common.observe
import io.parrotsoftware.qatest.common.toast
import io.parrotsoftware.qatest.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(),OnClickListener {

    override val binding: FragmentLoginBinding by viewBinding {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    override val viewModel: LoginViewModel by viewModels()

    override fun initView() {
        super.initView()
        viewModel.onStart()
        binding.btnLogin.setOnClickListener(this)
    }

    override fun viewModelsObserve() {
        viewModel.apply {
            observe(email, ::setEmail)
            observe(password, ::setPassword)
            observe(viewState, ::onViewState)
        }
    }

    private fun setEmail(value: String) {
        binding.editEmail.setText(value)
    }

    private fun setPassword(value: String) {
        binding.editPassword.setText(value)
    }

    private fun onViewState(state: LoginViewState?) {
        when (state) {
            LoginViewState.LoginError -> {
                requireContext().toast("Error al iniciar sesión")
            }

            LoginViewState.LoginSuccess -> {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToListFragment()
                )
                viewModel.navigated()
            }

            else -> {}
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.btnLogin.id -> {
                viewModel.onLoginPortraitClicked()
            }
        }
    }

    /* private lateinit var binding: FragmentLoginBinding
   private var viewModel: LoginViewModel by viewModels()

   override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View {
       binding = FragmentLoginBinding.inflate(inflater)
       binding.lifecycleOwner = this

       //viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

       // TODO Inject
       //viewModel.userManager = UserManagerImpl(requireContext())
       //viewModel.userRepository = UserRepositoryImpl(viewModel.userManager, NetworkInteractorImpl())

       binding.viewModel = viewModel

       lifecycle.addObserver(viewModel)
       observe(viewModel.getViewState(), ::onViewState)

       return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
       viewModel.initView()
   }

   override fun onResume() {
       super.onResume()
       (requireActivity() as AppCompatActivity).supportActionBar?.hide()
   }

   private fun onViewState(state: LoginViewState?) {
       when (state) {
           LoginViewState.LoginError -> {
               requireContext().toast("Error al iniciar sesión")
           }
           LoginViewState.LoginSuccess -> {
               findNavController().navigate(
                   LoginFragmentDirections.actionLoginFragmentToListFragment()
               )
               viewModel.navigated()
           }
           else -> {}
       }
   }*/
}