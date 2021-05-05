package com.seamfix.maontestapplication.ui.teams

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seamfix.maontestapplication.R
import com.seamfix.maontestapplication.data.model.entity.Team
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class TeamsAdapter(var context: Context,
                   var list: List<Team>,
                   var onItemClicked: (Team) -> Unit) : RecyclerView.Adapter<TeamViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_item_teams, parent, false)
        return TeamViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        if(list.isNotEmpty()){
            val team = list[position]

            Picasso.get().load(team.crestUrl).placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_broken_image).into(holder.imageView, object : Callback {
                    override fun onError(e: Exception?) {
                        holder.shimmerView.stopShimmer()
                        holder.shimmerView.hideShimmer()
                        holder.imageView.setImageResource(R.drawable.ic_broken_image)
                    }
                    override fun onSuccess() {
                        holder.shimmerView.stopShimmer()
                        holder.shimmerView.hideShimmer()
                    }
                })

            holder.parentLayout.setOnClickListener {
                onItemClicked.invoke(team)
            }
        }
    }

    override fun getItemCount(): Int {
      return list.size
    }

}
