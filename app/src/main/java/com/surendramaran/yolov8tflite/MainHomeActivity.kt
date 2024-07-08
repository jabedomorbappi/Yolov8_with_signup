package com.surendramaran.yolov8tflite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.surendramaran.yolov8tflite.databinding.ActivityMainHomeBinding

class MainHomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // go to image picker

        binding.btnImagepicker.setOnClickListener{
            val intent=Intent(this,ImageMainActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.btnDetection.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }

//        binding.btnYoutube.setOnClickListener{
//            val intent=Intent(this,MainActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }

        binding.btnAcc.setOnClickListener{
            val intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()

        }
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}