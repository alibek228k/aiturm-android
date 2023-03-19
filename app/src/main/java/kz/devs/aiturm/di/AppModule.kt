package kz.devs.aiturm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.devs.aiturm.data.remote.repository.UsersRepositoryImpl
import kz.devs.aiturm.data.remote.service.ApiService
import kz.devs.aiturm.domain.repository.UsersRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("baseUrl")
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(apiService: ApiService): UsersRepository {
        return UsersRepositoryImpl(apiService)
    }
}