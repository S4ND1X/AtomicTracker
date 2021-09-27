package mx.tec.atomictracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
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
        startActivity(intent)
    }
}