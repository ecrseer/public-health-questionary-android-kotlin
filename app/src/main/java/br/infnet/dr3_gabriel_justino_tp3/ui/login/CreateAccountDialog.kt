package br.infnet.dr3_gabriel_justino_tp3.ui.login

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import br.infnet.dr3_gabriel_justino_tp3.MainActivityViewModel
import br.infnet.dr3_gabriel_justino_tp3.PublicHealthQuestionaryApplication
import br.infnet.dr3_gabriel_justino_tp3.R
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [CreateAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class CreateAccountDialog : DialogFragment() {
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    val activityViewModel: MainActivityViewModel by activityViewModels()
    lateinit var publicHealthQuestionaryApplication: PublicHealthQuestionaryApplication
    lateinit var emailField:EditText
    lateinit var passwordField:EditText
    lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        publicHealthQuestionaryApplication =
            requireActivity().application as PublicHealthQuestionaryApplication
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.dialog_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailField=view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        passwordField=view.findViewById<EditText>(R.id.editTextTextPassword)
        confirmButton=view.findViewById<Button>(R.id.confirm_dialog_btn)
    }

    override fun onResume() {
        super.onResume()
        confirmButton.setOnClickListener {
            createAccount(emailField.text.toString(),passwordField.text.toString())
            onDestroy()
        }
    }
    fun showMessageSnack(message:String){
        Toast.makeText(
            requireContext(), "$message",
            Toast.LENGTH_SHORT+4242
        ).show()
    }

    private fun createAccount(email: String, password: String) {
        with(publicHealthQuestionaryApplication) {
            mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful && mAuth !=null) {
                        showMessageSnack("Usuario criado com sucesso! ${mUser?.uid}")
                        dismiss()
                    } else {
                        Log.d("ERRO LOGIN/CREATE", "${ task.exception!!.message }")
                        showMessageSnack("email ou senha invalidos")
                        mUser =   null
                    }
                }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        return dialog
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateAccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateAccountDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}