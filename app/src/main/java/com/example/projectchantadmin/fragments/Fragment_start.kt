package com.example.projectchantadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.projectchantadmin.R

class Fragment_start : Fragment() {

    private lateinit var buttonStartLogin : Button
    private lateinit var buttonCreateUser : Button
    private lateinit var viewStart: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewStart = inflater.inflate(R.layout.fragment_start, container, false)

        buttonStartLogin = viewStart.findViewById(R.id.id_start_inicioSesion)
        buttonCreateUser = viewStart.findViewById(R.id.id_start_createUser)


        return viewStart
    }

    override fun onStart() {
        super.onStart()

        buttonStartLogin.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_fragment_start_to_fragment_login, null))
        buttonCreateUser.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_fragment_start_to_fragment_main, null))

    }

}