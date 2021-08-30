package com.example.emailauthfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.emailauthfirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(
                    binding.etLoginEmail.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please Enter Email",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                TextUtils.isEmpty(
                    binding.etLoginPassword.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                else -> {

                    val email: String = binding.etLoginEmail.text.toString().trim() { it <= ' ' }
                    val password: String =
                        binding.etLoginPassword.text.toString().trim() { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login Success!!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                                val intent =
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()

                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }
                        }
                }
            }
        }
    }
}