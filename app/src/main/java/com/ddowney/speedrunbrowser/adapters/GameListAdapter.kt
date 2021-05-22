package com.ddowney.speedrunbrowser.adapters

import android.util.Log
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
  initialData: List<Game> = listOf(),
  private val itemClick: (Game) -> Unit,
) : RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

  private var data: List<Game> = initialData

  init {
    notifyDataSetChanged()
  }

  class ViewHolder(
    private val itemClick: (Game) -> Unit,
    private val binding: GameTextViewBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bindGameModel(game: Game) {
      Log.d("wubalub", "binding game -> $game")
      with(game) {
        binding.itemText.text = this.names.international
        val platforms = game.platforms?.toString()
        binding.itemSubtext.text = platforms?.substring(1, platforms.length - 1)
        itemView.setOnClickListener { itemClick(this) }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = GameTextViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(itemClick, binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindGameModel(data[position])
  }

  override fun getItemCount() = data.size

  fun updateData(data: List<Game>) {
    this.data = data
    notifyDataSetChanged()
  }

}
