package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.infnet.dr3_gabriel_justino_tp3.domain.Questions


class CreateEvaluatorSessionViewModel : ViewModel() {

    //private
    val _questions = MutableLiveData<List<String>>()
    val questionsSize: Int = if (_questions.value != null) _questions.value!!.size else 6
    val currentPosition = MutableLiveData<Int>().apply { value = 0 }
    val currentQuestion:LiveData<String> = Transformations.map(_questions, { question ->

        currentPosition!!.value?.let {
            question.get(it)
        }

    })
    private val _answers =
        MutableLiveData<MutableList<Boolean>>().apply { mutableListOf(true, false) }

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
}