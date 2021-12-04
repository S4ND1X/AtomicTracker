package mx.tec.atomictracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.*
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
        if(email.text.toString()== ""){
            Toast.makeText(this, "Por favor ingresa un e-mail válido", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.text.toString().length < 6){
            Toast.makeText(this, "La contraseña debe ser de al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        if(password.text.toString() == ""){
            Toast.makeText(this, "Por favor ingresa una contraseña", Toast.LENGTH_SHORT).show()
            return
        }
        Firebase
        Firebase.auth.createUserWithEmailAndPassword(
            email.text.toString(),
            password.text.toString()).addOnCompleteListener(this){
                if(it.isSuccessful){
                    Log.d("FIREBASE", "Registro exitoso")
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
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