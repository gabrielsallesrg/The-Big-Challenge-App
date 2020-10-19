package com.gsrg.tbc.ui.fragments.challenges.challengelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.tbc.R

class ChallengeListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)

    fun bind(description: String) {
        descriptionTextView.text = description
    }

    companion object {
        fun create(parent: ViewGroup): ChallengeListViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.challenge_list_item, parent, false)
            return ChallengeListViewHolder(view)
        }
    }
}