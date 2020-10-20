package com.gsrg.tbc.ui.fragments.challenges.challengelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gsrg.tbc.R
import com.gsrg.tbc.core.models.Challenge
import com.gsrg.tbc.core.utils.Result
import com.gsrg.tbc.databinding.FragmentChallengeListBinding
import com.gsrg.tbc.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.net.UnknownHostException

@AndroidEntryPoint
class ChallengeListFragment : BaseFragment() {

    private lateinit var binding: FragmentChallengeListBinding
    private val viewModel: ChallengeListViewModel by viewModels()
    private val adapter = ChallengeListAdapter(fun(challenge: Challenge) {
        //TODO save or send some data from challenge
        showMessage(binding.root, challenge.title)
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
        viewModel.requestChallengeList(firstRun = true)
    }

    private fun setListeners() {
        binding.refreshButton.setOnClickListener {
            viewModel.requestChallengeList()
        }
    }

    private fun setObservers() {
        viewModel.requestEventLiveData.observe(viewLifecycleOwner, Observer {
            when (val result = it.getContentIfNotHandled()) {
                is Result.Success -> {
                    hideLoading()
                }
                is Result.Error -> {
                    hideLoading()
                    val errorMessage: String = if (result.exception is UnknownHostException) {
                        getString(R.string.connection_error)
                    } else {
                        result.exception.message ?: result.exception.cause?.message ?: getString(R.string.unknown_error)
                    }
                    showMessage(binding.root, errorMessage)
                }
                is Result.Loading -> {
                    showLoading()
                }
            }
        })
        viewModel.challengeListLiveData.observe(viewLifecycleOwner, {
            binding.noDataTextView.isVisible = it.isEmpty()
            adapter.submitData(it)
        })
    }

    private fun setRecyclerView() {
        binding.recyclerView.let {
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            it.adapter = adapter
        }
    }
}