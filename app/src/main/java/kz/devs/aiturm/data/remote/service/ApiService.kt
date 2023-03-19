package kz.devs.aiturm.data.remote.service

import retrofit2.http.GET

interface ApiService {

    @GET("https//someUrl")
    suspend fun getUsers()
}