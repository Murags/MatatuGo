package app.ma3.data.api

import app.ma3.data.model.LoginRequest
import app.ma3.data.model.LoginResponse
import app.ma3.data.model.SignUpRequest
import app.ma3.data.model.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * API service for authentication endpoints
 */
interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>
}

