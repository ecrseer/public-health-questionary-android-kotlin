package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import androidx.lifecycle.*
import br.infnet.dr3_gabriel_justino_tp3.domain.DistrictSynthData
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorRepository
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorSession
import br.infnet.dr3_gabriel_justino_tp3.domain.Questions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object PossibleActions {
    const val creating = "creatingSession"
    const val created = "createdSession"
}

class CreateEvaluatorSessionViewModelFactory(private val repository: EvaluatorRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEvaluatorSessionViewModel::class.java)) {
            return CreateEvaluatorSessionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class CreateEvaluatorSessionViewModel
constructor(private val repository: EvaluatorRepository) : ViewModel() {

    //private
    val _questions = MutableLiveData<List<String>>().apply { value = Questions.allQuestions }
    val questionsSize: Int = if (_questions.value != null) _questions.value!!.size else 6

    val selectedSessionId = MutableLiveData<Long?>().apply { value = null }

    //val currentSession:LiveData<EvaluatorSession> = repository.getOneSessionById(selectedSession).asLiveData()
    val currentSession = MutableLiveData<EvaluatorSession>()

    fun updateSelectedSession(selectedId: Long) {
        viewModelScope.launch {
            val session = repository.getOneSessionById(selectedId)
            currentSession.postValue(session)

            val answers = session.answers
            answersString.postValue(answers)
            answersChars.postValue(answers.toCharArray())
        }
    }


    val currentAnswerPosition = MutableLiveData<Int>().apply { value = 0 }
    val isEndOfQuestionary = MutableLiveData<Boolean>().apply { false }

    val currentQuestion: LiveData<String> = Transformations.map(currentAnswerPosition) { position ->
        _questions.value?.let { questions ->
            if (position >= questions.size) {
                isEndOfQuestionary.postValue(true)
                "Salvar questionario?"
            } else {
                isEndOfQuestionary.postValue(false)
                questions.get(position)
            }
        }
    }

    val actionState = MutableLiveData<String>().apply { value = "" }


    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    //val answers = repository.get




    val answersString = MutableLiveData<String>().apply { value = "000000" }
    val answersChars = MutableLiveData<CharArray>().apply {
        value = "000000".toCharArray()
    }

    fun setAnswer2(isYesSelected: Boolean) {

        val previousAnswers = answersString.value
        val optionSelected = if (isYesSelected) '1' else '0'

        currentAnswerPosition?.value?.let { position ->
            val charAnswers: CharArray? = answersChars.value
                ?.apply { set(position, optionSelected) }

            charAnswers?.let {
                val str = String(it)
                val prevAnswers = answersString.value?.toCharArray()
                val isDiferent = !prevAnswers.contentEquals(it) && prevAnswers != null
                if (isDiferent) {
                    answersString.value = str
                    answersChars.value = it
                }
            }
        }
    }


    val currentAnswer = Transformations.switchMap(answersChars) { answersChars ->
        val answers = answersChars
         currentAnswerPosition.value?.let{ position->
            answers.let {
                val charList = it
                if (position >= charList.size) {
                    MutableLiveData<Boolean>().apply { value=false }
                } else {
                    val isYes = charList[position] == '1'
                    MutableLiveData<Boolean>().apply { value=isYes }
                }
            }

        }
    }

    val _chosenDistrict = MutableLiveData<String>().apply { value="" }
    fun setDistrict(txt:String){
        viewModelScope.launch {
            _chosenDistrict.postValue(txt)
        }
    }

    fun storeAnswers() {
        val district = _chosenDistrict.value!!
        val session = EvaluatorSession.newEmpty(district)

        CoroutineScope(Dispatchers.IO).launch {
            val createdId = repository.addNewSession(session)
            if (createdId != -1L) actionState.postValue(PossibleActions.created)
            val syntDataCollection= Firebase.firestore.collection("synthesizedData")
            val task = syntDataCollection.document("$district").get()
            task.addOnSuccessListener {doc->
                val districtSynthData = doc.toObject<DistrictSynthData>()
                if(districtSynthData==null){
                    var newDistrictSynthData = DistrictSynthData(
                        district,district,0,
                        Questions.emptyAnswersIntList)

                    val task = syntDataCollection
                        .document("$district")
                        .set(newDistrictSynthData)
                }
            }
            task.addOnFailureListener {
                println(it)
            }

            val taskGetDistricts = syntDataCollection.get()
            taskGetDistricts.addOnSuccessListener { districts->
            val districtsSynth = districts.toObjects<DistrictSynthData>()
            districtsSynth?.let{

            }

            }


        }
    }
}