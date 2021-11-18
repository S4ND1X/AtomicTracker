package com.example.atomictracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.atomictracker.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.signUpButton.setOnClickListener {


            val mEmail = binding.emailEditText.text.toString()
            val mPassword  = binding.passwordEditText.text.toString()
            val mRepeatPassword = binding.repeatPasswordEditText.text.toString()

            // Regex to validate password
            val passwordRegex = Pattern.compile("^" +
                    "(?=.*[-@#$%^&+=])" + // At least 1 special character
                    ".{6,}" + // At least 6 characters
                    "$")


            // Validate all fields with regex and confirmation password field
            if(mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
                Toast.makeText(baseContext, "Email must be valid",
                    Toast.LENGTH_SHORT).show()
            }else if (mPassword.isEmpty() || !passwordRegex.matcher(mPassword).matches()){
                Toast.makeText(baseContext, mPassword,
                    Toast.LENGTH_SHORT).show()
                Toast.makeText(baseContext, "Password must contain at least one special character and 6 characters",
                    Toast.LENGTH_SHORT).show()
            }else if(mPassword != mRepeatPassword){
                Toast.makeText(baseContext, "Password doesn't match",
                    Toast.LENGTH_SHORT).show()

            }else{
                createAccount(mEmail, mPassword)
            }
        }

        /**
         * When click on back button
         * return to SignIn Activity
         */
        binding.backImageView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Check on start of activity if currentUser is authenticated
     * If yes then load MainActivity
     */
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            // If email is verified then load main activity if not then load CheckEmailActivity
            if(currentUser.isEmailVerified){
                reload()
            }else{
                val intent = Intent(this, CheckEmailActivity::class.java)
                startActivity(intent)
            }
        }
    }

    /**
     * Register user in firebase
     * if successful then show message
     * else show error message
     */
    private fun createAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Redirect to verify the email
                    val intent = Intent(this, CheckEmailActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
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