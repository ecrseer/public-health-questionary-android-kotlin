package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import androidx.lifecycle.*
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorRepository
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorSession
import br.infnet.dr3_gabriel_justino_tp3.domain.Questions
import br.infnet.dr3_gabriel_justino_tp3.services.CriptoString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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



    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text



    val answersString = MutableLiveData<String>().apply { value="000000" }
    val answersChars = MutableLiveData<CharArray>().apply {
        value="000000".toCharArray() }
    fun setAnswer2(isYesSelected: Boolean) {

        val previousAnswers = answersString.value
        val optionSelected = if(isYesSelected) '1' else '0'
        currentPosition?.value?.let {position->
            val charAnswers:CharArray? = answersChars.value
                ?.apply { set(position,optionSelected) }

                charAnswers?.let {
                    val str = String(it)
                    val prevAnswers = answersString.value?.toCharArray()
                    val isDiferent= !prevAnswers.contentEquals(it) && prevAnswers!=null
                    if (isDiferent) {
                        answersString.value=str
                        answersChars.value=it
                    }
                }
            }
        }


    val currentAnswer:LiveData<Boolean> = Transformations.map(currentPosition) { position ->
        val answers = answersChars.value
            answers?.let{
                val charList = it
                if(position>=charList.size){
                    false
                }else {
                    val isYes = charList[position]=='1'
                    isYes
                }
        }
    }


    fun storeAnswers() {

        val encriptedCompanyName = CriptoString()
        encriptedCompanyName.setClearText("b2w www")

        val encriptedDistrictName = CriptoString()
        encriptedDistrictName.setClearText("rj")
        val now = Calendar.getInstance().time.time
        val session = EvaluatorSession(null,
            encriptedCompanyName, encriptedDistrictName,
            answersString.value!!,"",now)

        CoroutineScope(Dispatchers.IO).launch{
            val r = repository.addNewSession(session)
            println(r)
        }
    }
}