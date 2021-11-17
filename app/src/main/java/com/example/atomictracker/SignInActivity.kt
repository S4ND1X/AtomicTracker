package com.example.atomictracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.atomictracker.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SingIn Activity
        binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.signInAppCompatButton.setOnClickListener{
            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()

            when {
                mEmail.isEmpty() || mPassword.isEmpty() -> {
                    Toast.makeText(
                        baseContext, "Email or Password should not be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    SignIn(mEmail, mPassword)
                }
            }
        }
    }

    /**
     *
     */
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    /**
     *
     */
    private fun SignIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d("TAG", "signInWithEmail:success")
                    reload()
                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     *
     */
    private fun reload(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
}