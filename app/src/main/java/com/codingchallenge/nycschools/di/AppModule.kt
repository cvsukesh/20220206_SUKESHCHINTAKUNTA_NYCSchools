package com.codingchallenge.nycschools.di

import android.content.Context
import androidx.room.Room
import com.codingchallenge.nycschools.mvvm.NycSchoolsRepository
import com.codingchallenge.nycschools.network.NetworkDownloadTaskManager
import com.codingchallenge.nycschools.room.NycSchoolDao
import com.codingchallenge.nycschools.room.NycSchoolDatabase
import com.codingchallenge.nycschools.network.NycSchoolApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        const val BASE_URL: String = "https://data.cityofnewyork.us/resource/"
        const val WRITE_TIMEOUT = 60L
        const val READ_TIMEOUT = 60L
        const val CONNECTION_TIMEOUT = 60L
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
    }.build()

    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        client(okHttpClient)
        addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    @Provides
    fun provideNetworkDownloadTaskManager(@ApplicationContext context: Context): NetworkDownloadTaskManager =
        NetworkDownloadTaskManager(context)

    @Provides
    fun provideAPIInterface(retrofit: Retrofit): NycSchoolApi =
        retrofit.create(NycSchoolApi::class.java)

    @Provides
    fun provideNycSchoolRepository(
        networkDownloadTaskManager: NetworkDownloadTaskManager,
        nycSchoolApi: NycSchoolApi,
        nycSchoolDao: NycSchoolDao
    ): NycSchoolsRepository = NycSchoolsRepository(networkDownloadTaskManager, nycSchoolApi, nycSchoolDao)

    @Provides
    fun provideNycSchoolDatabase(@ApplicationContext context: Context): NycSchoolDatabase =
        Room.databaseBuilder(context, NycSchoolDatabase::class.java, "nyc-high-school-database")
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideNycSchoolDao(nycSchoolDatabase: NycSchoolDatabase) : NycSchoolDao = nycSchoolDatabase.getNycSchoolDao()
}