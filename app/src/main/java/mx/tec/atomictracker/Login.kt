package mx.tec.atomictracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var password : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.login_mail)
        password = findViewById(R.id.login_password)
    }

    @SuppressLint("WrongConstant")
    fun login(view : View?){
        Firebase.auth.signInWithEmailAndPassword(
            email.text.toString(),
            password.text.toString()).addOnCompleteListener(this){
                if(it.isSuccessful){
                    Log.d("FIREBASE", "Registro exitoso")
                    homeActivity(null)


                }else{
                    Log.d("FIREBASE", "Registro fallido: ${it.exception?.message}")
                    Toast.makeText(this, "User not found, try again or sign up", Toast.LENGTH_SHORT).show()
                }
        }
    }



    private fun homeActivity(view : View?){
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    public fun signupActivity(view : View?){
        val intent = Intent(this, Registro::class.java)
        startActivity(intent)
    }
}