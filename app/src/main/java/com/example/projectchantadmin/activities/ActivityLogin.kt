package com.example.projectchantadmin.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.projectchantadmin.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.Serializable


class ActivityLogin : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("430285401875-qal3lud0i9ep0jnqjlrhsr87voa3949p.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signInButton.setOnClickListener {
            signIn()
        }
        binding.idLogout.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        googleSignInClient.signOut()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        const val RC_SIGN_IN = 1001
        const val EXTRA_NAME = "EXTRA NAME"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle " + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(ContentValues.TAG, "Google sign in failed ", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Log.d(ContentValues.TAG, "CREDENTIAL " + credential.provider)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.d(ContentValues.TAG, "signInWithCredential:failed", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(applicationContext, GoogleSignInActivity::class.java)
            intent.putExtra("name", user.displayName)
            Log.d("telefono", user.phoneNumber.toString())
            Log.d("email", user.email.toString())
            startActivity(intent)
        }
    }


}
