package com.ddowney.speedrunbrowser.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddowney.speedrunbrowser.R
import com.ddowney.speedrunbrowser.models.GameModel
import com.ddowney.speedrunbrowser.storage.TempDataStore
import kotlinx.android.synthetic.main.game_text_view.view.*


/**
 * Created by Dan on 31/10/2017.
 */
class GameListAdapter(private val data : List<GameModel>, private val itemClick : (GameModel) -> Unit)
    : RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    class ViewHolder(itemView : View, private val itemClick : (GameModel) -> Unit)
        : RecyclerView.ViewHolder(itemView) {

        fun bindGameModel(game : GameModel) {
            with(game) {
                itemView.item_text.text = this.names.international
                if (this.platforms != null && !this.platforms?.isEmpty()!!) {
                    val tmp = mutableListOf<String>()
                    this.platforms?.forEach {
                        tmp.add(TempDataStore.getPlatformById(it))
                    }
                    itemView.item_subtext.text = tmp.joinToString(", ")
                } else {
                    itemView.item_subtext.text = "Platform Unavailable"
                }
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
        holder.bindGameModel(data[position])
    }

    override fun getItemCount() = data.size

}
