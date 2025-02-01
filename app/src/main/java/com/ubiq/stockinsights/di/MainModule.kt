package com.ubiq.stockinsights.di


import com.ubiq.stockinsights.data.api.MainApiClient
import com.ubiq.stockinsights.data.repository.MainRepoIml
import com.ubiq.stockinsights.domain.usecase.MainUseCase
import com.ubiq.stockinsights.domain.usecasehandler.MainUseCaseHandler
import com.ubiq.stockinsights.util.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun getMainApiClient(): MainApiClient = RetrofitHelper.getClient(MainApiClient::class.java)

    @Provides
    fun getMainUseCase(repo: MainRepoIml): MainUseCase = MainUseCase(repo)

    @Provides
    fun getMainRepo(client: MainApiClient): MainRepoIml = MainRepoIml(client)

    @Provides
    fun getMainUseCaseHandler(
        mainUseCase: MainUseCase
    ): MainUseCaseHandler = MainUseCaseHandler(mainUseCase)

}