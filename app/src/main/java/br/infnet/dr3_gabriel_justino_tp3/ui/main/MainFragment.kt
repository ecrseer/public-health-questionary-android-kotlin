package br.infnet.dr3_gabriel_justino_tp3.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.infnet.dr3_gabriel_justino_tp3.R

import androidx.fragment.app.viewModels
import br.infnet.dr3_gabriel_justino_tp3.PublicHealthQuestionaryApplication

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //private lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((requireActivity().application
                as PublicHealthQuestionaryApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }



}