package io.kamara.xkcd.daily.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.kamara.xkcd.daily.R
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.utils.loadFromUrl
import kotlinx.android.synthetic.main.favorite_list_item.view.*

class FavoritesAdapter(val onClickListener: (String) -> Unit) : ListAdapter<Comic, FavoritesAdapter.ViewHolder>(ItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favorite_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comic = getItem(position)
        holder.container.setOnClickListener { onClickListener(comic.num) }
        holder.titleTextView.text = comic.title
        holder.subtitleTextView.text = comic.alt
        comic?.img?.let { holder.comicThumb.loadFromUrl(it) }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: RelativeLayout = view.itemContainer
        val titleTextView: TextView = view.title
        val subtitleTextView: TextView = view.title
        val comicThumb: ImageView = view.comicThumb
    }

    object ItemDiff : DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(oldComic: Comic, newComic: Comic): Boolean {
            return oldComic.num == newComic.num
        }

        override fun areContentsTheSame(oldComic: Comic, newComic: Comic): Boolean {
            return oldComic == newComic
        }
    }
}