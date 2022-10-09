package com.example.projectchantadmin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.example.projectchantadmin.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient

class ActivityTestLogin : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private val TAG: String = "SignInActivity"
    private var RC_SIGN_IN: Int = 9001
    lateinit var button: SignInButton
    lateinit var text: TextView
    lateinit var mGoogleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_login)

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        text = findViewById(R.id.id_textView)
        button = findViewById(R.id.id_sign_in_button)

        button.setOnClickListener {
            signIn()
        }

    }

    fun signIn() {
        val signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result: GoogleSignInResult =
                Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)!!
            handleSignInResult(result)
        }

    }

    private fun handleSignInResult(result: GoogleSignInResult) {

        Log.d(TAG, "handleSignInResult" + result.isSuccess)
        if (result.isSuccess) {
            val account: GoogleSignInAccount? = result.signInAccount
            if (account != null) {
                text.text = "Hello ${account.displayName}!!!!"
            }
        }

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

}

