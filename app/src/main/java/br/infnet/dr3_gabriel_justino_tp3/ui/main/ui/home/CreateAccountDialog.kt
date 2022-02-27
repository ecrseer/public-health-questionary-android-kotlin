package br.infnet.dr3_gabriel_justino_tp3.ui.main.ui.home

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import br.infnet.dr3_gabriel_justino_tp3.PublicHealthQuestionaryApplication
import br.infnet.dr3_gabriel_justino_tp3.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [CreateAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateAccountDialog : DialogFragment() {
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var publicHealthQuestionaryApplication: PublicHealthQuestionaryApplication
    private lateinit var emailField:EditText
    private lateinit var passwordField:EditText
    private lateinit var createAccountButton: Button

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
        createAccountButton=view.findViewById<Button>(R.id.create_account_btn)

        createAccountButton.setOnClickListener {
            createAccount(emailField.text.toString(),passwordField.text.toString())
        }


    }

    private fun createAccount(email: String, password: String) {
        with(publicHealthQuestionaryApplication) {
            mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(requireActivity()) { task ->
                    mUser = if (task.isSuccessful && mAuth !=null) {
                        mAuth!!.currentUser
                    } else {
                        Log.d("ERRO LOGIN/CREATE", "${ task.exception!!.message }")
                        Toast.makeText(
                            this, "Falha na Autenticação",
                            Toast.LENGTH_SHORT
                        ).show()
                        null
                    }
                    //updateUI()
                }
        }
    }

    /** The system calls this only when creating the layout in a dialog. */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
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