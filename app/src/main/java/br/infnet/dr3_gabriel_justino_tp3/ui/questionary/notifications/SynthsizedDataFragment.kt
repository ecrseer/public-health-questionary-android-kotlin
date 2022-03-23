package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentSynthsizeddataBinding
import br.infnet.dr3_gabriel_justino_tp3.domain.DistrictSynthData

class SynthsizedDataFragment : Fragment() {

    private lateinit var synthsizedDataViewModel: SynthsizedDataViewModel
    private var _binding: FragmentSynthsizeddataBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private fun setAdapterAgain(list: List<DistrictSynthData>?) {
        val callbackClick = { position: Int -> println("positionnnn $position")        }
        with(binding.synthDistrictsRv) {
            if (list != null) {
                adapter = ListSynthsizedDataAdapter(
                    list, callbackClick
                )
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        synthsizedDataViewModel =
            ViewModelProvider(this).get(SynthsizedDataViewModel::class.java)

        _binding = FragmentSynthsizeddataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        with(synthsizedDataViewModel) {
            text.observe(viewLifecycleOwner, Observer {
                textView.text = it
            })
            districtsSynths.observe(viewLifecycleOwner, Observer {
                setAdapterAgain(it)
            })
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}