package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import androidx.lifecycle.*
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorRepository
import br.infnet.dr3_gabriel_justino_tp3.domain.Questions
import br.infnet.dr3_gabriel_justino_tp3.ui.login.MainViewModel
import com.google.firebase.auth.FirebaseAuth

class CreateEvaluatorSessionViewModelFactory(private val repository: EvaluatorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEvaluatorSessionViewModel::class.java)) {
            return CreateEvaluatorSessionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class CreateEvaluatorSessionViewModel
    constructor(private val repository: EvaluatorRepository)
    : ViewModel() {

    //private
    val _questions = MutableLiveData<List<String>>().apply { value=Questions.allQuestions }
    val questionsSize: Int = if (_questions.value != null) _questions.value!!.size else 6
    val currentPosition = MutableLiveData<Int>().apply { value = 0 }

    val isEndOfQuestionary = MutableLiveData<Boolean>().apply { false }
    val currentQuestion:LiveData<String> = Transformations.map(currentPosition) { position ->
        _questions.value?.let{
            if(position>=it.size){
                isEndOfQuestionary.postValue(true)
                "Salvar questionario?"
            }else {
                isEndOfQuestionary.postValue(false)
                it.get(position)
            }
        }
    }

      val _answers =
        MutableLiveData<MutableList<Boolean>>().apply { mutableListOf(true, false) }

      val answers:LiveData<MutableList<Boolean>> = _answers

    fun setAnswer(yesOrNo: Boolean): Boolean {
        val clone = _answers.value
        currentPosition.value?.let { position ->
            clone?.set(position, yesOrNo)
            if (clone != null) {
                _answers.postValue(clone)
                return true
            } else return false
        }
        return false


    }

    private val _text = MutableLiveData<String>().apply {
        value = "---"
    }
    val text: LiveData<String> = _text

    fun setText(txt: String) {
        _text.postValue(txt)
    }


    val answersS = MutableLiveData<String>().apply { value="000000" }
    fun setAnswer2(isYesSelected: Boolean) {
        val previousAnswers = answersS.value
        val optionSelected = if(isYesSelected) '1' else '0'
        currentPosition?.value?.let {position->
            val charAnswers:CharArray? =previousAnswers?.toCharArray()
            charAnswers?.set(position,optionSelected)
            if(charAnswers!=null) {
                val newAnswers = String(charAnswers)
                val isDiferent= answersS.value!=newAnswers
                if (isDiferent)
                    answersS.postValue(newAnswers)
            }
        }
    }
    val currentAnswer:LiveData<Boolean> = Transformations.map(currentPosition) { position ->
        val answers = answersS.value
            answers?.let{
            val charList = it.toCharArray()
            val isYes = charList[position]=='1'
            isYes
        }
    }
}