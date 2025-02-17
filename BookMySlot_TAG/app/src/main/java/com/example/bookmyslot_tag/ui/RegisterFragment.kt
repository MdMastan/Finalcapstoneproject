package com.example.bookmyslot_tag.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bookmyslot_tag.R
import com.example.bookmyslot_tag.database.DatabaseHelper
import com.example.bookmyslot_tag.database.UserModel

class RegisterFragment : Fragment() {

    private lateinit var etNewUsername: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        etNewUsername = view.findViewById(R.id.etNewUsername)
        etNewPassword = view.findViewById(R.id.etNewPassword)
        btnRegister = view.findViewById(R.id.btnRegister)
        dbHelper = DatabaseHelper(requireContext())

        btnRegister.setOnClickListener {
            val username = etNewUsername.text.toString()
            val password = etNewPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val user = UserModel(username, password)
                val isInserted = dbHelper.insertUser(user)

                if (isInserted) {
                    Toast.makeText(requireContext(), "Registration Successful!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                    Toast.makeText(requireContext(), "User already exists!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
