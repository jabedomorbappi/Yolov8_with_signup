package com.surendramaran.yolov8tflite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
//import com.example.googlesignin.databinding.ActivitySignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
//import com.google.firebase.auth.Firebase
import com.google.firebase.auth.FirebaseAuth


import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.surendramaran.yolov8tflite.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {


    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignupBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // set view binding

        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView((binding.root))

        auth = Firebase.auth
        //btnsignup


        binding.buttonSignup.setOnClickListener{
            val email=binding.etEmail.text.toString()
            val pass=binding.etPassword.text.toString()
            val confPass=binding.etConfirmPassword.text.toString()
            if (checkAllField())
            {
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){
                    //if sucessfull acc is created
                    // is also signed in
                    if (it.isSuccessful)
                    {
                        auth.signOut()
                        Toast.makeText(this,"acc created successfully",Toast.LENGTH_SHORT).show()
                        intent=Intent(this,SigninActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Log.e("error",it.exception.toString())

                    }


                }
            }


        }



        val currentUser = auth.currentUser

        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val intent = Intent(this, AnotherActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }




        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //  @SuppressLint("SuspiciousIndentation")
    @SuppressLint("SuspiciousIndentation")
    private fun checkAllField():Boolean{
        val email=binding.etEmail.text.toString()


        if (binding.etEmail.text.toString()=="")
        {
            binding.textInputLayoutEmail.error="this is required fail"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.textInputLayoutEmail.error="check the email format"
            return false
        }
        // also note pass should be at least 6 character
        if (binding.etPassword.text.toString()==""){
            binding.textInputLayoutPassword.error="this is required fill"
            binding.textInputLayoutPassword.errorIconDrawable=null
            return false
        }

        if (binding.etPassword.length()<=6){
            binding.textInputLayoutPassword.error="pass at least 6 character"
            binding.textInputLayoutPassword.errorIconDrawable=null
            return false
        }

        if (binding.etConfirmPassword.text.toString()==""){
            binding.textInputLayoutPassword.error="this is required fill"
            binding.textInputLayoutConfirmPassword.errorIconDrawable=null
            return false
        }

        if (binding.etPassword.text.toString()!=binding.etConfirmPassword.text.toString()){
            binding.textInputLayoutPassword.error="password do not match"
            return false
        }
        return true


    }
}