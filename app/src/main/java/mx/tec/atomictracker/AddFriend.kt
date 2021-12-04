package mx.tec.atomictracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class AddFriend : AppCompatActivity() {


    lateinit var nombre: TextView
    lateinit var correo : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)

        nombre = findViewById(R.id.addName)
        correo = findViewById(R.id.addMail)
    }


    fun add(view: View?){
        if(correo.text.toString()== ""){
            Toast.makeText(this, "Por favor ingresa un e-mail válido", Toast.LENGTH_SHORT).show()
            return
        }
        if(nombre.text.toString() == ""){
            Toast.makeText(this, "Por favor ingresa un nombre para tu amigo", Toast.LENGTH_SHORT).show()
            return
        }
        Firebase.auth.fetchSignInMethodsForEmail(correo.text.toString()).addOnCompleteListener{
            var isNewUser: Boolean
            isNewUser = it.getResult()?.signInMethods?.isEmpty() ?: true
            if(isNewUser){
                Toast.makeText(this, "No se pudo agregar el amigo.", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent()
                intent.putExtra("result", nombre.text.toString() )

                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }


    }
}