package com.example.demoproject2.DI

import com.example.demoproject2.interfaces.OlxAPI
import com.example.demoproject2.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun providesOlxAPI(retrofitBuilder: Retrofit.Builder): OlxAPI {
        return retrofitBuilder.build().create(OlxAPI::class.java)
    }
}