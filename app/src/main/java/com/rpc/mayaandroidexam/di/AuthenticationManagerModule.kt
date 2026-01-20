package com.rpc.mayaandroidexam.di

import android.content.Context
import com.rpc.mayaandroidexam.core.AuthenticationManager
import com.rpc.mayaandroidexam.core.MayaAccountManager
import com.rpc.mayaandroidexam.core.RetrofitAuthenticationManager
import com.rpc.mayaandroidexam.core.retrofit.RetrofitAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationManagerModule {

    @Provides
    @Singleton
    fun provideAuthenticationManager(
        service: RetrofitAPIService
    ): AuthenticationManager {
        return RetrofitAuthenticationManager(service)
    }

}