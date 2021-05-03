package com.wuujcik.spacex.ui.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wuujcik.spacex.R
import com.wuujcik.spacex.databinding.ItemLaunchElementBinding
import com.wuujcik.spacex.persistence.launch.Launch
import com.wuujcik.spacex.utils.formatDateTime
import java.util.*


class LaunchesAdapter : PagedListAdapter<Launch, LaunchesAdapter.CharacterViewHolder>(CharactersDiff()) {

    /** Callback when user click on holder */
    var onItemClicked: (item: Launch) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemLaunchElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }


    inner class CharacterViewHolder(
        private val binding: ItemLaunchElementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Launch) = with(itemView) {
            with(binding) {
                flightNumber.text =
                    context.getString(com.wuujcik.spacex.R.string.flight_no, item.flight_number)
                name.text = item.name
                item.date_unix?.let { dateTime ->
                    date.text = formatDateTime(context, Date(dateTime * 1000))
                }
                val img = item.links?.patch?.small
                if (img != null) {
                    image.load(img) {
                        placeholder(R.drawable.ic_rocket)
                    }
                } else {
                    image.load(R.drawable.ic_rocket)
                }
                root.setOnClickListener { onItemClicked(item) }
            }
        }
    }
}

class CharactersDiff : DiffUtil.ItemCallback<Launch>() {
    override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Launch, newItem: Launch): Boolean {
        return oldItem == newItem
    }
}
