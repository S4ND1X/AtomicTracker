package com.example.atomictracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.sql.Time
import java.util.*

class CreateHabitActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    var cal = Calendar.getInstance()

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)


        populateSpinner()
        pickDate()
        pickHour()
    }

    private fun getDateTimeCalendar() {
        cal = Calendar.getInstance()

        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate(){
        val datePickerBtn : Button = findViewById(R.id.date_picker_button)
        datePickerBtn.setOnClickListener{
            getDateTimeCalendar()

            val picker = DatePickerDialog(this, this, year, month, day)
            picker.datePicker.minDate = cal.timeInMillis
            picker.show()
        }
    }

    private fun pickHour(){
        val hourPickerBtn : Button = findViewById(R.id.hour_picker_button)
        hourPickerBtn.setOnClickListener{
            getDateTimeCalendar()
            TimePickerDialog(this, this, hour, minute, true).show()
        }
    }

    private fun populateSpinner() {
        val spinner: Spinner = findViewById(R.id.frequencySpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.frequency_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.setSelection(0)
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