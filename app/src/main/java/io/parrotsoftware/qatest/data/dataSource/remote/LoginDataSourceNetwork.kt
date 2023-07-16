package io.parrotsoftware.qatest.data.dataSource.remote

import io.parrotsoftware.qa_network.domain.NetworkResult
import io.parrotsoftware.qa_network.domain.responses.ApiCredentials

/**
 * LoginDataSourceNetwork
 * @author (c) 2023, QA Test
 */
interface LoginDataSourceNetwork {
    suspend fun login(email: String, password: String): NetworkResult<ApiCredentials>
}