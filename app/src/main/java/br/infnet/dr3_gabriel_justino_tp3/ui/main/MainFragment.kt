package br.infnet.dr3_gabriel_justino_tp3.ui.main

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.infnet.dr3_gabriel_justino_tp3.R

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.infnet.dr3_gabriel_justino_tp3.PublicHealthQuestionaryApplication
import br.infnet.dr3_gabriel_justino_tp3.databinding.MainFragmentBinding
import br.infnet.dr3_gabriel_justino_tp3.ui.main.ui.home.CreateAccountDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var publicHealthQuestionaryApplication: PublicHealthQuestionaryApplication


    //private lateinit var viewModel: MainViewModel

    private val viewModel: MainViewModel by viewModels {
        val application: PublicHealthQuestionaryApplication =
            requireActivity().application as PublicHealthQuestionaryApplication

        MainViewModelFactory(application.repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.inflate(inflater, container, false)
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
        binding.button.setOnClickListener {
            /*val startQ = Intent(requireActivity(),QuestionaryActivity::class.java)
            startActivity(startQ)*/
            CreateAccountDialog().show(childFragmentManager,"criar ")
        }

    }


}