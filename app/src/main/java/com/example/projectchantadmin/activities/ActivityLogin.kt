package com.example.projectchantadmin.activities

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.projectchantadmin.R
import com.example.projectchantadmin.databinding.ActivityLoginBinding
import com.example.projectchantadmin.entities.User
import com.example.projectchantadmin.utils.GoogleClientGso
import com.example.projectchantadmin.utils.UserSession
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable


class ActivityLogin : AppCompatActivity(), Serializable {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userEditText: EditText
    private lateinit var email: String
    private lateinit var person: User
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        userEditText = binding.editTextEmail

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("430285401875-qal3lud0i9ep0jnqjlrhsr87voa3949p.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        GoogleClientGso.googleSignInClient = googleSignInClient



        binding.signInButton.setOnClickListener {
            signIn()
        }

        binding.btnContinuar.setOnClickListener {

            email = userEditText.text.toString()
            //Agregamos validación
            when {
                email.isEmpty() -> {
                    Toast.makeText(
                        this,
                        "El campo email es obligatorio, caso contrario loguearse con GOOGLE!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                else -> {
                    UserSession.userEmail = userEditText.text.toString()
                    person = User(userEditText.text.toString())

                    val docRef = db.collection("Usuarios").document(email)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document.data != null) {
                                Toast.makeText(this, "Access Success", Toast.LENGTH_LONG)
                                    .show()
                                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                                UserSession.rol = document.data!!["rol"].toString()
                                if (UserSession.rol == "admin") {
                                    val intent =
                                        Intent(applicationContext, GoogleSignInActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    val intent =
                                        Intent(applicationContext, CustomerActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "El email no esta registrado, for favor registrese!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                Log.d(TAG, "No such document")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }


                }
            }

        }
        binding.btnRegistrar.setOnClickListener {

            email = userEditText.text.toString()

            if (isValidEmail(email)) {
                db.collection("Usuarios").document(userEditText.text.toString())
                    .set(person)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Creado con éxito", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(applicationContext, CustomerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
            } else {
                Toast.makeText(this, "Introduzca un email válido", Toast.LENGTH_LONG)
                    .show()
            }


        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
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

            val docRef = db.collection("Usuarios").document(user.email.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document.data != null) {
                        Toast.makeText(this, "Access Success", Toast.LENGTH_LONG)
                            .show()
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        UserSession.rol = document.data!!["rol"].toString()
                        UserSession.userName = document.data!!["name"].toString()
                        UserSession.userPhoto = document.data!!["photo"].toString()
                    } else {
                        Toast.makeText(
                            this,
                            "El email no esta registrado, se creo usuario default!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        Log.d(TAG, "No such document")
                        person = User(user.email.toString())
                        person.name = user.displayName.toString()
                        person.email = user.email.toString()
                        person.photo = user.photoUrl.toString()
                        person.phone = user.phoneNumber.toString()
                        docRef.set(person)
                    }

                    Log.d(TAG, "Rol!: ${UserSession.rol}")

                    if (UserSession.rol == "admin") {
                        val intent = Intent(applicationContext, GoogleSignInActivity::class.java)
                        Log.d("ROL ADMIN PARA GoogleSignInActivity", UserSession.rol.toString())
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(applicationContext, CustomerActivity::class.java)
                        Log.d("ROL ADMIN PARA CustomerActivity", UserSession.rol.toString())
                        startActivity(intent)
                        finish()
                    }
                }

        }
    }
}




