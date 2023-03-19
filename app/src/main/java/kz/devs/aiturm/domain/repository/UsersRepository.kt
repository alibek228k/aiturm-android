package kz.devs.aiturm.domain.repository

interface UsersRepository {
    suspend fun getUsers()
}