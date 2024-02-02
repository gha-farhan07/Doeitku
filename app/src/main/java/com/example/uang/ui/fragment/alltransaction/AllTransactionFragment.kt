package com.example.uang.ui.fragment.alltransaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uang.R
import com.example.uang.adapter.TransactionAdapter
import com.example.uang.databinding.FragmentAllTransactionBinding
import com.example.uang.viewmodel.AddTransactionViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import org.eazegraph.lib.charts.PieChart
import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 * Use the [AllTransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AllTransactionFragment : Fragment() {

    private val viewmodel by viewModels<AddTransactionViewModel>()
    lateinit var mPie: PieChart
    private var _binding: FragmentAllTransactionBinding? = null
    private val binding get() = _binding!!
    private var month = ""
    private var year = 0
    private var monthInt = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllTransactionBinding.inflate(inflater, container, false)


        return binding.root


    }


    private fun allTransaction() {
        binding.apply {

            transactionRecyclerView.visibility = View.VISIBLE
            selectors.visibility = View.GONE
            monthlyCard.visibility = View.GONE
            yearSpinner.visibility = View.GONE
            text1.visibility = View.GONE

            viewmodel.readTransactionData.observe(viewLifecycleOwner) { transactionList ->
                if (transactionList.isNotEmpty()) {
                    noTransactionsDoneText.text = "No Transaction History"
                    noTransactionsDoneText.visibility = View.VISIBLE
                    transactionRecyclerView.visibility = View.GONE
                } else {
                    noTransactionsDoneText.visibility = View.GONE
                    transactionRecyclerView.visibility = View.VISIBLE
                    transactionRecyclerView.adapter = TransactionAdapter()
                    transactionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                }

            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun showMonthlyTransactions() {
        binding.apply {
            text.text = "Monthly Budget"
            year = SimpleDateFormat("yyyy").format(Calendar.getInstance().time).toInt()
            val list = mutableListOf(2020)
            list.clear()
            for (i in year downTo 2020) {
                list += i
            }
            val yearAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,list)
            yearSpinner.adapter = yearAdapter
            setMonth(binding.January, binding.January)
            transactionRecyclerView.visibility = View.VISIBLE
            selectors.visibility = View.VISIBLE
            binding.monthlyCard.visibility = View.VISIBLE
            binding.yearSpinner.visibility = View.VISIBLE
            binding.text1.visibility = View.VISIBLE
            binding.title.text = "Monthly Transactions"

        }
    }

    private fun setMonth(v: View, button: MaterialButton) {
        month = button.text.toString()
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.accentColor))
        button.setStrokeColorResource(R.color.secondaryColor)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))

        when (v) {
            binding.January -> {
                removeBackground(binding.February)
                removeBackground(binding.March)
                removeBackground(binding.April)
                removeBackground(binding.May)
                removeBackground(binding.June)
                removeBackground(binding.July)
                removeBackground(binding.August)
                removeBackground(binding.September)
                removeBackground(binding.October)
                removeBackground(binding.November)
                removeBackground(binding.December)
            }
            binding.February -> {
                removeBackground(binding.January)
                removeBackground(binding.March)
                removeBackground(binding.April)
                removeBackground(binding.May)
                removeBackground(binding.June)
                removeBackground(binding.July)
                removeBackground(binding.August)
                removeBackground(binding.September)
                removeBackground(binding.October)
                removeBackground(binding.November)
                removeBackground(binding.December)
            }
            binding.March -> {
                removeBackground(binding.February)
                removeBackground(binding.January)
                removeBackground(binding.April)
                removeBackground(binding.May)
                removeBackground(binding.June)
                removeBackground(binding.July)
                removeBackground(binding.August)
                removeBackground(binding.September)
                removeBackground(binding.October)
                removeBackground(binding.November)
                removeBackground(binding.December)
            }
            binding.April -> {
                removeBackground(binding.February)
                removeBackground(binding.March)
                removeBackground(binding.January)
                removeBackground(binding.May)
                removeBackground(binding.June)
                removeBackground(binding.July)
                removeBackground(binding.August)
                removeBackground(binding.September)
                removeBackground(binding.October)
                removeBackground(binding.November)
                removeBackground(binding.December)
            }
            binding.May -> {
                removeBackground(binding.February)
                removeBackground(binding.March)
                removeBackground(binding.April)
                removeBackground(binding.January)
                removeBackground(binding.June)
                removeBackground(binding.July)
                removeBackground(binding.August)
                removeBackground(binding.September)
                removeBackground(binding.October)
                removeBackground(binding.November)
                removeBackground(binding.December)
            }
            binding.June -> {
                removeBackground(binding.February)
                removeBackground(binding.March)
                removeBackground(binding.April)
                removeBackground(binding.May)
                removeBackground(binding.January)
                removeBackground(binding.July)
                removeBackground(binding.August)
                removeBackground(binding.September)
                removeBackground(binding.October)
                removeBackground(binding.November)
                removeBackground(binding.December)
            }
            binding.July -> {
                removeBackground(binding.February)
                removeBackground(binding.March)
                removeBackground(binding.April)
                removeBackground(binding.January)
                removeBackground(binding.June)
                removeBackground(binding.January)
                removeBackground(binding.August)
                removeBackground(binding.September)
                removeBackground(binding.October)
                removeBackground(binding.November)
                removeBackground(binding.December)
            }
            binding.August -> {
                removeBackground(binding.February)
                removeBackground(binding.March)
                removeBackground(binding.April)
                removeBackground(binding.May)
                removeBackground(binding.January)
                removeBackground(binding.July)
                removeBackground(binding.January)
                removeBackground(binding.September)
                removeBackground(binding.October)
                removeBackground(binding.November)
                removeBackground(binding.December)
            }
            binding.September -> {
                removeBackground(binding.February)
                removeBackground(binding.March)
                removeBackground(binding.April)
                removeBackground(binding.January)
                removeBackground(binding.June)
                removeBackground(binding.July)
                removeBackground(binding.August)
                removeBackground(binding.January)
                removeBackground(binding.October)
                removeBackground(binding.November)
                removeBackground(binding.December)
            }
            binding.October -> {
                removeBackground(binding.February)
                removeBackground(binding.March)
                removeBackground(binding.April)
                removeBackground(binding.May)
                removeBackground(binding.January)
                removeBackground(binding.July)
                removeBackground(binding.August)
                removeBackground(binding.September)
                removeBackground(binding.January)
                removeBackground(binding.November)
                removeBackground(binding.December)
            }
            binding.November -> {
                removeBackground(binding.February)
                removeBackground(binding.March)
                removeBackground(binding.April)
                removeBackground(binding.January)
                removeBackground(binding.June)
                removeBackground(binding.July)
                removeBackground(binding.August)
                removeBackground(binding.September)
                removeBackground(binding.October)
                removeBackground(binding.January)
                removeBackground(binding.December)
            }
            binding.December -> {
                removeBackground(binding.February)
                removeBackground(binding.March)
                removeBackground(binding.April)
                removeBackground(binding.May)
                removeBackground(binding.January)
                removeBackground(binding.July)
                removeBackground(binding.August)
                removeBackground(binding.September)
                removeBackground(binding.October)
                removeBackground(binding.November)
                removeBackground(binding.January)
            }
        }
    }

    private fun removeBackground(button: MaterialButton) {
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
        button.setIconTintResource(R.color.textColor)
        button.setStrokeColorResource(R.color.textColor)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}