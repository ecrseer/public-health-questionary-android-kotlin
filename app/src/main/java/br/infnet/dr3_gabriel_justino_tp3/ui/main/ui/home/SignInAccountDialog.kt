package br.infnet.dr3_gabriel_justino_tp3.ui.main.ui.home

import android.util.Log
import android.widget.Toast

class SignInAccountDialog : CreateAccountDialog() {

    override fun onResume() {
        super.onResume()
        confirmButton.setText("Entrar")
        confirmButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            with(publicHealthQuestionaryApplication) {
                mAuth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(requireActivity()) { task ->
                        mUser = if (task.isSuccessful && mAuth !=null) {
                            mAuth!!.currentUser
                        } else {
                            Log.d("ERRO LOGIN/CREATE", "${task.exception!!.message}")
                            Toast.makeText(
                                this, "Falha na Autenticação",
                                Toast.LENGTH_SHORT
                            ).show()
                            null
                        }
                    }

            }
        }
    }
}