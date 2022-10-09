package com.example.projectchantadmin.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projectchantadmin.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {
    private var profileName: TextView? = null
    private var profileEmail: TextView? = null
    private var profileImage: ImageView? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private var signOut: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        profileName = findViewById(R.id.profile_text)
        profileEmail = findViewById(R.id.profile_email)
        profileImage = findViewById(R.id.profile_image)
        signOut = findViewById(R.id.sign_out)
        setDataOnView()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        signOut!!.setOnClickListener(View.OnClickListener {
            /*
              Sign-out is initiated by simply calling the googleSignInClient.signOut API. We add a
              listener which will be invoked once the sign out is the successful
               */
            googleSignInClient!!.signOut()
                .addOnCompleteListener { //On Succesfull signout we navigate the user back to LoginActivity
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
        })
    }

    private fun setDataOnView() {
        val googleSignInAccount = intent.getParcelableExtra<GoogleSignInAccount>(GOOGLE_ACCOUNT)
        Picasso.get().load(googleSignInAccount!!.photoUrl).centerInside().fit().into(profileImage)
        profileName!!.text = googleSignInAccount!!.displayName
        profileEmail!!.text = googleSignInAccount!!.email
    }

    companion object {
        const val GOOGLE_ACCOUNT = "google_account"
    }
}