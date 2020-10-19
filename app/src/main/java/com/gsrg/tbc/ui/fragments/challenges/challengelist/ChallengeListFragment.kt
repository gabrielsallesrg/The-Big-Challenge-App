package com.gsrg.tbc.ui.fragments.challenges.challengelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gsrg.tbc.R
import com.gsrg.tbc.databinding.FragmentChallengeListBinding
import com.gsrg.tbc.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeListFragment : BaseFragment() {

    private lateinit var binding: FragmentChallengeListBinding
    private val viewModel: ChallengeListViewModel by viewModels()
    private val adapter = ChallengeListAdapter(fun(challenge: Boolean) {
        //TODO save or send some data from challenge
        findNavController().navigate(R.id.action_challengeListFragment_to_challengeDetailsFragment)
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeListBinding.inflate(inflater, container, false)
        setListeners()
        setRecyclerView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
    }

    private fun setListeners() {}

    private fun setObservers() {}

    private fun setRecyclerView() {
        binding.recyclerView.let {
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            it.adapter = adapter
        }
        //TODO remove this line
        adapter.submitData(listOf(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true))
    }
}