package mx.tec.atomictracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates


class Detalle : AppCompatActivity() {
    lateinit var ubicacion : TextView
    lateinit var descripcion : TextView
    lateinit var frecuencia : TextView
    lateinit var inicio : TextView
    lateinit var fin : TextView
    //var currentID by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        descripcion = findViewById(R.id.descripcion)
        frecuencia = findViewById(R.id.frecuencia)
        inicio = findViewById(R.id.detalles_inicio)
        fin = findViewById(R.id.detalles_fin)
        ubicacion = findViewById(R.id.detalleUbicacion)
        //currentID = intent.getIntExtra("id", 0)
       // leerDatos(null)
    }

    //Leer informacion de la base de datos

    /*fun leerDatos(view : View?){
        Firebase.firestore.collection("habitos")
            .get().addOnSuccessListener {

                for(habit in it){
                    Log.d("FIRESTORE", "${habit.id} ${habit.data}")

                    //if(habit.id.toInt() == currentID){
                        Log.d("FIRESTORE",habit.get("Descripcion").toString())
                        Log.d("FIRESTORE",habit.get("Frecuencia").toString())
                        Log.d("FIRESTORE",habit.get("Inicio").toString())
                        Log.d("FIRESTORE",habit.get("Fin").toString())
                        descripcion.text = habit.get("Descripcion").toString()
                        inicio.text = habit.get("Inicio").toString()
                        fin.text = habit.get("Fin").toString()
                        frecuencia.text = habit.get("Frecuencia").toString()
                        ubicacion.text = habit.get("Ubicacion").toString()
                        Log.d("FIRESTORE", ubicacion.text.toString())
                    }
                }
            }.addOnFailureListener{
                Log.e("FIRESTORE", "Error al leer el habito:  ${it.message}")
            }
    }*/



}