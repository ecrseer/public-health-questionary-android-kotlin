package br.infnet.dr3_gabriel_justino_tp3.ui.login

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
                            showMessageSnack("bem vindo de volta")
                            activityViewModel.isLoggedIn.postValue(true)
                            dismiss()
                        } else {
                            Log.d("ERRO LOGIN/CREATE", "${task.exception!!.message}")
                            showMessageSnack( "Falha na Autenticação")
                            mUser =   null
                        }
                    }

            }
        }
    }
}