package com.harison.core.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {
    @Provides
    @Singleton
    @Named("App context")
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }
}