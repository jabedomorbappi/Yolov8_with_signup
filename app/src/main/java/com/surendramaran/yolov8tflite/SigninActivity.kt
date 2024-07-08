package com.surendramaran.yolov8tflite


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.surendramaran.yolov8tflite.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivitySigninBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // use to remove action bar
        supportActionBar?.hide()

        auth= Firebase.auth
        binding.buttonSignin.setOnClickListener{
            val email=binding.signinEtEmail.text.toString()
            val pass=binding.signinEtpass.text.toString()
            if (checkAllFilled())
            {
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this,"Successfully sign in ",Toast.LENGTH_SHORT).show()
                        //go to another activity
                        val intent= Intent(this,MainHomeActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    else{
                        Log.e("error",it.exception.toString())

                    }

                }

            }
        }


        // another binding.
        binding.tvcreateAccount.setOnClickListener{
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()


        }
        binding.ForgotPassword.setOnClickListener {

            val intent=Intent(this,ForgottenPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }






        enableEdgeToEdge()
        // setContentView(R.layout.activity_signin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkAllFilled():Boolean
    {
        val email=binding.signinEtEmail.text.toString()


        if (binding.signinEtEmail.text.toString()=="")
        {
            binding.signinTextInputLayoutEmail.error="this is required fail"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.signinTextInputLayoutEmail.error="check the email format"
            return false
        }
        // also note pass should be at least 6 character
        if (binding.signinEtpass.text.toString()==""){
            binding.signinTextInputLayoutpass.error="this is required fill"
            binding.signinTextInputLayoutpass.errorIconDrawable=null
            return false
        }

        if (binding.signinEtpass.length()<=6){
            binding.signinTextInputLayoutpass.error="pass at least 6 character"
            binding.signinTextInputLayoutpass.errorIconDrawable=null
            return false
        }
        return true

    }
}