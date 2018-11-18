package com.ddowney.speedrunbrowser.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddowney.speedrunbrowser.R
import com.ddowney.speedrunbrowser.models.Game
import kotlinx.android.synthetic.main.game_text_view.view.*


/**
 * Created by Dan on 31/10/2017.
 */
class GameListAdapter(
        private val data: List<Game>,
        private val itemClick: (Game) -> Unit
) : RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    class ViewHolder(
            itemView: View,
            private val itemClick: (Game) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bindGameModel(game : Game) {
            with(game) {
                itemView.item_text.text = this.names.international
                val platforms = game.platforms?.toString()
                itemView.item_subtext.text = platforms?.substring(1, platforms.length-1)
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.game_text_view, parent, false)
        return ViewHolder(v, itemClick)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.bindGameModel(data[position])
    }

    override fun getItemCount() = data.size

}
