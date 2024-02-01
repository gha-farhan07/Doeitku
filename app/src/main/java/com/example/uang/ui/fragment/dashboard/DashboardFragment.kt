package com.example.uang.ui.fragment.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uang.R
import com.example.uang.adapter.TransactionAdapter
import com.example.uang.databinding.FragmentDashboardBinding
import com.example.uang.viewmodel.AddTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<AddTransactionViewModel>()
    private val mAdapter by lazy { TransactionAdapter() }
    private var totalIncome: Double = 0.0
    private var totalAmount = 0.0
    private var totalExpense: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpRecyclerView()
        loadDatabaseFromCache()
    }

    private fun setUpUI() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addTranscationActivity)
        }
    }

    private fun setUpRecyclerView() {
        binding.recylerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadDatabaseFromCache() {
        totalIncome = 0.0
        totalExpense = 0.0
        totalAmount = 0.0
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.readTransactionData.observe(viewLifecycleOwner) { transactions ->
                if (transactions.isNotEmpty()) {
                    mAdapter.setData(transactions)

                    for (transaction in transactions) {
                        when (transaction.type) {
                            "Expense" -> totalExpense += transaction.amount.toDouble()
                            "Income" -> totalIncome += transaction.amount.toDouble()
                        }
                    }

                    updateUI()
                }
            }
        }
    }

    private fun updateUI() {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

        binding.expenseAmountTextview.text = currencyFormat.format(totalExpense)
        binding.incomeAmountTextview2.text = currencyFormat.format(totalIncome)
        totalAmount = totalIncome - totalExpense
        binding.totalBalanceAmount.text = currencyFormat.format(totalAmount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
