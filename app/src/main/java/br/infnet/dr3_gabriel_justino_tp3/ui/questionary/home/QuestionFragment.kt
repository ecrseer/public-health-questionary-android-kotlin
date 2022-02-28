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

    private lateinit var viewModel: QuestionViewModel

    private val createSessionViewModel by viewModels<CreateEvaluatorSessionViewModel>({
        requireParentFragment()
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionaryBinding.inflate(inflater, container, false)


        // return binding.root
        return inflater.inflate(R.layout.question_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            optionsRadioGroup = findViewById<RadioGroup>(R.id.answers_radiogroup)

            yesRadioButton = findViewById<RadioButton>(R.id.yes_radiobtn)
            noRadioButton = findViewById<RadioButton>(R.id.no_radiobtn)
            setOnClickListener {
                val isYesSelected = optionsRadioGroup.checkedRadioButtonId == R.id.yes_radiobtn
                val optTxt = if (isYesSelected) "Sim" else "Nao"
                findViewById<TextView>(R.id.txt_question).setText(optTxt)
                createSessionViewModel.setText(optTxt)
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}