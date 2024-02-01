package com.example.uang.bindingadapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.uang.R
import com.example.uang.room.entities.IncomeEntities
import com.example.uang.ui.fragment.dashboard.DashboardFragmentDirections

class BindingAdapter {

    companion object {
        @BindingAdapter("OnTransactionClickListener")
        @JvmStatic
        fun onTransactionClickListener(
            transactionRowLayout: ConstraintLayout, dataTransaction: IncomeEntities
        ) {
            transactionRowLayout.setOnClickListener {
                try {
                    val action =
                        DashboardFragmentDirections.actionDashboardFragmentToTransactionDetailsFragment(
                            dataTransaction
                        )
                    transactionRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("OnRecipieListener", e.toString())
                }
            }
        }


        @BindingAdapter("SetIconAsCategory")
        @JvmStatic
        fun setIconCategory(
            view: View, dataTransaction: String?,
        ) {
            dataTransaction?.let {
                when (it) {
                    "Food" -> {
                        when (view) {
                            is ImageView -> {
                                view.setImageResource(R.drawable.ic_baseline_fastfood_24)
                                view.setColorFilter(
                                    ContextCompat.getColor(
                                        view.context, R.color.yellow
                                    )
                                )
                            }

                            is TextView -> {
                                view.setTextColor(
                                    ContextCompat.getColor(view.context, R.color.yellow)
                                )
                            }

                        }
                    }

                    "Shopping" -> {
                        when (view) {
                            is ImageView -> {
                                view.setImageResource(R.drawable.baseline_add_shopping_cart_24)
                                view.setColorFilter(
                                    ContextCompat.getColor(
                                        view.context, R.color.lightBlue
                                    )
                                )
                            }

                            is TextView -> {
                                view.setTextColor(
                                    ContextCompat.getColor(view.context, R.color.lightBlue)
                                )
                            }

                        }
                    }

                    "Transport" -> {
                        when (view) {
                            is ImageView -> {
                                view.setImageResource(R.drawable.ic_transport)
                                view.setColorFilter(
                                    ContextCompat.getColor(
                                        view.context, R.color.textColor
                                    )
                                )
                            }

                            is TextView -> {
                                view.setTextColor(
                                    ContextCompat.getColor(view.context, R.color.textColor)
                                )
                            }

                        }
                    }

                    "Health" -> {
                        when (view) {
                            is ImageView -> {
                                view.setImageResource(R.drawable.baseline_health_and_safety_24)
                                view.setColorFilter(
                                    ContextCompat.getColor(
                                        view.context, R.color.red
                                    )
                                )
                            }

                            is TextView -> {
                                view.setTextColor(
                                    ContextCompat.getColor(view.context, R.color.red)
                                )
                            }

                        }
                    }

                    "Education" -> {
                        when (view) {
                            is ImageView -> {
                                view.setImageResource(R.drawable.baseline_cast_for_education_24)
                                view.setColorFilter(
                                    ContextCompat.getColor(
                                        view.context, R.color.green
                                    )
                                )
                            }

                            is TextView -> {
                                view.setTextColor(
                                    ContextCompat.getColor(view.context, R.color.green)
                                )
                            }

                        }
                    }

                    "Other" -> {
                        when (view) {
                            is ImageView -> {
                                view.setImageResource(R.drawable.baseline_question_mark_24)
                                view.setColorFilter(
                                    ContextCompat.getColor(
                                        view.context, R.color.lightBrown
                                    )
                                )
                            }

                            is TextView -> {
                                view.setTextColor(
                                    ContextCompat.getColor(view.context, R.color.lightBrown)
                                )
                            }

                        }
                    }

                }
            }

        }
    }
}