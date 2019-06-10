package io.kamara.xkcd.daily

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.kamara.xkcd.daily.adapters.FavoritesAdapter
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.di.Injectable
import io.kamara.xkcd.daily.utils.toast
import io.kamara.xkcd.daily.viewmodels.ComicDetailViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject


class FavoritesFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var comicDetailViewModel: ComicDetailViewModel? = null

    private val adapter = FavoritesAdapter(::onItemClick)

    override fun toolbarTitle(): Int {
        return R.string.toolbar_title_favorites
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        comicDetailViewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(ComicDetailViewModel::class.java)

        comicDetailViewModel?.favoriteComics?.observe(viewLifecycleOwner, Observer { result ->
            updateViews(result) }
        )

        favoriteList.adapter = adapter
        favoriteList.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    private fun updateViews(result: List<Comic>) {
        adapter.submitList(result)
    }

    private fun onItemClick(comicId: String) {
        context.toast("Favorite item with nun $comicId clicked", Toast.LENGTH_SHORT)
        //TODO: Open detail page
    }
}
