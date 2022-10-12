package com.example.projectchantadmin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectchantadmin.databinding.ActivityGoogleSignInBinding
import java.io.Serializable


class GoogleSignInActivity : AppCompatActivity(), Serializable {

    private lateinit var binding: ActivityGoogleSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textDisplayName.text = intent.getStringExtra("name")

    }
}