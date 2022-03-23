package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import br.infnet.dr3_gabriel_justino_tp3.PublicHealthQuestionaryApplication
import br.infnet.dr3_gabriel_justino_tp3.R
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentCreateEvaluatorSessionBinding
import com.google.android.material.snackbar.Snackbar

class CreateEvaluatorSessionFragment : Fragment() {

    ///private lateinit var createEvaluatorSessionViewModel: CreateEvaluatorSessionViewModel
    private val createSessionViewModel: CreateEvaluatorSessionViewModel by viewModels {
        val application: PublicHealthQuestionaryApplication =
            requireActivity().application as PublicHealthQuestionaryApplication

        CreateEvaluatorSessionViewModelFactory(application.repository)
    }
    private var _binding: FragmentCreateEvaluatorSessionBinding? = null
    private val binding get() = _binding!!

    val args: CreateEvaluatorSessionFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateEvaluatorSessionBinding.inflate(inflater, container, false)
        val textView: TextView = binding.textHome

        with(createSessionViewModel) {
            text.observe(viewLifecycleOwner, Observer {
                textView.text = it
            })
            actionState.observe(viewLifecycleOwner, Observer { action ->
                if (action === PossibleActions.creating) {
                    binding.questionsViewpager.currentItem = 0
                }
                if (action === PossibleActions.created) {
                    Snackbar.make(
                        requireActivity().findViewById(R.id.questionary_navhost),
                        "Respostas salvas com sucesso!", Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                }
            })
            selectedSessionId.observe(viewLifecycleOwner, Observer { it->
                it.let { selectedId->
                    if (selectedId != null) {
                        updateSelectedSession(selectedId)
                    }
                }
            })

        }



        return binding.root
    }



    private fun changePage(value: Int) {
        with(binding.questionsViewpager as ViewPager2) { currentItem += value }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedPosition = args.sessionPosition.toInt()
        if(selectedPosition>=0){
            createSessionViewModel.selectedSessionId.postValue(selectedPosition.toLong())

        }
        with(binding.questionsViewpager as ViewPager2) {
            val size = createSessionViewModel.questionsSize
            adapter = QuestionsAdapter(childFragmentManager, lifecycle, size + 1)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    createSessionViewModel.currentAnswerPosition.postValue(position)
                    super.onPageSelected(position)
                }
            })
        }
        binding.previousquestionBtn.setOnClickListener { changePage(-1) }
        binding.nextquestionBtn.setOnClickListener { changePage(1) }
        binding.txtSessionDistrict.doAfterTextChanged {
            it?.let{
                val district = it.toString()
                createSessionViewModel.setDistrict(district)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}