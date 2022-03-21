package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.list

import androidx.lifecycle.*
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorRepository

class ListEvaluatorSessionsViewModelFactory(
    private val repository: EvaluatorRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListEvaluatorSessionsViewModel::class.java)) {
            return ListEvaluatorSessionsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ListEvaluatorSessionsViewModel(private val repository: EvaluatorRepository) :
    ViewModel() {


//    private val allAessions = MutableLiveData<MutableList<EvaluatorSession> >()

    val allSessions = repository.getAllSessions().asLiveData()

    private val _text = MutableLiveData<String>().apply {
        value = "Meus questionarios"
    }
    val text: LiveData<String> = _text
}