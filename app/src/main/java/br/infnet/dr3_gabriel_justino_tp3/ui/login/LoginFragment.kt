package br.infnet.dr3_gabriel_justino_tp3.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.infnet.dr3_gabriel_justino_tp3.MainActivityViewModel
import br.infnet.dr3_gabriel_justino_tp3.PublicHealthQuestionaryApplication
import br.infnet.dr3_gabriel_justino_tp3.databinding.LoginFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var publicHealthQuestionaryApplication: PublicHealthQuestionaryApplication


    private val viewModel: MainViewModel by viewModels {
        val application: PublicHealthQuestionaryApplication =
            requireActivity().application as PublicHealthQuestionaryApplication

        MainViewModelFactory(application.repository)
    }
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.main_fragment, container, false)
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun addSession(textView: TextView) {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.add().also {
                withContext(Dispatchers.Main) {
                    println("$it")
                    textView.setText("$it")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allSessionsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                println("$it")
            }
        })
        binding.message.setOnClickListener { mview ->
            viewModel.readFirstCompanyName()
        }
        binding.createAccountBtn.setOnClickListener {

            CreateAccountDialog().show(childFragmentManager,"criar ")
        }
        binding.signinButton.setOnClickListener {
            SignInAccountDialog().show(childFragmentManager,"entrar")
        }

    }


}