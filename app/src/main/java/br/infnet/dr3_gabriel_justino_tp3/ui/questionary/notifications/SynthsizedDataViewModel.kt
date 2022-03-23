package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.dr3_gabriel_justino_tp3.domain.DistrictSynthData
import br.infnet.dr3_gabriel_justino_tp3.domain.DistrictSynthDataRepository
import br.infnet.dr3_gabriel_justino_tp3.domain.Questions
import com.google.firebase.firestore.ktx.toObjects


class SynthsizedDataViewModel : ViewModel() {
    val districtsSynths = MutableLiveData<MutableList<DistrictSynthData>>()

    init {
        val taskLoadAll  = DistrictSynthDataRepository
            .districtSyntData.addSnapshotListener { snapshot, error ->
                val isChanged = snapshot?.documentChanges?.size?.let { it>0 }
                if(isChanged !=null && isChanged) run {
                    val districtsData = snapshot.toObjects<DistrictSynthData>()
                    districtsSynths.postValue(districtsData.toMutableList())

                }
            }

    }

    private val _text = MutableLiveData<String>().apply {
        value = "Dados de todas avaliaçoes"
    }
    val text: LiveData<String> = _text
}