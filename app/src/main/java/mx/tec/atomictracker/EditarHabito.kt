package mx.tec.atomictracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

class EditarHabito : AppCompatActivity() {

    lateinit var nombre : EditText
    lateinit var descripcion : EditText
    lateinit var inicio : EditText
    lateinit var fin : EditText
    lateinit var frecuencia : EditText
    var currentID = 0
    var ubicacion = ""


    val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->

        if(result.resultCode == Activity.RESULT_OK){

            val data: Intent? = result.data
            Toast.makeText(this," Successfully changed location", Toast.LENGTH_SHORT).show()
            ubicacion = data?.getStringExtra("result").toString()
            Log.wtf("LOCATION FROM MAP ","Received location: $ubicacion")
            updateLocation()

        }

    }

    fun updateLocation(){
        Log.wtf("TRYING TO WRITE IN DOCUMENT", currentID.toString())
        Firebase.firestore.collection("habitos").document(currentID.toString()).update("Ubicacion", ubicacion)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_habito)

        nombre = findViewById(R.id.editar_nombre)
        descripcion = findViewById(R.id.editar_descripcion)
        inicio = findViewById(R.id.editar_inicio)
        fin = findViewById(R.id.editar_fin)
        frecuencia = findViewById(R.id.editar_frecuencia)
        currentID = intent.getIntExtra("id", 0)
        ubicacion = intent.getStringExtra("ubicacion").toString()


    }

    fun openMaps(view: View?){
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("ubicacion",ubicacion)
        intent.putExtra("id", currentID)
        activityResultLauncher.launch(intent)
    }


    fun editHabit(view: View?){
        val habit = hashMapOf(
            "Nombre" to nombre.text.toString(),
            "Descripcion" to descripcion.text.toString(),
            "Inicio" to inicio.text.toString(),
            "Fin" to fin.text.toString(),
            "Frecuencia" to frecuencia.text.toString()
        )
        Firebase.firestore.collection("habitos").document(currentID.toString()).update(habit as Map<String, Any>)
        Toast.makeText(this, "Habit updated successfully", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Home::class.java)
        startActivity(intent)

    }

}