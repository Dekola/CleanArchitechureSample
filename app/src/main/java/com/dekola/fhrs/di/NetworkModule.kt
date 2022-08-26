package com.dekola.fhrs.di

import android.content.Context
import com.dekola.fhrs.network.Network
import com.dekola.fhrs.network.NetworkConnectivity
import com.dekola.fhrs.network.api.AuthoritiesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideAuthoritiesApiService(retrofit: Retrofit): AuthoritiesApiService =
        retrofit.create(AuthoritiesApiService::class.java)

    @Provides
    fun provideClientBuilder(): OkHttpClient {
        val client = OkHttpClient.Builder()


        // Add staging flag
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor(interceptor)

        client.addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.addHeader("x-api-version", "2")
            val original = builder.build()
            chain.proceed(original)
        }
        return client.build()

    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).baseUrl("http://api.ratings.food.gov.uk/")
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return Network(context)
    }

}