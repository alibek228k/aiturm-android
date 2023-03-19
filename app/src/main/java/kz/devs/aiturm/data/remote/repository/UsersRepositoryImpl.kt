package kz.devs.aiturm.data.remote.repository

import kz.devs.aiturm.data.remote.service.ApiService
import kz.devs.aiturm.domain.repository.UsersRepository

class UsersRepositoryImpl(
    private val api: ApiService
) : UsersRepository {

    override suspend fun getUsers() {

    }
}