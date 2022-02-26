package br.infnet.dr3_gabriel_justino_tp3.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorRepository
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorSession
import br.infnet.dr3_gabriel_justino_tp3.services.CriptoString
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MainViewModelFactory(private val repository: EvaluatorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class MainViewModel(private val repository: EvaluatorRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    val allSessionsLiveData:LiveData<List<EvaluatorSession>>  = repository.getAllSessions().asLiveData()

    suspend fun add(): Long {
        val encriptedCompanyName = CriptoString()
        encriptedCompanyName.setClearText("b2w cococo")
        val test =  EvaluatorSession(null,encriptedCompanyName,"rj", "sdsf")
        var addedSessionId = 0L
        coroutineScope {
            addedSessionId = async { repository.addNewSession(test) }.await()
        }
        return addedSessionId
    }
    fun readFirstCompanyName(){
        val encriptedName = allSessionsLiveData.value?.first()?.companyName
        encriptedName?.let {
            println(it.getClearText())
        }
    }


}