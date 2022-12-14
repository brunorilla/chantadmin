package com.example.projectchantadmin.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.projectchantadmin.R
import com.example.projectchantadmin.utils.UserSession

class Fragment_customer : Fragment() {

    private lateinit var nombre: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_customer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nombre = view.findViewById(R.id.id_txt_customer)

        Log.d(ContentValues.TAG, "Rol en FRAGMENT LOGIN!!: ${UserSession.rol}")
        Log.d(ContentValues.TAG, "Name en FRAGMENT LOGIN!!: ${UserSession.userName}")
        nombre.text = UserSession.rol
    }

}