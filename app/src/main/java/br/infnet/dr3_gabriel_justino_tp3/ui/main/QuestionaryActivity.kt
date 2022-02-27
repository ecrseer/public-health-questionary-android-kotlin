package br.infnet.dr3_gabriel_justino_tp3.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.infnet.dr3_gabriel_justino_tp3.R
import br.infnet.dr3_gabriel_justino_tp3.databinding.ActivityQuestionaryBinding

class QuestionaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomBtns: BottomNavigationView = binding.bottomNavBtns

        val navController = findNavController(R.id.questionary_navhost)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomBtns.setupWithNavController(navController)
    }

}