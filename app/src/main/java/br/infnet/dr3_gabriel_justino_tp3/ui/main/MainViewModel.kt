package br.infnet.dr3_gabriel_justino_tp3.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorRepository
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorSession
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MainViewModelFactory(private val repository: EvaluatorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class MainViewModel(private val repository: EvaluatorRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    suspend fun add(): Long {
        val test =  EvaluatorSession(null,"petrobras","rj",mutableListOf(""))
        var addedSessionId = 0L
        coroutineScope {
            addedSessionId = async { repository.addNewSession(test) }.await()
        }
        return addedSessionId
    }
}