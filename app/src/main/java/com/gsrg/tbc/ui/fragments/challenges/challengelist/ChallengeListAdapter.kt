package com.gsrg.tbc.ui.fragments.challenges.challengelist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.tbc.R
import com.gsrg.tbc.core.models.Challenge

class ChallengeListAdapter(
    private val itemClickAction: (Challenge) -> Unit
) : RecyclerView.Adapter<ChallengeListViewHolder>() {

    private var dataList: MutableList<Challenge> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        return ChallengeListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        dataList[position].let { challenge ->
            val description = holder.itemView.context.getString(
                R.string.challenge_cell_text,
                challenge.title,
                challenge.description
            )

            holder.bind(description = description)
            holder.itemView.setOnClickListener { itemClickAction.invoke(challenge) }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun submitData(challengeList: List<Challenge>) {
        if (challengeList != dataList) {
            dataList = challengeList.toMutableList()
            notifyDataSetChanged()
        }
    }
}