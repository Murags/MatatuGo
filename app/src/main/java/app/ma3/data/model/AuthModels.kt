package app.ma3.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val id: Int,
    val name: String,
    val email: String,
    val access_token: String
)

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)

data class SignUpResponse(
    val id: Int,
    val name: String,
    val email: String,
    val created_at: String,
    val updated_at: String,
    val access_token: String
)

data class AuthErrorResponse(
    val detail: String? = null
)

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val accessToken: String
) : Parcelable
