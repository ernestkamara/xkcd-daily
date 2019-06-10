package io.kamara.xkcd.daily


import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.di.Injectable
import io.kamara.xkcd.daily.repository.api.Resource
import io.kamara.xkcd.daily.utils.Constants
import io.kamara.xkcd.daily.viewmodels.ComicDetailViewModel
import kotlinx.android.synthetic.main.fragment_comic_detail.*
import javax.inject.Inject


/**
 * A fragment representing a single [Comic] detail screen
 *
 */
class ComicDetailFragment : BaseFragment(), Injectable {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private var comicDetailViewModel: ComicDetailViewModel? = null
    private var comicId: String? = null


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
    }

    private fun updateViews(result: Resource<Comic>) {
        val comic = result.data
        comic?.img?.let { loadImage(it, comicImage) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_comic_detail, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_browse, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorites -> {
                Toast.makeText(requireContext(), "TBI", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
     private fun loadImage(imageUrl: String, view: ImageView) {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.load_image_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade(Constants.IMAGE_LOADING_CROSS_FADE_DURATION))
            .into(view)
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
