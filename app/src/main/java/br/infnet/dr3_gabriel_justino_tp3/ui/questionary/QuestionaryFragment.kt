package br.infnet.dr3_gabriel_justino_tp3.ui.questionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.infnet.dr3_gabriel_justino_tp3.R
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentQuestionaryBinding

class QuestionaryFragment : Fragment() {

    private var _binding: FragmentQuestionaryBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionaryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomBtns: BottomNavigationView = binding.bottomNavBtns

        val navHostFragment = childFragmentManager.findFragmentById(R.id.questionary_navhost)
                as NavHostFragment
        val navController = findNavController(navHostFragment)

        bottomBtns.setupWithNavController(navController)

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}