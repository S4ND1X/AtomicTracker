package mx.tec.atomictracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

val database = Firebase.database;
val myRef = database.getReference("Users")

lateinit var nombreField: EditText
lateinit var descripcionField: EditText
lateinit var frecuenciaField: TextView
lateinit var inicioField: EditText
lateinit var finField: EditText
lateinit var recordarField: Switch


class CreacionHabitoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creacion_habito)

        nombreField = findViewById(R.id.nombre)
        descripcionField = findViewById(R.id.descripcion)
        frecuenciaField = findViewById(R.id.frecuencia)
        inicioField = findViewById(R.id.inicio)
        finField = findViewById(R.id.fin)
        recordarField = findViewById(R.id.recordar)


    }


    fun createHabit(view: View?){

        val habit = HabitDTO()

        with(habit){
            nombre = nombreField.text.toString()
            descripcion = descripcionField.text.toString()
            frecuencia  = frecuenciaField.text.toString()
            inicio = inicioField.text.toString()
            fin = finField.text.toString()
            recordar = recordarField.isChecked
            // poner el field de longitud y latitud
        }

        // Obtener el user del email haciendo un split porque no se pueden guardar usuarios con ., @, etc
        val email = intent.extras?.getString("email").toString()
        val user = email.split('@')[0]

        // Guardar datos debajo del usuario y debajo de sus habitos
        myRef.child(user).push().setValue(habit)


        Toast.makeText(this, "Se guardo el habito correctamente", Toast.LENGTH_SHORT ).show()


    }
}