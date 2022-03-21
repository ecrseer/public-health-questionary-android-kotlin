package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.dr3_gabriel_justino_tp3.domain.Questions

class NotificationsViewModel : ViewModel() {
    val _questions = MutableLiveData<List<String>>().apply { value = Questions.allQuestions }
    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}