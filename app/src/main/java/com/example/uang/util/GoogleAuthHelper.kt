package com.example.uang.util

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.uang.R
import com.example.uang.SignInResultData
import com.example.uang.UserData
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthHelper(
    private val oneTapClient: SignInClient,
    private val application: Context
) {

    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }

        return result?.pendingIntent?.intentSender
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e

        }
    }

    fun getSignedUser(): UserData? = auth.currentUser?.run {
        UserData(
            uid, displayName, photoUrl.toString()
        )
    }


    suspend fun signInWithIntent(intent: Intent): SignInResultData {
        val client = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = client.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResultData(
                data = user?.let {
                    UserData(
                        userId = it.uid,
                        userName = it.displayName,
                        profilePrictureUser = it.photoUrl.toString()
                    )
                }, errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResultData(
                data = null,
                errorMessage = e.message
            )
        }
    }


    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(application.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}