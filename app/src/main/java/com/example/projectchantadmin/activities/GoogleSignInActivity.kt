package com.example.projectchantadmin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.projectchantadmin.R

class GoogleSignInActivity : AppCompatActivity() {

    lateinit var buttonAdmin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in)

        buttonAdmin = findViewById(R.id.idbuttonadmin)

        buttonAdmin.setOnClickListener{
            startActivity(Intent(this, AdminActivity::class.java))
        }



    }
}