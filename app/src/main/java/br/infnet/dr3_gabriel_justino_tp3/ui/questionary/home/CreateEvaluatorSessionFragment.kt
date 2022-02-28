package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentCreateEvaluatorSessionBinding

class CreateEvaluatorSessionFragment : Fragment() {

    ///private lateinit var createEvaluatorSessionViewModel: CreateEvaluatorSessionViewModel
    private val createSessionViewModel: CreateEvaluatorSessionViewModel by viewModels ()
    private var _binding: FragmentCreateEvaluatorSessionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     /*   createEvaluatorSessionViewModel =
            ViewModelProvider(this).get(CreateEvaluatorSessionViewModel::class.java)*/

        _binding = FragmentCreateEvaluatorSessionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        createSessionViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.questionsViewpager as ViewPager2){
            adapter = QuestionsAdapter(childFragmentManager,lifecycle,2)
        }
        //createSessionViewModel.setText("banana")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}