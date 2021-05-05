package com.seamfix.maontestapplication.ui.teams

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener
import com.seamfix.maontestapplication.R
import com.seamfix.maontestapplication.data.model.entity.Team


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

            try {
                GlideToVectorYou.init()
                        .with(context)
                        .setPlaceHolder(R.drawable.ic_baseline_image_24, R.drawable.ic_broken_image)
                        .withListener(object: GlideToVectorYouListener {
                            override fun onLoadFailed() {
                                holder.shimmerView.stopShimmer()
                                holder.shimmerView.hideShimmer()
                                holder.imageView.setImageResource(R.drawable.ic_broken_image)
                            }

                            override fun onResourceReady() {
                                holder.shimmerView.stopShimmer()
                                holder.shimmerView.hideShimmer()
                            }
                        })
                        .load(Uri.parse(team.crestUrl), holder.imageView)
            } catch (e: Exception) {
                holder.shimmerView.stopShimmer()
                holder.shimmerView.hideShimmer()
                holder.imageView.setImageResource(R.drawable.ic_broken_image)
                Log.e(TeamsAdapter::class.simpleName, "Error from Glide")
            }

            holder.parentLayout.setOnClickListener {
                onItemClicked.invoke(team)
            }
        }
    }

    override fun getItemCount(): Int {
      return list.size
    }

}
