package com.example.atomictracker

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.atomictracker.databinding.ActivityCreateHabitBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*


private val database = Firebase.database
val myRef = database.getReference("Users")

class CreateHabitActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var cal: Calendar = Calendar.getInstance()

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    private var savedDay: Int = 0
    private var savedMonth: Int = 0
    private var savedYear: Int = 0
    private var savedHour: Int = 0
    private var savedMinute: Int = 0

    private lateinit var binding: ActivityCreateHabitBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize Activity Activity
        binding = ActivityCreateHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateSpinner()


        // Add listener to date picker
        binding.datePickerButton.setOnClickListener {
            getDateTimeCalendar()

            val picker = DatePickerDialog(this, this, year, month, day)
            picker.datePicker.minDate = cal.timeInMillis
            picker.show()
        }
        //Add listener to hour picker
        binding.hourPickerButton.setOnClickListener {
            getDateTimeCalendar()
            TimePickerDialog(this, this, hour, minute, true).show()
        }

        // Add listener to save button
        binding.createHabitButton.setOnClickListener {

            val hName = binding.habitName.text.toString()
            val hFrequency = binding.frequencySpinner.selectedItem.toString()
            val hNotification = binding.notificationSwitch.isChecked

            // Make validations before sending information
            when {
                hName.isEmpty() || hFrequency.isEmpty() || savedYear == 0 ||
                        savedMonth == 0 || savedDay == 0 || savedHour == 0
                        || savedMinute == 0 -> {

                    Toast.makeText(
                        baseContext,
                        "Por favor verifica que todos los campos esten completos",
                        Toast.LENGTH_LONG
                    ).show()

                }
                else -> {
                    createHabit(
                        hName, hFrequency, hNotification, savedYear,
                        savedMonth, savedDay, savedHour, savedMinute
                    )

                    scheduleNotification(hName)
                }
            }
        }
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

    private fun getTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(savedYear, savedMonth, savedDay, savedHour, savedMinute)
        return calendar.timeInMillis
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

    private fun createHabit(
        hName: String, hFrequency: String, hNotification: Boolean, hYear: Int,
        hMonth: Int, hDay: Int, hHour: Int, hMinute: Int
    ) {

        val habit = HabitDTO()

        with(habit) {
            name = hName
            frequency = hFrequency
            notification = hNotification
            year = hYear
            month = hMonth
            day = hDay
            hour = hHour
            minute = hMinute
        }

        val userName: String = Firebase.auth.currentUser?.email?.split('@')?.get(0) ?: ""

        myRef.child(userName).push().setValue(habit)

        Toast.makeText(baseContext, "Se creo el habito correctamente", Toast.LENGTH_SHORT).show()

    }

    private fun getDateTimeCalendar() {
        cal = Calendar.getInstance()

        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun populateSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.frequency_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.frequencySpinner.adapter = adapter
        }
        binding.frequencySpinner.setSelection(0)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
    }

}