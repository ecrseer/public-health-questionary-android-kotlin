package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import androidx.lifecycle.*
import br.infnet.dr3_gabriel_justino_tp3.domain.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
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
        val optionSelected = if (isYesSelected) '9' else '1'

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


    val currentAnswer = Transformations.map(answersChars) { answersChars ->
        val answers = answersChars
        currentAnswerPosition.value?.let { position ->
            answers.let {charList->

                if (position >= charList.size) {
                    '0'
                } else {
                    charList[position]
                }
            }

        }
    }

    val _chosenDistrict = MutableLiveData<String>().apply { value = "" }
    fun setDistrict(txt: String) {
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

            val districtSyntData = DistrictSynthDataRepository
                .districtSyntData
                .document("$district")
            val taskGetDistricts = districtSyntData.get()

            taskGetDistricts.addOnSuccessListener { doc ->
                createSynthsizedDataIfNotExistsOnFirestore(doc, district,districtSyntData)
                districtSyntData.get().addOnSuccessListener { doc ->
                    addAnswersStatisticsToSynthsizedDataOnFirestore(doc, districtSyntData)
                }
            }
            taskGetDistricts.addOnFailureListener {
                println(it)
            }


        }
    }

    private fun addAnswersStatisticsToSynthsizedDataOnFirestore(
        doc: DocumentSnapshot?,
        districtSyntData: DocumentReference
    ) {
        doc?.let {
            val districtObjData = doc.toObject<DistrictSynthData>()
            districtObjData?.let { districtData ->
                answersString.value?.let { answrs ->
                    districtData.setSynthsizedDataByStringAnswers(answrs)
                }

                districtSyntData.set(districtData)

            }

        }
    }

    private fun createSynthsizedDataIfNotExistsOnFirestore(
        doc: DocumentSnapshot, district: String, districtSyntData: DocumentReference
    ) {
        val districtSynthDataToObj = doc.toObject<DistrictSynthData>()
        if (districtSynthDataToObj == null) {
            var newDistrictSynthData = DistrictSynthData(
                district, district, 0,
                Questions.emptyAnswersIntList,
                Questions.emptyAnswersIntList
            )

            val task = districtSyntData.set(newDistrictSynthData)
        }

    }
}