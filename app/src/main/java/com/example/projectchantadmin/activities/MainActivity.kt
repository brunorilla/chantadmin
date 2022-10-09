package com.example.projectchantadmin.activities

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectchantadmin.R
import com.example.projectchantadmin.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private val TAG = "AndroidClarified"
    private var googleSignInClient: GoogleSignInClient? = null
    private var oneTapClient: SignInClient? = null
    private var signInRequest: BeginSignInRequest? = null
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val getEmail = binding.idEditTextEmailAddressStart.text
        val getPasswd = binding.idEditTextPasswordStart.text

        //*************************************************************
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //validar si tengo internet aca tener un try por si no hay...
        /*binding.signInButton.setOnClickListener {
            val signInIntent = googleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, 101)
        }*/
        //*************************************************************

        binding.idButtonLogin.setOnClickListener {

            when {
                getEmail.toString().isEmpty() || getPasswd.toString().isEmpty() -> {
                    Toast.makeText(baseContext, "Email o password incorrectos", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    signIn(getEmail.toString(), getPasswd.toString())

                }
            }
        }

        /*binding.idButtonCreate.setOnClickListener {
            //Log.d("Email", getEmail.toString())
            //Log.d("Passwd", getPasswd.toString())
            when {
                getEmail.toString().isEmpty() || getPasswd.toString().isEmpty() -> {
                    Toast.makeText(baseContext, "Email o password incorrectos", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    signIn(getEmail.toString(), getPasswd.toString())
                }
            }
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) when (requestCode) {
            101 -> try {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)!!
                onLoggedIn(account)
            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            }
        }
    }

    private fun onLoggedIn(googleSignInAccount: GoogleSignInAccount) {
        val intent = Intent(this, CreateActivity::class.java)
        intent.putExtra(ProfileActivity.GOOGLE_ACCOUNT, googleSignInAccount)
        startActivity(intent)
        finish()
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }

    private fun signIn(email: String, password: String) {
        //val bundle = Bundle()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "La cuenta no existe..., por favor cree una",
                        Toast.LENGTH_SHORT
                    ).show()
                    //bundle.putString("textFromActivityA", email);
                    val intent = Intent(this, CreateActivity::class.java)
                    intent.putExtra("DATA_EMAIL_KEY", email);
                    startActivity(intent)

                }
            }
    }

    private fun createNewUser(email: String, password: String) {
        val bundle = Bundle()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "La cuenta no existe..., por favor cree una",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, CreateActivity::class.java)
                    bundle.putString("textFromActivityA", email);
                    this.startActivity(intent)
                }
            }
    }

    private fun reload() {
        val intent = Intent(this, StartActivity::class.java)
        this.startActivity(intent)
    }
}