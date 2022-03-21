package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.infnet.dr3_gabriel_justino_tp3.PublicHealthQuestionaryApplication
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
    private lateinit var questionTxt: TextView
    private lateinit var createSessionBtn: Button

    private lateinit var application: PublicHealthQuestionaryApplication


    private val createSessionViewModel by viewModels<CreateEvaluatorSessionViewModel>({
        requireParentFragment()
    })
    private val viewModel:QuestionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionaryBinding.inflate(inflater, container, false)
        application=requireActivity().application as PublicHealthQuestionaryApplication
        // nao consigo usar viewbinding aqui
        // return binding.root
        return inflater.inflate(R.layout.question_fragment, container, false)
    }

    private fun getViewFields(view: View) {
        with(view) {
            questionTxt = findViewById(R.id.txt_question)

            optionsRadioGroup = findViewById(R.id.answers_radiogroup)
            yesRadioButton = findViewById(R.id.yes_radiobtn)
            noRadioButton = findViewById(R.id.no_radiobtn)
            createSessionBtn = findViewById(R.id.registersession_btn)
        }

    }
    private fun setupQuestionViewModel(){
        //viewModel.currentAnswer.post
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupQuestionViewModel()
        with(createSessionViewModel){
            currentQuestion.observe(viewLifecycleOwner, Observer {
                it?.let {
                    questionTxt.setText(it)
                }
            })
            isEndOfQuestionary.observe(viewLifecycleOwner, Observer { isEnd ->
                if (isEnd) {
                    optionsRadioGroup.visibility = View.GONE
                    createSessionBtn.visibility = View.VISIBLE
                } else {
                    optionsRadioGroup.visibility = View.VISIBLE
                    createSessionBtn.visibility = View.GONE
                }
            })

        }


        with(view) {
            getViewFields(this)
            optionsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                val isYesSelected = checkedId == R.id.yes_radiobtn
                ///application.mUser.email
                createSessionViewModel.setAnswer2(isYesSelected)

            }
            createSessionBtn.setOnClickListener {
                with(createSessionViewModel){
                    actionState.postValue(PossibleActions.creating)
                    storeAnswers()
                }
            }
        }


    }

    private fun resetQuestionText() {
        questionTxt?.let {
            it.setText("---")
        }
    }

    override fun onResume() {
        super.onResume()
        createSessionViewModel.currentAnswer.let {liveData->
            liveData.value?.let{isYes->
                val shouldMarkYes = !yesRadioButton.isChecked && isYes
                if(shouldMarkYes)
                    yesRadioButton.isChecked = true

                val shouldMarkNo = !noRadioButton.isChecked && !isYes
                if(shouldMarkNo)
                    noRadioButton.isChecked = true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        resetQuestionText()
    }


}