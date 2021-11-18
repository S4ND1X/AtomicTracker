package com.example.atomictracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.atomictracker.databinding.ActivityCheckEmailBinding
import com.example.atomictracker.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class CheckEmailActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityCheckEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        // Get the user
        val user = auth.currentUser

        binding.veficateEmailAppCompatButton.setOnClickListener {
            val profileUpdates = userProfileChangeRequest {}
            user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    if(user.isEmailVerified){
                        reload()
                    }else{
                        Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.signOutImageView.setOnClickListener {
            signOut()
        }
    }

    /**
     * Check on start of activity if currentUser is authenticated and email is verified
     * If yes then load MainActivity
     * If no send a verification email to the user
     */
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                reload();
            }else{
                sendEMailVerification()
            }

        }
    }


    /**
     * Send a verification email to the user
     * If send a email was successful tell the user a email was sent
     */
    private fun sendEMailVerification(){
        val user = auth.currentUser
        user!!.sendEmailVerification().addOnCompleteListener(this) { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "A verification email was sent, please confirm your email", Toast.LENGTH_LONG).show()
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

    /**
     * Logout the current user
     */

    private fun signOut(){
        Firebase.auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

}