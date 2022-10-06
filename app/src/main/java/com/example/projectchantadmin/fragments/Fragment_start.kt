package com.example.projectchantadmin.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.projectchantadmin.R
import com.example.projectchantadmin.activities.SplashActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class Fragment_start : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var idButtonLogout: Button
    private lateinit var idButtonDB: Button
    private lateinit var viewStart: View
    private lateinit var applicationContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewStart = inflater.inflate(R.layout.fragment_start, container, false)

        idButtonLogout = viewStart.findViewById(R.id.id_button_logout)
        idButtonDB = viewStart.findViewById(R.id.id_button_dataBase)
        applicationContext = findNavController().context
        auth = Firebase.auth

        return viewStart
    }

    override fun onStart() {
        super.onStart()

        idButtonLogout.setOnClickListener {
            val windowStart = Intent(applicationContext, SplashActivity::class.java)
            auth.signOut()
            startActivity(windowStart)
        }

        idButtonDB.setOnClickListener {
            Toast.makeText(applicationContext, "Generando datos....", Toast.LENGTH_SHORT)
                .show()
        }

    }

}