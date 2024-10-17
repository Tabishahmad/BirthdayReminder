package com.sample.reminder.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sample.reminder.R
import com.sample.reminder.databinding.FragmentAddBirthdayBindingImpl
import com.sample.reminder.viewmodel.BirthdayViewModel
import java.util.Calendar

class AddBirthdayFragment : Fragment() {

    private lateinit var viewModel: BirthdayViewModel
    private lateinit var binding: FragmentAddBirthdayBindingImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_birthday, container, false
        )

        viewModel = ViewModelProvider(this).get(BirthdayViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Handle Save button click
        binding.btnSave.setOnClickListener {
            saveBirthday()
        }

        return binding.root
    }

    private fun saveBirthday() {
        // Get the name
        val name = binding.etName.text.toString()

        // Get the date from DatePicker
        val calendar = Calendar.getInstance()
        calendar.set(
            binding.datePicker.year,
            binding.datePicker.month,
            binding.datePicker.dayOfMonth
        )

        // Get the time from TimePicker
        calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.hour)
        calendar.set(Calendar.MINUTE, binding.timePicker.minute)

        // Get notification days
        val notifyBeforeDays = binding.spinnerNotifyDays.selectedItem.toString().toInt()

        // Pass all data to the ViewModel's onSaveClicked function
        viewModel.onSaveClicked(name, calendar.timeInMillis, notifyBeforeDays)

        // Navigate back to the birthday list
        findNavController().popBackStack()
    }
}
