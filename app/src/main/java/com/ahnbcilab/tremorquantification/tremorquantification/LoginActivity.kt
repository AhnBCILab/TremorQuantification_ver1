package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val ID_NULL: Int = 1
    val PW_NULL: Int = 2
    val LOGIN_FAILED: Int = 3
    val LOGIN_SUCCESS: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            when (login(InputId.text.toString(), InputPassword.text.toString())) {
                ID_NULL -> InputIdLayout.error = "Enter the Id"
                PW_NULL -> InputPasswordLayout.error = "Enter the Password"
                LOGIN_FAILED -> Toast.makeText(this, "Wrong Id or Password", Toast.LENGTH_SHORT).show()
                LOGIN_SUCCESS -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        textSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        InputId.setOnFocusChangeListener { view, hasFocus ->
            run {
                if (!hasFocus && !InputId.text.isBlank())
                    InputIdLayout.error = null
            }
        }

        InputPassword.setOnFocusChangeListener { view, hasFocus ->
            run {
                if (!hasFocus && !InputPassword.text.isBlank())
                    InputPasswordLayout.error = null
            }
        }
    }

    fun login(id: String, pw: String): Int {
        when {
            id.isBlank() -> return ID_NULL
            pw.isBlank() -> return PW_NULL
        }

        val flag = LOGIN_SUCCESS
        // ProgressDialog should be changed
        val dialog = ProgressDialog(this)
        dialog.setMessage("Authenticating...")

        dialog.show()
        // Firebase..? Server..?
        /*
        if (loginfunction())
            flag = LOGIN_SUCCESS
        else
            flag = LOGIN_FAILED
        */
        dialog.dismiss()
        return flag
    }
}
