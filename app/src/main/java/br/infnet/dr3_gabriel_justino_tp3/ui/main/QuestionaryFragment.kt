package br.infnet.dr3_gabriel_justino_tp3.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import br.infnet.dr3_gabriel_justino_tp3.MainActivity
import br.infnet.dr3_gabriel_justino_tp3.R
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentQuestionaryBinding
import com.google.android.material.navigation.NavigationView

class QuestionaryFragment : Fragment() {

    private lateinit var binding: FragmentQuestionaryBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionaryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomBtns: BottomNavigationView = binding.bottomNavBtns

        val navHostFragment = childFragmentManager.findFragmentById(R.id.questionary_navhost)
                as NavHostFragment
        val navController = findNavController(navHostFragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        bottomBtns.setupWithNavController(navController)
       /* val navController = navHostFragment.navController
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        val navHost = view.findViewById<NavHostFragment>(R.id.questionary_navhost)
        val navController = findNavController()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //val appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(requireActivity().supportFragmentManager., appBarConfiguration)
        setupWithNavController(navHost,navController)
        bottomBtns.setupWithNavController(navController)*/

    }

}