package com.example.shoopinglistwithtesting.di

import android.content.Context
import androidx.room.Room
import com.example.shoopinglistwithtesting.data.local.ShoppingDao
import com.example.shoopinglistwithtesting.data.local.ShoppingDatabase
import com.example.shoopinglistwithtesting.data.remote.PixabayApi
import com.example.shoopinglistwithtesting.repository.ShoppingRepository
import com.example.shoopinglistwithtesting.repository.ShoppingRepositoryImpl
import com.example.shoopinglistwithtesting.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePixabayApi() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constant.BASE_URL)
        .build()
        .create(PixabayApi::class.java)

    @Singleton
    @Provides
    fun provideShoppingItemDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ShoppingDatabase::class.java, Constant.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(db: ShoppingDatabase) = db.shoppingDao()

    // ? potential bug
    // ? if didn't work change to / ShoppingRepositoryImpl(api, dao) as ShoppingRepository /
    @Singleton
    @Provides
    fun provideShoppingRepositoryImpl(api: PixabayApi, dao: ShoppingDao): ShoppingRepository =
        ShoppingRepositoryImpl(api, dao)

}
