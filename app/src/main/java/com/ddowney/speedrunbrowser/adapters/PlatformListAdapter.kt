package com.ddowney.speedrunbrowser.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddowney.speedrunbrowser.R
import com.ddowney.speedrunbrowser.models.Platform
import kotlinx.android.synthetic.main.game_text_view.view.*


/**
 * Created by Dan on 31/10/2017.
 */
class PlatformListAdapter(private val data : List<Platform>, private val itemClick : (Platform) -> Unit)
    : RecyclerView.Adapter<PlatformListAdapter.ViewHolder>() {

    class ViewHolder(itemView : View, private val itemClick : (Platform) -> Unit)
        : RecyclerView.ViewHolder(itemView) {

        fun bindPlatformModel(platform : Platform) {
            with(platform) {
                itemView.item_text.text = this.name
                itemView.item_subtext.text = "platform subtext"
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int): ViewHolder {
        val v = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.game_text_view, parent, false)
        return ViewHolder(v, itemClick)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.bindPlatformModel(data[position])
    }

    override fun getItemCount() = data.size

}
