package app.ma3.data.repository

import android.content.Context
import app.ma3.data.api.AuthApiService
import app.ma3.data.model.AuthErrorResponse
import app.ma3.data.model.LoginRequest
import app.ma3.data.model.LoginResponse
import app.ma3.data.model.SignUpRequest
import app.ma3.data.model.SignUpResponse
import app.ma3.data.model.User
import app.ma3.data.network.NetworkModule
import app.ma3.data.preferences.TokenManager
import com.google.gson.Gson
import retrofit2.Response

class AuthRepository(
    private val authApiService: AuthApiService = NetworkModule.authApiService,
    private val tokenManager: TokenManager? = null
) {

    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val loginRequest = LoginRequest(email = email, password = password)
            val response = authApiService.login(loginRequest)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                val user = User(
                    id = loginResponse.id,
                    name = loginResponse.name,
                    email = loginResponse.email,
                    accessToken = loginResponse.access_token
                )


                tokenManager?.saveAuthData(
                    accessToken = loginResponse.access_token,
                    userId = loginResponse.id,
                    userName = loginResponse.name,
                    userEmail = loginResponse.email
                )

                Result.success(user)
            } else {
                val errorMessage = try {
                    val errorBody = response.errorBody()?.string()
                    if (!errorBody.isNullOrEmpty()) {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(errorBody, AuthErrorResponse::class.java)
                        errorResponse.detail ?: "Login failed: ${response.code()} ${response.message()}"
                    } else {
                        "Login failed: ${response.code()} ${response.message()}"
                    }
                } catch (e: Exception) {
                    "Login failed: ${response.code()} ${response.message()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signup(name: String, email: String, password: String): Result<User> {
        return try {
            val signUpRequest = SignUpRequest(name = name, email = email, password = password)
            val response = authApiService.signup(signUpRequest)

            if (response.isSuccessful && response.body() != null) {
                val signUpResponse = response.body()!!
                val user = User(
                    id = signUpResponse.id,
                    name = signUpResponse.name,
                    email = signUpResponse.email,
                    accessToken = signUpResponse.access_token
                )

                tokenManager?.saveAuthData(
                    accessToken = signUpResponse.access_token,
                    userId = signUpResponse.id,
                    userName = signUpResponse.name,
                    userEmail = signUpResponse.email
                )

                Result.success(user)
            } else {
                val errorMessage = try {
                    val errorBody = response.errorBody()?.string()
                    if (!errorBody.isNullOrEmpty()) {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(errorBody, AuthErrorResponse::class.java)
                        errorResponse.detail ?: "Sign up failed: ${response.code()} ${response.message()}"
                    } else {
                        "Sign up failed: ${response.code()} ${response.message()}"
                    }
                } catch (e: Exception) {
                    "Sign up failed: ${response.code()} ${response.message()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        tokenManager?.clearAuthData()
    }
}
