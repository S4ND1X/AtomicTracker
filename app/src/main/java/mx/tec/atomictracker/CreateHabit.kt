package mx.tec.atomictracker

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tec.atomictracker.databinding.ActivityCreateHabitBinding
import java.util.*

class CreateHabit : AppCompatActivity(),TimePickerDialog.OnTimeSetListener  {

    lateinit var nombre : EditText
    lateinit var descripcion : EditText
    //lateinit var inicio : EditText
    var inicio : String = ""
    var fin : String = ""
    var ubicacion : String = ""
    var inicioSet: Boolean = false
    var finSet : Boolean = false
    //NEW WIDGETS FOR HABIT CREATION
    lateinit var spinner : Spinner
    private lateinit var binding: ActivityCreateHabitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)



        //TESTING AREA
        binding = ActivityCreateHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nombre = binding.editNombre
        descripcion = binding.editDescripcion
        spinner = binding.spinner
        populateSpinner()
        binding.inicioButton.setOnClickListener {
            inicioSet = true
            finSet = false
            TimePickerDialog(this, this, 12, 0, true).show()
        }
        binding.finButton.setOnClickListener {
            inicioSet = false
            finSet = true
            TimePickerDialog(this, this, 12, 0, true).show()
        }


    }

    fun populateSpinner(){
        val options = arrayOf("diario", "cada dos dias", "semanal")
        ArrayAdapter.createFromResource(
            this,
            R.array.Frecuencias,
            android.R.layout.simple_spinner_item
        ).also{adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
        spinner.setSelection(0)
    }

    //Listener para agregar la ubicacion del nuevo habito
    val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->

        if(result.resultCode == Activity.RESULT_OK){

            val data: Intent? = result.data
            Toast.makeText(this," Successfully changed location", Toast.LENGTH_SHORT).show()
            ubicacion = data?.getStringExtra("result").toString()
            Log.wtf("LOCATION FROM MAP ","Received location: $ubicacion")
            //updateLocation()

        }

    }

    fun openMaps(view: View?){
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("ubicacion",ubicacion)
        //intent.putExtra("id", currentID)
        Toast.makeText(this, "Sending the ubication $ubicacion", Toast.LENGTH_SHORT).show()
        activityResultLauncher.launch(intent)
    }


    fun crear(view : View?){

        if(nombre.text.toString()== "" || descripcion.text.toString()== "" || inicio== "" || fin== ""){
            Toast.makeText(this, "Por favor, llena todos los campos \n", Toast.LENGTH_SHORT).show()
            return
        }
        val data = hashMapOf(

            "Nombre" to nombre.text.toString(),
            "Descripcion" to descripcion.text.toString(),
            //"Frecuencia" to frecuencia.text.toString(),
            "Frecuencia" to spinner.selectedItem.toString(),
            "Inicio" to inicio,
            "Fin" to fin,
            "Ubicacion" to ubicacion

        )

        Firebase.firestore.collection("habitos").add(data).addOnSuccessListener { documentReference->
            Log.d("FIREBASE", "Document written with ID: ${documentReference.id}")
            //this.finish()
            //this.finishAfterTransition()
            val intent = Intent(this, Home::class.java)
            scheduleNotification(nombre.toString())
            startActivity(intent)
        }.addOnFailureListener{e ->
            Log.w("FIREBASE", "Error adding the document", e)
        }

        Toast.makeText(this, "HÁBITO GUARDADO", Toast.LENGTH_SHORT).show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        Toast.makeText(this, "$p1 $p2", Toast.LENGTH_SHORT).show()
        if(inicioSet){
            inicio = "$p1:$p2"
        }else{
            fin = "$p1:$p2"
        }

    }

    //NOTIFICATIONS

    private fun getTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(2021, 12, 5, inicio.split(":")[0].toInt(), inicio.split(":")[1].toInt())
        //Toast.makeText(this, "$inicio", Toast.LENGTH_SHORT).show()
        return calendar.timeInMillis
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt " + dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Habits Reminder Channel"
        val description = "Channel to send reminder of your habit"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = description
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleNotification(name: String) {
        val intent = Intent(applicationContext, ReminderBroadcaster::class.java)
        val title = name
        val message = "Es tiempo de $name"

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

        showAlert(time, title, message)


    }
}