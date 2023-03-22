package kz.devs.aiturm.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun login(email: String, password: String): Task<AuthResult>
    suspend fun logout(): Result<Boolean>
    suspend fun signUp(email: String, password: String): Task<AuthResult>
}