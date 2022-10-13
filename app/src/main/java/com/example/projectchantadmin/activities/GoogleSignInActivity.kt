package com.example.projectchantadmin.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectchantadmin.databinding.ActivityGoogleSignInBinding
import com.example.projectchantadmin.test.UserRandom
import java.io.Serializable


class GoogleSignInActivity : AppCompatActivity(), Serializable {

    private lateinit var binding: ActivityGoogleSignInBinding
    private lateinit var users: UserRandom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textDisplayName.text = intent.getStringExtra("name")

        binding.idBack.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }

        users = UserRandom()

        binding.idMostrarLista.setOnClickListener {
            users.print(users.generateUsersRandom(50))
        }


    }
}