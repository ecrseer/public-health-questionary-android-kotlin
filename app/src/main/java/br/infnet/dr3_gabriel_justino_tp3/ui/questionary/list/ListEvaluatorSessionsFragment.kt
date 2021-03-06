package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.infnet.dr3_gabriel_justino_tp3.MainActivityViewModel
import br.infnet.dr3_gabriel_justino_tp3.PublicHealthQuestionaryApplication
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentEvaluatorsessionsListBinding
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorSession

class ListEvaluatorSessionsFragment : Fragment() {

    //private lateinit var listEvaluatorSessionsViewModel: ListEvaluatorSessionsViewModel
    private val listEvaluatorSessionsViewModel: ListEvaluatorSessionsViewModel by viewModels{
        val application = requireActivity().application as PublicHealthQuestionaryApplication
        ListEvaluatorSessionsViewModelFactory(application.repository)
    }
    private var _binding: FragmentEvaluatorsessionsListBinding? = null

    val activityViewModel: MainActivityViewModel by activityViewModels()
    lateinit var publicHealthQuestionaryApplication: PublicHealthQuestionaryApplication

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private fun setAdapterAgain(sessionList: List<EvaluatorSession>?) {
        val navToSession = { position: Int ->
            val action = ListEvaluatorSessionsFragmentDirections
                .actionNavigationDashboardToNavigationHome(position.toString())

            findNavController().navigate(action)
            println("positionnnn $position")
        }
        with(binding.evaluatorsessionsRv) {
            if (sessionList != null) {
                adapter = ListEvaluatorSessionsAdapter(
                    sessionList, navToSession
                )
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*listEvaluatorSessionsViewModel =
            ViewModelProvider(this).get(ListEvaluatorSessionsViewModel::class.java)
*/
        _binding = FragmentEvaluatorsessionsListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        listEvaluatorSessionsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        with(listEvaluatorSessionsViewModel){
            allSessions.observe(viewLifecycleOwner, Observer {
                setAdapterAgain(it)
            })
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener {
            val application = requireActivity().application as PublicHealthQuestionaryApplication
            application.mAuth?.apply{
                signOut()
                activityViewModel.isLoggedIn.postValue(false)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}