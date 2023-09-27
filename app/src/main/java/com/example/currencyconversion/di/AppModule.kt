package com.example.currencyconversion.di

import android.util.Log
import com.example.currencyconversion.data.remote.AnnotationExclusionStrategy
import com.example.currencyconversion.data.remote.OkHttpInterceptor
import com.example.currencyconversion.data.remote.endpoint.CurrencyApiEndpointInterface
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val client: OkHttpClient =
        try {
            OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(OkHttpInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        } catch (e: Exception) {
            Log.e("AppModule","okhttp client creation error")
            throw RuntimeException(e)
        }

    private val gson = Gson().newBuilder()
        .addSerializationExclusionStrategy(AnnotationExclusionStrategy())
        .serializeNulls()
        .setLenient()
        .create()


    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://openexchangerates.org/api/")
            .client(client)
            .build()


    @Provides
    @Singleton
    fun provideCurrencyApiEndpointInterface(retrofit: Retrofit): CurrencyApiEndpointInterface =
        retrofit.create(CurrencyApiEndpointInterface::class.java)
}
