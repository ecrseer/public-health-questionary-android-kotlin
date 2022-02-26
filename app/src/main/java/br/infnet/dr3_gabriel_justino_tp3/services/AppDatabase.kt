package br.infnet.dr3_gabriel_justino_tp3.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorSession
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorSessionDAO
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = arrayOf(EvaluatorSession::class),
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getEvaluatorSessionDAO():EvaluatorSessionDAO
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context,scope:CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }



    }
}