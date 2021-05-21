package com.ddowney.speedrunbrowser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddowney.speedrunbrowser.R
import com.ddowney.speedrunbrowser.databinding.GameTextViewBinding
import com.ddowney.speedrunbrowser.models.Game


/**
 * Created by Dan on 31/10/2017.
 */
class GameListAdapter(
  private val data: List<Game>,
  private val binding: GameTextViewBinding,
  private val itemClick: (Game) -> Unit,
) : RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

  class ViewHolder(
    itemView: View,
    private val itemClick: (Game) -> Unit,
    private val binding: GameTextViewBinding,
  ) : RecyclerView.ViewHolder(itemView) {

    fun bindGameModel(game: Game) {
      with(game) {

        binding.itemText.text = this.names.international
        val platforms = game.platforms?.toString()
        binding.itemSubtext.text = platforms?.substring(1, platforms.length - 1)
        itemView.setOnClickListener { itemClick(this) }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val v = LayoutInflater
      .from(parent.context)
      .inflate(R.layout.game_text_view, parent, false)
    return ViewHolder(v, itemClick, binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindGameModel(data[position])
  }

  override fun getItemCount() = data.size

}
