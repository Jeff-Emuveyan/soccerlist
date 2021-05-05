package com.seamfix.maontestapplication.ui.main

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.seamfix.maontestapplication.R

class CompetitionViewHolder(var context: Context,
                            var view: View) : RecyclerView.ViewHolder(view) {

    var tvLeagueName: TextView = view.findViewById(R.id.tvLeagueName)
    var tvCountry: TextView = view.findViewById(R.id.tvCountry)
    var tvStartDate: TextView = view.findViewById(R.id.tvStartDate)
    var parentLayout: ConstraintLayout = view.findViewById(R.id.parentLayout)

}
