package com.example.uang.ui.fragment.alltransaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
import org.eazegraph.lib.models.PieModel
import java.text.SimpleDateFormat
import java.util.Calendar


@AndroidEntryPoint
class AllTransactionFragment : Fragment(), View.OnClickListener {

    private val viewmodel by viewModels<AddTransactionViewModel>()
    lateinit var mPie: PieChart
    private var _binding: FragmentAllTransactionBinding? = null
    private val binding get() = _binding!!
    private var month = ""
    private var year = 0
    private var monthInt = 1
    private val mAdapter by lazy { TransactionAdapter() }
    private var totalExpense = 0.0
    private var totalIncome: Double = 0.0
    private var totalFood = 0.0f
    private var totalShopping = 0.0f
    private var totalTransport = 0.0f
    private var totalHealth = 0.0f
    private var totalOthers = 0.0f
    private var totalAcademics = 0.0f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllTransactionBinding.inflate(inflater, container, false)


        setListener()

        when (binding.toggleSelector.checkedButtonId) {
            R.id.all -> allTransaction()
            R.id.monthly -> showMonthlyTransactions()
            R.id.yearly -> showYearlyTransactions()
        }

        binding.toggleSelector.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.all -> allTransaction()
                    R.id.monthly -> showMonthlyTransactions()
                    R.id.yearly -> showYearlyTransactions()
                }
            }

        }


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
                if (transactionList.isEmpty()) {
                    noTransactionsDoneText.text = "No Transaction History"
                    noTransactionsDoneText.visibility = View.VISIBLE
                    transactionRecyclerView.visibility = View.GONE
                } else {
                    mAdapter.setData(transactionList)
                    noTransactionsDoneText.visibility = View.GONE
                    transactionRecyclerView.visibility = View.VISIBLE
                    transactionRecyclerView.adapter = mAdapter
                    transactionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                }

            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun showMonthlyTransactions() {
        binding.apply {
            year = SimpleDateFormat("yyyy").format(Calendar.getInstance().time).toInt()
            val list = mutableListOf(2020)
            list.clear()
            for (i in year downTo 2020) {
                list += i
            }
            val yearAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, list)
            yearSpinner.adapter = yearAdapter
            setMonth(binding.January, binding.January)

            showsMonthTransaction()
            transactionRecyclerView.visibility = View.VISIBLE
            selectors.visibility = View.VISIBLE
            binding.monthlyCard.visibility = View.VISIBLE
            binding.yearSpinner.visibility = View.VISIBLE
            binding.text1.visibility = View.VISIBLE
            binding.title.text = "Monthly Transactions"
            binding.yearSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        year = binding.yearSpinner.selectedItem.toString().toInt()
                        showsMonthTransaction()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        year = binding.yearSpinner.selectedItem.toString().toInt()
                        showsMonthTransaction()
                    }
                }

        }
    }

    private fun showsMonthTransaction() {
        mPie = binding.piechart
        mPie.clearChart()
        viewmodel.readMonthlyTransactionData(monthInt, year)
            .observe(viewLifecycleOwner) { transaction ->
                if (transaction.isEmpty()) {
                    binding.noTransactionsDoneText.text = "No transaction done on $month $year "
                    binding.noTransactionsDoneText.visibility = View.VISIBLE
                    binding.monthlyCard.visibility = View.GONE
                    binding.transactionRecyclerView.visibility = View.GONE
                    binding.text1.visibility = View.GONE
                } else {
                    binding.transactionRecyclerView.adapter = mAdapter
                    mAdapter.setData(transaction)
                    binding.transactionRecyclerView.layoutManager =
                        LinearLayoutManager(requireContext())
                    binding.monthlyCard.visibility = View.VISIBLE
                    binding.noTransactionsDoneText.visibility = View.GONE
                    binding.transactionRecyclerView.visibility = View.VISIBLE
                    binding.text1.visibility = View.VISIBLE

                }

                for (i in transaction) {
                    totalExpense += i.amount.toDouble()
                    when (i.category) {
                        "Food" -> {
                            totalFood += (i.amount.toFloat())
                        }

                        "Shopping" -> {
                            totalShopping += (i.amount.toFloat())
                        }

                        "Transport" -> {
                            totalTransport += (i.amount.toFloat())
                        }

                        "Health" -> {
                            totalHealth += (i.amount.toFloat())
                        }

                        "Other" -> {
                            totalOthers += (i.amount.toFloat())
                        }

                        "Education" -> {
                            totalAcademics += (i.amount.toFloat())
                        }
                    }

                    binding.expense.text = "Rp ${totalExpense.toInt()}"
                    binding.date.text = "${month} ${year}"
                    if (totalExpense > totalIncome) {
                        binding.indicator.setImageResource(R.drawable.ic_negative_transaction)
                        binding.expense.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                    } else {
                        binding.indicator.setImageResource(R.drawable.ic_positive_amount)
                    }
                    showPiChart()

                }

            }

    }

    @SuppressLint("SimpleDateFormat")
    private fun showYearlyTransactions() {
        binding.apply {
            year = SimpleDateFormat("yyyy").format(Calendar.getInstance().time).toInt()
            val list = mutableListOf(2020)
            list.clear()
            for (i in year downTo 2020) {
                list += i
            }
            val yearAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, list)
            yearSpinner.adapter = yearAdapter
            showsYearTransaction()
            binding.transactionRecyclerView.visibility = View.VISIBLE
            binding.selectors.visibility = View.GONE
            binding.monthlyCard.visibility = View.VISIBLE
            binding.yearSpinner.visibility = View.VISIBLE
            binding.text1.visibility = View.VISIBLE
            binding.yearSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        year = binding.yearSpinner.selectedItem.toString().toInt()
                        showsYearTransaction()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        year = binding.yearSpinner.selectedItem.toString().toInt()
                        showsYearTransaction()
                    }
                }

        }
    }

    private fun showsYearTransaction() {
        mPie = binding.piechart
        mPie.clearChart()
        totalExpense = 0.0
        totalIncome = 0.0
        totalFood = 0.0f
        totalShopping = 0.0f
        totalTransport = 0.0f
        totalHealth = 0.0f
        totalOthers = 0.0f
        totalAcademics = 0.0f
        viewmodel.readYearlyTransactionData(year).observe(viewLifecycleOwner) { transactionList ->
            if (transactionList.isEmpty()) {
                binding.noTransactionsDoneText.text = "No transaction done on Year $year "
                binding.noTransactionsDoneText.visibility = View.VISIBLE
                binding.monthlyCard.visibility = View.GONE
                binding.transactionRecyclerView.visibility = View.GONE
                binding.text1.visibility = View.GONE
            } else {
                binding.monthlyCard.visibility = View.VISIBLE
                binding.noTransactionsDoneText.visibility = View.GONE
                binding.transactionRecyclerView.visibility = View.VISIBLE
                binding.text1.visibility = View.VISIBLE
                mAdapter.setData(transactionList)
                binding.transactionRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.transactionRecyclerView.adapter = mAdapter


                for (i in transactionList) {
                    totalExpense += i.amount.toDouble()
                    when (i.category) {
                        "Food" -> {
                            totalFood += (i.amount.toFloat())
                        }

                        "Shopping" -> {
                            totalShopping += (i.amount.toFloat())
                        }

                        "Transport" -> {
                            totalTransport += (i.amount.toFloat())
                        }

                        "Health" -> {
                            totalHealth += (i.amount.toFloat())
                        }

                        "Other" -> {
                            totalOthers += (i.amount.toFloat())
                        }

                        "Education" -> {
                            totalAcademics += (i.amount.toFloat())
                        }
                    }
                }
                binding.expense.text = "Rp ${totalExpense.toInt()}"
                binding.date.text = "Year: ${year}"
                if (totalExpense > totalIncome) {
                    binding.indicator.setImageResource(R.drawable.ic_negative_transaction)
                    binding.expense.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                } else {
                    binding.indicator.setImageResource(R.drawable.ic_positive_amount)
                }
                showPiChart()
            }
        }
    }

    private fun showPiChart() {
        mPie.addPieSlice(
            PieModel(
                "Food",
                totalFood,
                ContextCompat.getColor(requireContext(), R.color.yellow)
            )
        )
        mPie.addPieSlice(
            PieModel(
                "Shopping",
                totalShopping,
                ContextCompat.getColor(requireContext(), R.color.lightBlue)
            )
        )
        mPie.addPieSlice(
            PieModel(
                "Health",
                totalHealth,
                ContextCompat.getColor(requireContext(), R.color.red)
            )
        )
        mPie.addPieSlice(
            PieModel(
                "Others",
                totalOthers,
                ContextCompat.getColor(requireContext(), R.color.lightBrown)
            )
        )
        mPie.addPieSlice(
            PieModel(
                "Transport",
                totalTransport,
                ContextCompat.getColor(requireContext(), R.color.violet)
            )
        )
        mPie.addPieSlice(
            PieModel(
                "Academics",
                totalAcademics,
                ContextCompat.getColor(requireContext(), R.color.green)
            )
        )

        if (totalIncome > totalExpense) {
            mPie.addPieSlice(
                PieModel(
                    "Empty",
                    (totalIncome - (totalExpense.toFloat())).toFloat(),
                    ContextCompat.getColor(requireContext(), R.color.dominantColor)
                )
            )
        }

        mPie.startAnimation()
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


    private fun setListener() {
        binding.January.setOnClickListener(this)
        binding.February.setOnClickListener(this)
        binding.March.setOnClickListener(this)
        binding.April.setOnClickListener(this)
        binding.May.setOnClickListener(this)
        binding.June.setOnClickListener(this)
        binding.July.setOnClickListener(this)
        binding.August.setOnClickListener(this)
        binding.September.setOnClickListener(this)
        binding.October.setOnClickListener(this)
        binding.November.setOnClickListener(this)
        binding.December.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.January -> {
                setMonth(v, binding.January)
                monthInt = 1
                showsMonthTransaction()
            }

            binding.February -> {
                setMonth(v, binding.February)
                monthInt = 2
                showsMonthTransaction()
            }

            binding.March -> {
                setMonth(v, binding.March)
                monthInt = 3
                showsMonthTransaction()
            }

            binding.April -> {
                setMonth(v, binding.April)
                monthInt = 4
                showsMonthTransaction()
            }

            binding.May -> {
                setMonth(v, binding.May)
                monthInt = 5
                showsMonthTransaction()
            }

            binding.June -> {
                setMonth(v, binding.June)
                monthInt = 6
                showsMonthTransaction()
            }

            binding.July -> {
                setMonth(v, binding.July)
                monthInt = 7
                showsMonthTransaction()
            }

            binding.August -> {
                setMonth(v, binding.August)
                monthInt = 8
                showsMonthTransaction()
            }

            binding.September -> {
                setMonth(v, binding.September)
                monthInt = 9
                showsMonthTransaction()
            }

            binding.October -> {
                setMonth(v, binding.October)
                monthInt = 10
                showsMonthTransaction()
            }

            binding.November -> {
                setMonth(v, binding.November)
                monthInt = 11
                showsMonthTransaction()
            }

            binding.December -> {
                setMonth(v, binding.December)
                monthInt = 12
                showsMonthTransaction()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}