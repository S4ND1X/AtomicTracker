package mx.tec.atomictracker

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Login : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var googleLogin : ImageButton

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.login_mail)
        password = findViewById(R.id.login_password)
        googleLogin = findViewById(R.id.sign_in_button)

        //Firebase auth
        auth = Firebase.auth

        //GOOGLE SIGN IN
        googleLogin.setOnClickListener {
            val googleConfig = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("134042100290-e3m6qrbnqemct6rv8prrre4uqra4ec5i.apps.googleusercontent.com")
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConfig)
            googleClient.signOut()
            val RC_SIGN_IN = 0
            startActivityForResult(googleClient.signInIntent, RC_SIGN_IN)
        }

    }

    fun login(view : View?){
        if(email.text.toString()== ""){
            Toast.makeText(this, "Por favor, ingresa un correo válido", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.text.toString() == ""){
            Toast.makeText(this, "Por favor, ingresa una contraseña", Toast.LENGTH_SHORT).show()
            return
        }
        Firebase.auth.signInWithEmailAndPassword(
            email.text.toString(),
            password.text.toString()).addOnCompleteListener(this){
                if(it.isSuccessful){
                    Log.d("FIREBASE", "Registro exitoso")
                    homeActivity()


                }else{
                    Log.d("FIREBASE", "Registro fallido: ${it.exception?.message}")
                    Toast.makeText(this, "User not found, try again or sign up", Toast.LENGTH_SHORT).show()
                }
        }
    }



    fun homeActivity(){
        val intent = Intent(this, Home::class.java)
        intent.putExtra("mail", email.text.toString())
        startActivity(intent)
    }

    fun signupActivity(view : View?){
        val intent = Intent(this, Registro::class.java)
        startActivity(intent)
    }

    //GOOGLE LOGIN
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)
                if(account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                        if(it.isSuccessful){
                            var googleEmail = it.result.user?.email
                            homeActivity()
                        }
                    }
                }
            }catch(e:ApiException){
                Log.e("GOOGLE", e.message.toString())
            }
        }

    }


}