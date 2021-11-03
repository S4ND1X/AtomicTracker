package mx.tec.atomictracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateHabit : AppCompatActivity() {

    lateinit var nombre : EditText
    lateinit var descripcion : EditText
    lateinit var frecuencia : EditText
    lateinit var inicio : EditText
    lateinit var fin : EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)

        nombre = findViewById(R.id.editNombre)
        descripcion = findViewById(R.id.editDescripcion)
        frecuencia = findViewById(R.id.editFrecuencia)
        inicio = findViewById(R.id.editInicio)
        fin = findViewById(R.id.editFin)

    }

    fun crear(view : View?){


        val data = hashMapOf(

            "Nombre" to nombre.text.toString(),
            "Descripcion" to descripcion.text.toString(),
            "Frecuencia" to frecuencia.text.toString(),
            "Inicio" to inicio.text.toString(),
            "Fin" to fin.text.toString(),
            "Ubicacion" to ""

        )

        Firebase.firestore.collection("habitos").add(data).addOnSuccessListener { documentReference->
            Log.d("FIREBASE", "Document written with ID: ${documentReference.id}")
            this.finish()
        }.addOnFailureListener{e ->
            Log.w("FIREBASE", "Error adding the document", e)
        }

        Toast.makeText(this, "HÁBITO GUARDADO", Toast.LENGTH_SHORT).show()
    }
}