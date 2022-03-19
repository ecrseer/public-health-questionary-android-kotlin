package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import br.infnet.dr3_gabriel_justino_tp3.PublicHealthQuestionaryApplication
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentCreateEvaluatorSessionBinding
import com.google.android.gms.ads.*
import java.util.*

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
            answersChars.observe(viewLifecycleOwner, Observer {
                it.let{

                }
            })

        }



        return binding.root
    }
    private fun changePage(value:Int){
        with(binding.questionsViewpager as ViewPager2){ currentItem+=value }
    }
    private fun adsPubBind(view:View){
     MobileAds.initialize(requireContext())
        val adView = binding.adView
        val adBuild = AdRequest.Builder().build()

        adView.loadAd(adBuild)


        adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                println("onAdFailedToLoad::: $adError")
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
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
        adsPubBind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}