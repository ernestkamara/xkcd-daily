package io.kamara.xkcd.daily


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.di.Injectable
import io.kamara.xkcd.daily.repository.api.Resource
import io.kamara.xkcd.daily.utils.*
import io.kamara.xkcd.daily.utils.Constants.Companion.ARG_COMIC_IMAGE_URL
import io.kamara.xkcd.daily.viewmodels.ComicDetailViewModel
import kotlinx.android.synthetic.main.actions_browse_comic.*
import kotlinx.android.synthetic.main.fragment_comic_detail.*
import javax.inject.Inject
import kotlin.random.Random


/**
 * A fragment representing a single [Comic] detail screen
 *
 */
class ComicDetailFragment : BaseFragment(), Injectable, SearchView.OnQueryTextListener {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private var comicDetailViewModel: ComicDetailViewModel? = null
    private var comicId: String? = null
    private var _comic: Comic? = null //TODO: Update usage


    override fun toolbarTitle(): Int {
        return R.string.toolbar_title_browse_comics
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            comicId = it.getString(Constants.ARG_COMIC_ID)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        comicDetailViewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(ComicDetailViewModel::class.java)

        comicId?.let { comicDetailViewModel?.setComicId(it) }
        comicDetailViewModel?.comic?.observe(viewLifecycleOwner, Observer { result -> updateViews(result) })
        setupActions()
    }

    private fun updateViews(result: Resource<Comic>) {
        //TODO: Handle error and loading state
        result.data?.let { comic ->
            val imageUrl = comic.img
            takeIf { _comic?.img != imageUrl }?.apply { imageUrl?.let { loadImage(it, comicImage) } }
            comicId = comic.num
            _comic = comic
            activity?.invalidateOptionsMenu()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_comic_detail, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_browse, menu)
        val searchItem = menu.findItem(R.id.action_search)
        setupSearch(searchItem)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val favoriteMenuItem = menu.findItem(R.id.action_favorites)
        comicId?.let {
            comicDetailViewModel?.isFavoriteComic(it).let { isFavorite ->
                when (isFavorite) {
                    true -> favoriteMenuItem.icon = resources.getDrawable(R.drawable.ic_favorite_black_24dp)
                    else -> favoriteMenuItem.icon = resources.getDrawable(R.drawable.ic_favorite_border_black_24dp)
                }
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorites -> {
                toggleFavorite()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
     private fun loadImage(imageUrl: String, view: ImageView) {
         view.setOnClickListener {
             activity?.let{
                 val intent = Intent (it, FullscreenPreviewActivity::class.java)
                 intent.putExtra(ARG_COMIC_IMAGE_URL, imageUrl)
                 it.startActivity(intent)
             }
         }
         view.loadFromUrl(imageUrl)
    }

    private fun toggleFavorite() {
        comicId?.let {
            comicDetailViewModel?.toggleFavorite(it)
            activity?.invalidateOptionsMenu()
        }
    }

    private fun setupActions() {
        nextComicFab.setOnClickListener {
            context.toast(it.contentDescription.toString(), Toast.LENGTH_SHORT)
            comicId?.let { currentNum -> updateComicId(currentNum.toInt().inc()) }
        }
        previousComicFab.setOnClickListener {
            context.toast(it.contentDescription.toString(), Toast.LENGTH_SHORT)
            comicId?.let { currentNum -> updateComicId(currentNum.toInt().dec()) }
        }
        randomComicFab.setOnClickListener {
            context.toast(it.contentDescription.toString(), Toast.LENGTH_SHORT)
            //TODO: Fix with actual offsets
            updateComicId((Random.nextInt(1, 2158)))
        }
    }

    private fun updateComicId(comicId: Int) {
        comicDetailViewModel?.setComicId(comicId.toString())
    }

    private fun setupSearch(searchItem: MenuItem) {
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = getString(R.string.search_view_hint)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        //TODO: Support title and field search
        if (query.isNumeric()) {
            comicDetailViewModel?.setComicId(query)
        } else{
            context.toast(R.string.invalid_search_query_warning, Toast.LENGTH_SHORT)
        }
        view?.hideKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    companion object {
        /**
         * @param comic_Id Id of the Comic to create detail page for.
         * @return A new instance of fragment ComicDetailFragment.
         */
        @JvmStatic
        fun newInstance(comic_Id: String) =
            ComicDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.ARG_COMIC_ID, comic_Id)
                }
            }
    }
}
