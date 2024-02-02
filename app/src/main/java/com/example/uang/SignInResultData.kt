package com.example.uang

data class SignInResultData(
    val data: UserData?,
    val errorMessage: String?
)


data class UserData (
    val userId: String,
    val userName: String?,
    val profilePrictureUser: String
)
