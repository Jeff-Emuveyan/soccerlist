package com.seamfix.maontestapplication.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seamfix.maontestapplication.R
import com.seamfix.maontestapplication.data.model.entity.Competition

class CompetitionsAdapter(var context: Context,
                          var competitions: List<Competition>,
                          var onItemClicked: (Competition) -> Unit) : RecyclerView.Adapter<CompetitionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_item_competition, parent, false)
        return CompetitionViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        if(competitions.isNotEmpty()){
            val competition = competitions[position]
            holder.tvLeagueName.text = competition.name
            holder.tvCountry.text = competition.area?.name ?: "Country"
            holder.tvStartDate.text = competition.currentSeason?.startDate ?: "N/A"
            holder.parentLayout.setOnClickListener {
                onItemClicked.invoke(competition)
            }
        }
    }

    override fun getItemCount(): Int {
       return competitions.size
    }

}
