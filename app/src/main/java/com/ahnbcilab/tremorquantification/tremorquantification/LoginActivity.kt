package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.CurrentUserData
import com.ahnbcilab.tremorquantification.functions.Authentication
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.jvm.java

class LoginActivity : AppCompatActivity() {

    companion object {
        const val ID_NULL: Int = 1
        const val PW_NULL: Int = 2
        const val FUN_SUCCESS: Int = 3
    }

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            when (login(InputId.text.toString(), InputPassword.text.toString())) {
                ID_NULL -> InputIdLayout.error = "Enter the Id"
                PW_NULL -> InputPasswordLayout.error = "Enter the Password"
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

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            CurrentUserData.user = currentUser
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun login(id: String, pw: String): Int {
        when {
            id.isBlank() -> return ID_NULL
            pw.isBlank() -> return PW_NULL
        }

        // ProgressDialog should be changed
        val dialog = ProgressDialog(this)
        dialog.setMessage("Authenticating...")

        dialog.show()

        mAuth.signInWithEmailAndPassword(id, pw).addOnCompleteListener {
            if (it.isSuccessful) {
                CurrentUserData.user = mAuth.currentUser
            } else {
                Toast.makeText(this, "Authentication failed!\nError: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                CurrentUserData.user = null
            }
            dialog.dismiss()
        }.addOnSuccessListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        return FUN_SUCCESS
    }
}
