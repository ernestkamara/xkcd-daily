package io.kamara.xkcd.daily.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.kamara.xkcd.daily.R
import io.kamara.xkcd.daily.data.Comic
import kotlinx.android.synthetic.main.favorite_list_item.view.*

class FavoritesAdapter : ListAdapter<Comic, FavoritesAdapter.ViewHolder>(ItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favorite_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        //holder.container.setOnClickListener { item.listener(item.id) }
        holder.titleTextView.text = item.title
        holder.subtitleTextView.text = item.alt
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: RelativeLayout = view.itemContainer
        val titleTextView: TextView = view.title
        val subtitleTextView: TextView = view.title
    }

    object ItemDiff : DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.num == newItem.num
        }

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem == newItem
        }
    }
}