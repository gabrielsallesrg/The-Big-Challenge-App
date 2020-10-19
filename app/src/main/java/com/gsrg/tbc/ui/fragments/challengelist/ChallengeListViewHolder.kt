package com.gsrg.tbc.ui.fragments.challengelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.tbc.R

class ChallengeListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val firstTextView: TextView = view.findViewById(R.id.firstTextView) //TODO give proper name to these views
    private val secondTextView: TextView = view.findViewById(R.id.secondTextView)

    fun bind(first: String, second: String) { //TODO give proper name to these parameters
        firstTextView.text = first
        secondTextView.text = second
    }

    companion object {
        fun create(parent: ViewGroup): ChallengeListViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.challenge_list_item, parent, false)
            return ChallengeListViewHolder(view)
        }
    }
}