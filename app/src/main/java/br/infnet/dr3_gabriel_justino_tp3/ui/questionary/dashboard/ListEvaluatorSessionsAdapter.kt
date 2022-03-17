package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.infnet.dr3_gabriel_justino_tp3.databinding.FragmentEvaluatorsessionItemBinding
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorSession
import java.util.*

class ListEvaluatorSessionsAdapter(
    private var listaNotasImg: List<EvaluatorSession>,
    val clickCallback:(Int)->Unit
) : RecyclerView.Adapter<ListEvaluatorSessionsAdapter.ViewHolder>() {

    fun mudarLista(listaNotasImgNova: List<EvaluatorSession>){
        listaNotasImg = listaNotasImgNova
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val minhaBindingView = FragmentEvaluatorsessionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        val imagemItemViewHolder = ViewHolder(minhaBindingView,clickCallback)

        return imagemItemViewHolder

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listaNotasImg[position]

        val date = item.timestamp?.let { Date(it) }
        holder.title.text = date.toString()

    }

    override fun getItemCount(): Int = listaNotasImg.size

    inner class ViewHolder(binding: FragmentEvaluatorsessionItemBinding, clickCallback:(Int)->Unit ) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.txtEvasessionItem
        val background: CardView = binding.cardvEvasessionBackground
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