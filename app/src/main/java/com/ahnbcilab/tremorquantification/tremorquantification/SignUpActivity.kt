package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.ProgressDialog
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.CurrentUserData
import com.ahnbcilab.tremorquantification.functions.Authentication
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    companion object {
        const val NAME_NULL: Int = 1
        const val ID_NULL: Int = 2
        const val PW_NULL: Int = 3
        const val FUN_SUCCESS: Int = 4
    }

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

        btnSignup.setOnClickListener {
            when (signUp(InputName.text.toString(), InputId.text.toString(), InputPassword.text.toString())) {
                NAME_NULL -> InputNameLayout.error = "Enter the Name"
                ID_NULL -> InputIdLayout.error = "Enter the Id"
                PW_NULL -> InputPasswordLayout.error = "Enter the Password"
            }
        }

        textLogin.setOnClickListener {
            startActivity(intent)
        }

        InputName.setOnFocusChangeListener { view, hasFocus ->
            run {
                if (!hasFocus && !InputName.text.isBlank())
                    InputNameLayout.error = null
            }
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

    private fun signUp(name: String, id: String, pw: String): Int {
        when {
            name.isBlank() -> return NAME_NULL
            id.isBlank() -> return ID_NULL
            pw.isBlank() -> return PW_NULL
        }

        // ProgressDialog should be changed
        val dialog = ProgressDialog(this)
        dialog.setMessage("Creating account...")

        dialog.show()

        mAuth.createUserWithEmailAndPassword(id, pw).addOnCompleteListener {
            if (it.isSuccessful) {
                CurrentUserData.user = mAuth.currentUser
            }
            else {
                CurrentUserData.user = null
                Toast.makeText(this, "Create account failed!\nError: ${it.exception?.message}", Toast.LENGTH_LONG).show()
            }
            dialog.dismiss()
        }.addOnSuccessListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        return FUN_SUCCESS
    }
}
