package com.example.uang.ui.fragment.transactiondetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.uang.R
import com.example.uang.databinding.FragmentEditTransactionBinding
import com.example.uang.room.entities.IncomeEntities
import com.example.uang.viewmodel.AddTransactionViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class EditTransactionFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentEditTransactionBinding? = null
    private val binding get() = _binding!!
    private val transactions by navArgs<EditTransactionFragmentArgs>()
    private var category = ""
    private val viewmodel by viewModels<AddTransactionViewModel>()
    var day = 0
    var year = 0
    var month = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditTransactionBinding.inflate(inflater, container, false)

        // Set the OnClickListener for edtDate
        binding.edtDate.setOnClickListener {
            setCalendar()
        }

        setData()
        addSetGridButtonListener()

        binding.addTransaction.setOnClickListener {
            addNewTransaction()
        }

        return binding.root
    }


    private fun setData() {
        binding.edtTitle.setText(transactions.dataTransaction.title)
        binding.edtDate.setText(transactions.dataTransaction.date)
        binding.edtAmount.setText(transactions.dataTransaction.amount)
        binding.editNote.setText(transactions.dataTransaction.desc)
        category = transactions.dataTransaction.category


    }


    private fun addNewTransaction() {
        try {
            val title = binding.edtTitle.text.toString()
            val amount = binding.edtAmount.text.toString()
            val note = binding.editNote.text.toString()
            val date = binding.edtDate.text.toString()

            val transaction = IncomeEntities(
                transactions.dataTransaction.id,
                type = transactions.dataTransaction.type,
                title = title,
                amount = amount.toDouble().toString(),
                desc = note,
                date = date,
                category = category,
                day = day,
                month = month,
                year = year
            )

            viewmodel.updateTransaction(transaction)
            Toast.makeText(requireContext(), "Transaction Update Successfully", Toast.LENGTH_SHORT).show()
            val dataArgs = EditTransactionFragmentDirections.actionEditTransactionFragmentToTransactionDetailsFragment(transaction)
            Navigation.findNavController(binding.root).navigate(dataArgs)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error updating transaction ${e.message}", Toast.LENGTH_SHORT).show()
        }


    }


    private fun setCalendar() {
        val cal = Calendar.getInstance()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            cal.timeInMillis = selection // Set the selected date to the Calendar

            val myFormat = "dd MMMM yyyy" // Mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.edtDate.setText(sdf.format(cal.time))

            // Extract day, month, and year from the Calendar
            day = cal[Calendar.DAY_OF_MONTH]
            month = cal[Calendar.MONTH] + 1 // Calendar months are 0-based, so add 1
            year = cal[Calendar.YEAR]
        }

        datePicker.show(childFragmentManager, "tag")
    }




    private fun addSetGridButtonListener() {
        binding.food.setOnClickListener(this)
        binding.shopping.setOnClickListener(this)
        binding.transport.setOnClickListener(this)
        binding.health.setOnClickListener(this)
        binding.academics.setOnClickListener(this)
        binding.others.setOnClickListener(this)
    }

    private fun setCategory(v: View, button: MaterialButton) {
        category = button.text.toString()
        button.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.secondaryColor
            )
        )
        button.setIconTintResource(R.color.coloronPrimary)
        button.setStrokeColorResource(R.color.coloronPrimary)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.coloronPrimary))

        when (v) {
            binding.food -> {
                removeBackground(binding.shopping)
                removeBackground(binding.transport)
                removeBackground(binding.health)
                removeBackground(binding.others)
                removeBackground(binding.academics)
            }

            binding.shopping -> {
                removeBackground(binding.food)
                removeBackground(binding.transport)
                removeBackground(binding.health)
                removeBackground(binding.others)
                removeBackground(binding.academics)
            }

            binding.transport -> {
                removeBackground(binding.shopping)
                removeBackground(binding.food)
                removeBackground(binding.health)
                removeBackground(binding.others)
                removeBackground(binding.academics)
            }

            binding.health -> {
                removeBackground(binding.shopping)
                removeBackground(binding.transport)
                removeBackground(binding.food)
                removeBackground(binding.others)
                removeBackground(binding.academics)
            }

            binding.others -> {
                removeBackground(binding.shopping)
                removeBackground(binding.transport)
                removeBackground(binding.health)
                removeBackground(binding.food)
                removeBackground(binding.academics)
            }

            binding.academics -> {
                removeBackground(binding.shopping)
                removeBackground(binding.transport)
                removeBackground(binding.health)
                removeBackground(binding.others)
                removeBackground(binding.food)
            }
        }
    }

    private fun removeBackground(button: MaterialButton) {
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
        button.setIconTintResource(R.color.accentColor)
        button.setStrokeColorResource(R.color.accentColor)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.accentColor))
    }




    override fun onClick(v: View?) {
        when (v) {
            binding.food -> {
                setCategory(v, binding.food)
            }

            binding.shopping -> {
                setCategory(v, binding.shopping)
            }

            binding.transport -> {
                setCategory(v, binding.transport)
            }

            binding.health -> {
                setCategory(v, binding.health)
            }

            binding.others -> {
                setCategory(v, binding.others)
            }

            binding.academics -> {
                setCategory(v, binding.academics)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}