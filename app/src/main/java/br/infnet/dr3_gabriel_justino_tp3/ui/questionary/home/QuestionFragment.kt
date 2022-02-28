package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.infnet.dr3_gabriel_justino_tp3.R
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentQuestionaryBinding

class QuestionFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionFragment()
    }

    private var _binding: FragmentQuestionaryBinding? = null
    private val binding get() = _binding!!


    private lateinit var optionsRadioGroup: RadioGroup
    private lateinit var yesRadioButton: RadioButton
    private lateinit var noRadioButton: RadioButton
    private lateinit var questionTxt:TextView


    private val createSessionViewModel by viewModels<CreateEvaluatorSessionViewModel>({
        requireParentFragment()
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionaryBinding.inflate(inflater, container, false)

        // nao consigo usar viewbinding aqui
        // return binding.root
        return inflater.inflate(R.layout.question_fragment, container, false)
    }
    private fun getViewFields(view:View){
        with(view){
            questionTxt = findViewById(R.id.txt_question)

            optionsRadioGroup = findViewById(R.id.answers_radiogroup)
            yesRadioButton = findViewById(R.id.yes_radiobtn)
            noRadioButton = findViewById(R.id.no_radiobtn)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createSessionViewModel.currentQuestion.observe(viewLifecycleOwner, Observer {
            it?.let{
                questionTxt.setText(it)
            }
        })
        with(view) {
           getViewFields(this)
           optionsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
               val isYesSelected = checkedId == R.id.yes_radiobtn
               createSessionViewModel.setAnswer(isYesSelected)
           }
        }


    }


}