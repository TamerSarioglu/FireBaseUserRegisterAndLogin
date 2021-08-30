package com.example.emailauthfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.emailauthfirebase.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            when {
                TextUtils.isEmpty(
                    binding.etRegisterEmail.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter Email",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                TextUtils.isEmpty(
                    binding.etRegisterPassword.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                else -> {

                    val email: String = binding.etRegisterEmail.text.toString().trim() { it <= ' ' }
                    val password: String =
                        binding.etRegisterPassword.text.toString().trim() { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Register Success!!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                                val intent =
                                    Intent(this@RegisterActivity, LoginActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()

                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }
                        }
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}