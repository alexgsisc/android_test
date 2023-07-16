package io.parrotsoftware.qatest.data.dataSource.remote.impl

import io.parrotsoftware.qa_network.domain.NetworkResult
import io.parrotsoftware.qa_network.domain.requests.ApiAuthRequest
import io.parrotsoftware.qa_network.domain.responses.ApiCredentials
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import io.parrotsoftware.qa_network.services.ParrotApiService
import io.parrotsoftware.qatest.data.dataSource.remote.LoginDataSourceNetwork
import timber.log.Timber
import javax.inject.Inject

/**
 * LoginDataSourceNetworkImpl
 * @author (c) 2023, QA Test
 */
class LoginDataSourceNetworkImpl @Inject constructor(
    private val parrotApiService: ParrotApiService,

) : LoginDataSourceNetwork {
    val networkInteractor: NetworkInteractor = NetworkInteractorImpl ()

    override suspend fun login(email: String, password: String): NetworkResult<ApiCredentials> {
        val responseAuth = networkInteractor.safeApiCall {
            parrotApiService.auth(ApiAuthRequest(email, password))
        }
        Timber.d("Holaaaaaaaa ${responseAuth.requiredResult.accessToken}")

        if(responseAuth.requiredResult.accessToken.isNotEmpty()){
            executeGetMe(responseAuth.requiredResult.accessToken)
        }
        return responseAuth
    }

    private suspend fun executeGetMe(accessToken: String){
        val responseUser = networkInteractor.safeApiCall {
            parrotApiService.getMe("Bearer $accessToken")
        }

        Timber.d("executeGetMe ${responseUser.requiredResult.result}" )
        if (responseUser.requiredResult.result.stores.isEmpty()) {

        }
    }
}