package com.example.uang

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.uang.databinding.ActivityUserAuthBinding
import com.example.uang.ui.MainActivity
import com.example.uang.util.GoogleAuthHelper
import com.example.uang.viewmodel.AuthenticationViewMode
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserAuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserAuthBinding
    private val viewmodel by viewModels<AuthenticationViewMode>()
    private lateinit var googleHelper: GoogleAuthHelper
    private lateinit var state: SignState

    private val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    val signInResult = googleHelper.signInWithIntent(
                        intent = result.data ?: return@launch
                    )

                    viewmodel.onSignResult(signInResult)

                    if (state.isSignInSuccessfull) {

                        Toast.makeText(
                            this@UserAuthActivity,
                            "Sign In Succesfull",
                            Toast.LENGTH_LONG
                        ).show()

                        goToNextScreen()

                    }
                }
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Uang)
        binding = ActivityUserAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            viewmodel.state.collect { signState ->
                state = signState

                state.signInError?.let {
                    Toast.makeText(
                        this@UserAuthActivity,
                        it,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }



       googleHelper =
            GoogleAuthHelper(Identity.getSignInClient(this), this)

        binding.googleSignUp.setOnClickListener {
            lifecycleScope.launch {
                val signIntentSender = googleHelper.signIn()
                activityResultLauncher.launch(
                    IntentSenderRequest.Builder(
                        signIntentSender ?: return@launch
                    ).build()
                )
            }



        }

    }

    private fun goToNextScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}