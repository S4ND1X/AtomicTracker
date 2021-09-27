package mx.tec.atomictracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View


lateinit var email: String


class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        email = intent.extras?.getString("email").toString()
    }

     fun logOutActivity(view : View?){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    fun editarActivity(view : View?){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    fun eliminarActivity(view : View?){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    fun agregarActivity(view : View?){
        val intent = Intent(this, CreacionHabitoActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }
}