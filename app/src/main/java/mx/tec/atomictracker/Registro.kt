package mx.tec.atomictracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Registro : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var password : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        email = findViewById(R.id.signup_email)
        password = findViewById(R.id.signup_password)
    }

    fun signup(view : View?){
        Firebase.auth.createUserWithEmailAndPassword(
            email.text.toString(),
            password.text.toString()).addOnCompleteListener(this){
                if(it.isSuccessful){
                    Log.d("FIREBASE", "Registro exitoso")
                    loginActivity(null)
                }else{
                    Log.d("FIREBASE", "Registro fallido : ${it.exception?.message}")
                }
        }
    }

    fun loginActivity(view : View?){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

}