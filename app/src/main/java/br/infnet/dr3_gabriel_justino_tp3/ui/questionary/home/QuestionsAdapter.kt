package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuestionsAdapter (fa: FragmentManager, lifecycl: Lifecycle,
                        var tamanho:Int?) : FragmentStateAdapter(fa,lifecycl) {
    override fun getItemCount(): Int = tamanho?: 2

    fun changeSize(novo:Int){
        if(tamanho!=novo){
            tamanho = novo;
            notifyDataSetChanged()
        }
    }
    private fun questionOfPosition(position: Int): Fragment {
        val frag = QuestionFragment.newInstance()
        frag.arguments = bundleOf("posicaoQuestaoSelecionada" to position)
        return frag;
    }


    override fun createFragment(position: Int): Fragment {
        return questionOfPosition(position);
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }




}