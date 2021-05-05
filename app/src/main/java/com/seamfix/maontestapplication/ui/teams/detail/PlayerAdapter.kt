package com.seamfix.maontestapplication.ui.teams.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seamfix.maontestapplication.R
import com.seamfix.maontestapplication.data.model.entity.TeamDetail

class PlayerAdapter(var context: Context, private val teamDetail: TeamDetail?) : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder{
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_item_players, parent, false)
        return PlayerViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        var lisOfPlayers = teamDetail?.squad
        if(lisOfPlayers != null){
            val player = lisOfPlayers[position]
            holder.tvPlayerName.text = player.name
            holder.tvPosition.text = player.position
            holder.tvCountry.text = player.nationality
            holder.tvDateOfBirth.text = player.dateOfBirth
        }
    }

    override fun getItemCount(): Int {
        return teamDetail?.squad?.size ?: 0
    }

}
