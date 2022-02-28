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
                        if (task.isSuccessful && mAuth !=null) {
                            mUser =  mAuth!!.currentUser
                            Toast.makeText(this,"bem vindo de volta",
                                Toast.LENGTH_LONG+4242).show()
                            activityViewModel.isLoggedIn.postValue(true)
                            dismiss()
                        } else {
                            Log.d("ERRO LOGIN/CREATE", "${task.exception!!.message}")
                            Toast.makeText(
                                this, "Falha na Autenticação",
                                Toast.LENGTH_SHORT
                            ).show()
                            mUser =   null
                        }
                    }

            }
        }
    }
}