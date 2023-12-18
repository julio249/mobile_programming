package com.example.final_project.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.final_project.R
import com.example.final_project.database.DatabaseHandler
import com.example.final_project.firestore.FirestoreClass
import com.example.final_project.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : BaseActivity() {

    private var toolbar_sign_up_activity : Toolbar? = null
    private var et_name : EditText? = null
    private var et_email: EditText? = null
    private var et_password: EditText? = null
    private var btn_sign_up: Button? = null
    val dbHandler = DatabaseHandler(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        et_name = findViewById(R.id.et_name)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        btn_sign_up = findViewById(R.id.btn_sign_up)

        //FULL SCREEN
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        toolbar_sign_up_activity = findViewById(R.id.toolbar_sign_up_activity)
        setUpActionBar()
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar_sign_up_activity)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_sign_up_activity?.setNavigationOnClickListener { onBackPressed() }

        btn_sign_up?.setOnClickListener {
            registerUser()
        }
    }

    //Getting information of the user from the textFields
    private fun registerUser() {
        val name: String = et_name?.text.toString().trim{ it <= ' ' }
        val email: String = et_email?.text.toString().trim { it <= ' ' }
        val password: String = et_password?.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)) {
            Toast.makeText(this@SignUpActivity, "Now we can register a new user", Toast.LENGTH_SHORT).show()
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!

                        //Setting parameters of the user
                        val user = User(firebaseUser.uid, name, registeredEmail)

//                        //Creating a new user
                        FirestoreClass().registerUser(this, user)
//                        val addUser = dbHandler.addUser(user)

//                        if (addUser>0)
//                        {
//                            setResult(Activity.RESULT_OK)
////                            finish()
//                        }




                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter an email")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> {
                true
            }
        }
    }

    fun userRegisteredSuccess() {
        Toast.makeText(this, "You have successfully registered", Toast.LENGTH_LONG).show()
        hideProgressDialog()
        FirebaseAuth.getInstance().signOut()
        finish()
    }
}
