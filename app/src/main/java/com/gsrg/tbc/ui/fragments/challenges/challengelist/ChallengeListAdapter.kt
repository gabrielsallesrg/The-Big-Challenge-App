package com.gsrg.tbc.ui.fragments.challenges.challengelist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChallengeListAdapter(
    private val itemClickAction: (Boolean) -> Unit
) : RecyclerView.Adapter<ChallengeListViewHolder>() {

    private var dataList: MutableList<Boolean> = mutableListOf() //TODO define properly this

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        return ChallengeListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        dataList[position].let { challenge ->
            holder.bind("first", "second")
            holder.itemView.setOnClickListener { itemClickAction.invoke(challenge) }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun submitData(challengeList: List<Boolean>) {
        if (challengeList != dataList) {
            dataList = challengeList.toMutableList()
            notifyDataSetChanged()
        }
    }
}