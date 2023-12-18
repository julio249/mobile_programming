package com.example.final_project.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.example.final_project.R

class IntroActivity : BaseActivity  () {
    private var btn_sign_up_intro: Button? = null
    private var btn_sign_in_intro: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        setContentView(R.layout.activity_intro)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        btn_sign_up_intro = findViewById(R.id.btn_sign_up_intro)
        btn_sign_in_intro = findViewById(R.id.btn_sign_in_intro)

        btn_sign_up_intro?.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        btn_sign_in_intro?.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
        }

    }
}