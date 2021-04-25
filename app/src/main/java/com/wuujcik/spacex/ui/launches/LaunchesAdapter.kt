package com.wuujcik.spacex.ui.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wuujcik.spacex.R
import com.wuujcik.spacex.databinding.ItemLaunchElementBinding
import com.wuujcik.spacex.persistence.Launch
import com.wuujcik.spacex.utils.formatDateTime
import java.util.*

class LaunchesAdapter :
    ListAdapter<Launch, LaunchesAdapter.ViewHolder>(diffCallback) {


    /** Callback when user click on holder */
    var onItemClicked: (item: Launch) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLaunchElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class ViewHolder(private val binding: ItemLaunchElementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Launch) = with(itemView) {
            binding.flightNumber.text = context.getString(R.string.flight_no, item.flight_number)
            binding.name.text = item.name
            item.date_unix?.let { dateTime ->
                binding.date.text = formatDateTime(context, Date(dateTime * 1000))
            }
            val img = item.links?.patch?.small
            if (img != null) {
                binding.image.load(img) {
                    placeholder(R.drawable.ic_rocket)
                }
            } else {
                binding.image.load(R.drawable.ic_rocket)
            }
            binding.root.setOnClickListener { onItemClicked(item) }
        }
    }


    companion object {
        const val TAG = "LaunchesAdapter"
        var diffCallback: DiffUtil.ItemCallback<Launch> =
            object : DiffUtil.ItemCallback<Launch>() {

                override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Launch, newItem: Launch): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }
}

