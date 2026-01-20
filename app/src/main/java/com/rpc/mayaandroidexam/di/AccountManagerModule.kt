package com.rpc.mayaandroidexam.di

import android.content.Context
import com.rpc.mayaandroidexam.core.MayaAccountManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AccountManagerModule {

    @Provides
    @Singleton
    fun provideMayaAccountManager(
        @ApplicationContext context: Context,
    ): MayaAccountManager {
        return MayaAccountManager(context)
    }

}