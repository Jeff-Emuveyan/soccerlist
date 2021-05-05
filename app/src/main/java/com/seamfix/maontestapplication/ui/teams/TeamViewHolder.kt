package com.seamfix.maontestapplication.ui.teams

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.seamfix.maontestapplication.R

class TeamViewHolder(var context: Context, var view: View): RecyclerView.ViewHolder(view) {

    var imageView: ImageView = view.findViewById(R.id.imageView)
    var parentLayout: CardView = view.findViewById(R.id.parentLayout)
    var shimmerView: ShimmerFrameLayout =  view.findViewById(R.id.shimmer)

}
