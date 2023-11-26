package com.example.final_project.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.final_project.R


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
//            var currentUserID = FirestoreClass().getCurrentUserID()
//            if (currentUserID.isEmpty())
//            {
//                startActivity(Intent(this,MainActivity::class.java))
//            }else{
//                startActivity(Intent(this,IntroActivity::class.java))
//
//            }
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        },2500)

    }
}