package com.example.final_project.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.final_project.R
import com.example.final_project.models.User
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {

    private var et_mail: EditText? = null
    private var et_password: EditText? = null
    private var btn_sign_in: Button? = null
    private lateinit var auth: FirebaseAuth
    private var toolbar_sign_in_activity: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        et_mail = findViewById(R.id.et_email_sign_in)
        et_password = findViewById(R.id.et_password_sign_in)
        btn_sign_in = findViewById(R.id.btn_sign_in)

        auth = FirebaseAuth.getInstance()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        btn_sign_in?.setOnClickListener{
            signInRegisteredUser()
        }

        toolbar_sign_in_activity = findViewById(R.id.toolbar_sign_in_activity)
        setUpActionBar()
    }

    private fun setUpActionBar()
    {
        setSupportActionBar(toolbar_sign_in_activity)
        val actionBar =  supportActionBar

        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_sign_in_activity?.setNavigationOnClickListener { onBackPressed() }

    }

    private fun signInRegisteredUser()
    {
        val email: String = et_mail?.text.toString().trim{it<=' '}
        val password: String = et_password?.text.toString().trim{it<=' '}

        if (validateForm(email,password))
        {
            showProgressDialog(resources.getString(R.string.please_wait))

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Sign-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {

                        Toast.makeText(
                            this@SignInActivity,
                            "You have successfully signed in.",
                            Toast.LENGTH_LONG
                        ).show()

                        startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String): Boolean
    {
        return when {

            TextUtils.isEmpty(email)-> {
                showErrorSnackBar("please enter a email")
                false
            }
            TextUtils.isEmpty(password)-> {
                showErrorSnackBar("please enter a password")
                false
            }
            else -> {
                true
            }
        }
    }

    fun signInSuccess(user: User){

        hideProgressDialog()
        startActivity(Intent(this,HomeActivity::class.java))
        finish()

    }
}