package com.sample.reminder.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.reminder.R
import com.sample.reminder.databinding.FragmentBirthdayListBinding
import com.sample.reminder.viewmodel.BirthdayViewModel

class BirthdayListFragment : Fragment() {

    private lateinit var viewModel: BirthdayViewModel
    private lateinit var binding: FragmentBirthdayListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Bind the layout to the fragment using DataBindingUtil
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_birthday_list, container, false
        )

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this).get(BirthdayViewModel::class.java)

        // Bind ViewModel to the layout
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup RecyclerView
        val birthdayAdapter = BirthdayAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = birthdayAdapter
        }

        // Observe the list of birthdays
        viewModel.allBirthdays.observe(viewLifecycleOwner) { birthdays ->
            if (birthdays.isNullOrEmpty()) {
                // No birthdays in the list, navigate to AddBirthdayFragment
                findNavController().navigate(R.id.action_birthdayListFragment_to_addBirthdayFragment)
            } else {
                // Birthdays exist, display them in the list
                birthdayAdapter.submitList(birthdays)
            }
        }

        // Observe the FAB click event and navigate to AddBirthdayFragment
        viewModel.fabClickEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_birthdayListFragment_to_addBirthdayFragment)
        }

        return binding.root
    }
}

