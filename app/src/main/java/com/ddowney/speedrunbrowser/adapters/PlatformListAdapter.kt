package com.ddowney.speedrunbrowser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddowney.speedrunbrowser.R
import com.ddowney.speedrunbrowser.databinding.GameTextViewBinding
import com.ddowney.speedrunbrowser.models.Platform

/**
 * Created by Dan on 31/10/2017.
 */
class PlatformListAdapter(
  private val data: List<Platform>,
  private val itemClick: (Platform) -> Unit,
) : RecyclerView.Adapter<PlatformListAdapter.ViewHolder>() {

  class ViewHolder(
    private val itemClick: (Platform) -> Unit,
    private val binding: GameTextViewBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bindPlatformModel(platform: Platform) {
      with(platform) {
        binding.itemText.text = this.name
        binding.itemSubtext.text = "platform subtext"
        itemView.setOnClickListener { itemClick(this) }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = GameTextViewBinding.inflate(LayoutInflater.from(parent.context))
    return ViewHolder(itemClick, binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindPlatformModel(data[position])
  }

  override fun getItemCount() = data.size

}
