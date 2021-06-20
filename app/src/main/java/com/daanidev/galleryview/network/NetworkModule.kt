package com.daanidev.galleryview.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideRetrofitInterface(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://picsum.photos/v2/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {

    val interceptor = HttpLoggingInterceptor()
    return OkHttpClient().newBuilder().addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build()
}

fun provideRetrofitInterface(retrofit: Retrofit): RetrofitInterface = retrofit.create(RetrofitInterface::class.java)