package br.infnet.dr3_gabriel_justino_tp3.domain

import androidx.annotation.WorkerThread
import androidx.room.*
import br.infnet.dr3_gabriel_justino_tp3.services.CriptoString
import kotlinx.coroutines.flow.Flow
import java.util.*

@Entity
data class EvaluatorSession(
    @PrimaryKey(autoGenerate = true)
    val idEvaluator:Long? = null,
    var companyName: CriptoString,
    var district:CriptoString,
    var answers: String,
    val firebaseEmail:String,
    val timestamp: Long? = null
){
    companion object Factory{

        private fun generate(district:String?): EvaluatorSession {
            val encriptedCompanyName = CriptoString()
            val districtName = district?: ""
            encriptedCompanyName.setClearText("")

            val encriptedDistrictName = CriptoString()
            encriptedDistrictName.setClearText("")
            val now = Calendar.getInstance().time.time
            val session = EvaluatorSession(
                null,
                encriptedCompanyName, encriptedDistrictName,
                "000000", "", now
            )
            return session
        }

        fun newEmpty(): EvaluatorSession =
             this.generate(null)


        fun newEmpty(district:String): EvaluatorSession =
            this.generate(district)

    }


}

@Dao
interface EvaluatorSessionDAO{
    @Query("SELECT * FROM evaluatorsession")
    fun getAll():Flow<List<EvaluatorSession>>

    @Query(value = "SELECT * FROM evaluatorsession WHERE idEvaluator = :id")
    fun getOneByIdLiveData(id:Long):Flow<EvaluatorSession>

    @Query(value = "SELECT * FROM evaluatorsession WHERE idEvaluator = :id")
    suspend fun getOneById(id:Long):EvaluatorSession

    @Insert
    fun insert(evaluatorSession: EvaluatorSession):Long

    @Update
    fun update(evaluatorSession: EvaluatorSession)
}

class EvaluatorRepository(private val evaluatorSessionDAO: EvaluatorSessionDAO){
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
     fun getAllSessions (): Flow<List<EvaluatorSession>> {
        return evaluatorSessionDAO.getAll()

    }
    suspend fun getOneSessionById(id:Long) = evaluatorSessionDAO.getOneById(id)

    //fun getOneSessionById(id:Long) = evaluatorSessionDAO.getOneByIdLiveData(id)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addNewSession (evaluatorSession: EvaluatorSession):Long{
        return evaluatorSessionDAO.insert(evaluatorSession)

    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun editSession (evaluatorSession: EvaluatorSession){
        return evaluatorSessionDAO.update(evaluatorSession)

    }

}