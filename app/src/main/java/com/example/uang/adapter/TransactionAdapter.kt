package com.example.uang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.uang.databinding.ItemTransactionBinding
import com.example.uang.room.entities.IncomeEntities

class TransactionAdapter: RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {
    private var transaction = emptyList<IncomeEntities>()



    class MyViewHolder(private val binding: ItemTransactionBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: IncomeEntities) {
                binding.data = data
                binding.executePendingBindings()
            }

        companion object {
            fun from(parent: ViewGroup) : MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTransactionBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionAdapter.MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.MyViewHolder, position: Int) {
        val data = transaction[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return transaction.size
    }


    fun setData(newData: List<IncomeEntities>) {
        val recipeDiffUtil = com.example.util.DiffUtil(transaction, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipeDiffUtil)
        transaction = newData

        /*Mengupdate perbedaan yang ada*/
        diffUtilResult.dispatchUpdatesTo(this)
    }
}