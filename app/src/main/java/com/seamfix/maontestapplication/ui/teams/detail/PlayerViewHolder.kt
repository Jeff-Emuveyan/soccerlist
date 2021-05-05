package com.seamfix.maontestapplication.ui.teams.detail

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seamfix.maontestapplication.R

class PlayerViewHolder(var context: Context, var view: View) : RecyclerView.ViewHolder(view) {

    var tvPlayerName: TextView = view.findViewById(R.id.tvTitle)
    var tvPosition: TextView = view.findViewById(R.id.tvMessage)
    var tvCountry: TextView = view.findViewById(R.id.tvCountry)
    var tvDateOfBirth: TextView = view.findViewById(R.id.tvCountryOfBirth)
}
