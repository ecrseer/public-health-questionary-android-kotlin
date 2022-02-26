package br.infnet.dr3_gabriel_justino_tp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.infnet.dr3_gabriel_justino_tp3.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}