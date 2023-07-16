package io.parrotsoftware.qa_network

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_network.services.ParrotApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkBuilder {

    /* private val defaultMoshiConverterFactory by lazy {
         val moshi = Moshi.Builder()
             .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
             .add(KotlinJsonAdapterFactory())
             .build()
         MoshiConverterFactory.create(moshi)
     }

     fun build(
         url: String
     ): Retrofit {
         val clientBuilder = OkHttpClient.Builder()
             .writeTimeout(60, TimeUnit.SECONDS)
             .readTimeout(60, TimeUnit.SECONDS)
             .connectTimeout(60, TimeUnit.SECONDS)

         val retrofitBuilder = Retrofit.Builder()
             .baseUrl(url)
             .client(clientBuilder.build())
             .addConverterFactory(defaultMoshiConverterFactory)
         return retrofitBuilder.build()
     }*/

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): Moshi =
        Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshiConverterFactory))
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideParrotApiService(
        retrofit: Retrofit
    ): ParrotApiService = retrofit.create(ParrotApiService::class.java)
}