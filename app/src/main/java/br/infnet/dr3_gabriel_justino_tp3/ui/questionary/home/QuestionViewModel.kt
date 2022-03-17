package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorRepository

class QuestionViewModelFactory(private val position:Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            //return QuestionViewModel(position) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class QuestionViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val currentAnswer = MutableLiveData<Char>().apply { value='0' }

}