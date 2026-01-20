package com.rpc.mayaandroidexam.di

import com.google.gson.GsonBuilder
import com.rpc.mayaandroidexam.core.retrofit.RetrofitAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitAPIService(): RetrofitAPIService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(RetrofitAPIService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RetrofitAPIService::class.java)
    }
}