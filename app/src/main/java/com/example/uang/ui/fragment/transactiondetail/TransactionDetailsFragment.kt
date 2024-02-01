package com.example.uang.ui.fragment.transactiondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.uang.R
import com.example.uang.databinding.FragmentTransactionDetailsBinding
import com.example.uang.viewmodel.AddTransactionViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransactionDetailsFragment : Fragment() {

    private val args by navArgs<TransactionDetailsFragmentArgs>()
    private var _binding: FragmentTransactionDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<AddTransactionViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionDetailsBinding.inflate(inflater, container, false)

        binding.title.text = args.data.title
        binding.amount.text = "Rp ${args.data.amount}"
        binding.category.text = args.data.category
        binding.date.text = args.data.date
        binding.note.text = args.data.desc




        // Remove the current fragment from the back stack

        // Remove the current fragment from the back stack


        binding.trash.setOnClickListener {
            deleteTransaction()
        }

        binding.back.setOnClickListener {
            val argument =
                TransactionDetailsFragmentDirections.actionTransactionDetailsFragmentToDashboardFragment()
            Navigation.findNavController(binding.root).navigate(argument)
        }

        binding.edit.setOnClickListener {
            val argument =
                TransactionDetailsFragmentDirections.actionTransactionDetailsFragmentToEditTransactionFragment(
                    args.data
                )
            Navigation.findNavController(binding.root).navigate(argument)
        }

        return binding.root
    }

    private fun deleteTransaction() {
        val bottomSheet: BottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.bottom_dialog)
        bottomSheet.setContentView(R.layout.dialog_delete)

        val delete = bottomSheet.findViewById<Button>(R.id.delete)
        val cancel = bottomSheet.findViewById<Button>(R.id.cancel)

        delete?.setOnClickListener {
            args.data.id?.let { viewmodel.deleteTransactionData(it) }
            bottomSheet.dismiss()
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_transactionDetailsFragment_to_dashboardFragment)
        }
        cancel?.setOnClickListener {
            bottomSheet.dismiss()
        }

        bottomSheet.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}