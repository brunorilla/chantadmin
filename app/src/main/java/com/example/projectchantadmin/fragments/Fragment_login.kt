package com.example.projectchantadmin.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.projectchantadmin.R
import com.example.projectchantadmin.entities.User
import com.example.projectchantadmin.utils.UserSession

class Fragment_login : Fragment() {

    private lateinit var fotoPerfil: ImageView
    private lateinit var rolPerfil: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fotoPerfil = view.findViewById(R.id.id_photo_perfil)
        rolPerfil = view.findViewById(R.id.id_rol_perfil)
        Glide.with(this)
            .load(UserSession.userPhoto)
            .circleCrop()
            .override(300, 300)
            .into(fotoPerfil)

        //rolPerfil.text = UserSession.rol.toString()
        Log.d(ContentValues.TAG, "Rol en FRAGMENT LOGIN!!: ${UserSession.rol}")
        Log.d(ContentValues.TAG, "Name en FRAGMENT LOGIN!!: ${UserSession.userName}")
        rolPerfil.text = UserSession.rol
    }

}