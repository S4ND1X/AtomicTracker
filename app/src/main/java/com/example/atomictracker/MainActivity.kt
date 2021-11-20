package com.example.atomictracker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.atomictracker.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private val fileResult = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.signOutImageView.setOnClickListener {
            signOut()
        }

        binding.updateProfileAppCompatButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            updateProfile(name)
        }

        binding.profileImageView.setOnClickListener {
            fileManager()
        }

        updateUI()
    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, fileResult)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {
                val uri = data.data

                uri?.let { imageUpload(it) }

            }
        }
    }

    private fun imageUpload(mUri: Uri) {

        val user = auth.currentUser
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Users")
        val fileName: StorageReference = folder.child("img"+user!!.uid)

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->

                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(uri.toString())
                }

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Se realizaron los cambios correctamente.",
                                Toast.LENGTH_SHORT).show()
                            updateUI()
                        }
                    }
            }
        }.addOnFailureListener {
            Log.i("TAG", "file upload error")
        }
    }



    /**
     * Logout the current user
     */

    private fun signOut(){
        Firebase.auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }


    /**
     * Update user profile info
     * @param name Name of the user
     */
    private fun updateProfile(name: String){

        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(baseContext,
                        "Data was updated successfully",
                        Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
    }

    /**
    * Show current auth user info
     */
    private fun updateUI(){
        val user = auth.currentUser

        if(user!=null){
            binding.emailTextView.text = user.email
            binding.nameTextView.text = user.displayName
            binding.nameEditText.setText(user.displayName)
            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.profile_photo)
                .into(binding.profileImageView)
            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.profile_photo)
                .into(binding.bgProfileImageView)
        }
    }
}