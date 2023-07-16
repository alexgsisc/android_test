package io.parrotsoftware.qatest.di.login

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qatest.data.dataSource.remote.LoginDataSourceNetwork
import io.parrotsoftware.qatest.data.dataSource.remote.impl.LoginDataSourceNetworkImpl
import io.parrotsoftware.qatest.data.managers.UserManager
import io.parrotsoftware.qatest.data.managers.impl.UserManagerImpl
import io.parrotsoftware.qatest.data.repositories.UserRepository
import io.parrotsoftware.qatest.data.repositories.impl.UserRepositoryImpl

/**
 * DataSourceModule
 * @author (c) 2023, QA Test
 */

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun provideLoginDataSourceNetwork(
        loginDataSourceNetwork: LoginDataSourceNetworkImpl
    ): LoginDataSourceNetwork

    @Binds
    fun provideUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun provideUserManager(
        userManager: UserManagerImpl
    ): UserManager

}