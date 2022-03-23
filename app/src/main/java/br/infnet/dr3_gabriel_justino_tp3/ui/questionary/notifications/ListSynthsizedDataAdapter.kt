package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentSynthsizeddataItemBinding
import br.infnet.dr3_gabriel_justino_tp3.domain.DistrictSynthData
import br.infnet.dr3_gabriel_justino_tp3.domain.Questions
import java.util.*

class ListSynthsizedDataAdapter(
    private var list: List<DistrictSynthData>,
    val clickCallback:(Int)->Unit
) : RecyclerView.Adapter<ListSynthsizedDataAdapter.ViewHolder>() {

    fun mudarLista(listaNotasImgNova: List<DistrictSynthData>){
        list = listaNotasImgNova
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val minhaBindingView = FragmentSynthsizeddataItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        val imagemItemViewHolder = ViewHolder(minhaBindingView,clickCallback)

        return imagemItemViewHolder

    }
    private fun generateResumeInText(districtSynthData: DistrictSynthData): String {
        var inText = ""
        districtSynthData.positiveAnswers?.let{ positiveList->
            districtSynthData.answersTotal?.let { totalList->
                for((index,answerTotal) in totalList.withIndex()){
                    val question = Questions.allQuestions[index]

                    val positiveTotal= positiveList[index]
                    val positiveAverage = positiveTotal * 100 / answerTotal
                    inText+= "$question \n $positiveAverage % das respostas são positivas \n\n"
                }

            }

        }
        return inText
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        val title = item.district
        holder.title.text = title
        holder.resume.text = generateResumeInText(item)

    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(binding: FragmentSynthsizeddataItemBinding, clickCallback:(Int)->Unit ) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.txtSynthsizedDistrictTitle
        val resume: TextView = binding.txtSynthsizedDistrictResume
        val background: CardView = binding.cardvSynthsizedBackground
        init{
            background.setOnClickListener {
                clickCallback(adapterPosition)
            }
        }

        override fun toString(): String {
            return super.toString() + " '"+title.text
        }

    }




}