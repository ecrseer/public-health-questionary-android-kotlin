package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import br.infnet.dr3_gabriel_justino_tp3.PublicHealthQuestionaryApplication
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentCreateEvaluatorSessionBinding
import br.infnet.dr3_gabriel_justino_tp3.domain.Questions

class CreateEvaluatorSessionFragment : Fragment() {

    ///private lateinit var createEvaluatorSessionViewModel: CreateEvaluatorSessionViewModel
    private val createSessionViewModel: CreateEvaluatorSessionViewModel by viewModels {
        val application: PublicHealthQuestionaryApplication =
            requireActivity().application as PublicHealthQuestionaryApplication

        CreateEvaluatorSessionViewModelFactory(application.repository)
    }
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


        val textView: TextView = binding.textHome

        with(createSessionViewModel){
            text.observe(viewLifecycleOwner, Observer {
                textView.text = it
            })


            _questions.observe(viewLifecycleOwner, Observer {
                it?.let{
                    val d = 0
                }
            })
            currentPosition.observe(viewLifecycleOwner, Observer {
                it?.let{
                    val d = 0
                }
            })
            answers.observe(viewLifecycleOwner, Observer {
                it.let{

                }
            })

        }



        return binding.root
    }
    private fun changePage(value:Int){
        with(binding.questionsViewpager as ViewPager2){ currentItem+=value }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.questionsViewpager as ViewPager2){
            val size = createSessionViewModel.questionsSize
            adapter = QuestionsAdapter(childFragmentManager,lifecycle,size+1)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    createSessionViewModel.currentPosition.postValue(position)
                    super.onPageSelected(position)
                }
            })
        }
        binding.previousquestionBtn.setOnClickListener {changePage(-1)}
        binding.nextquestionBtn.setOnClickListener { changePage(1)}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}