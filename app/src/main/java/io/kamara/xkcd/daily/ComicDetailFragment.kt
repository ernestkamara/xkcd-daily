package io.kamara.xkcd.daily


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.repository.ComicRepository
import io.kamara.xkcd.daily.utils.Constants
import io.kamara.xkcd.daily.utils.InjectorUtils
import io.kamara.xkcd.daily.viewmodels.ComicDetailViewModel
import io.kamara.xkcd.daily.viewmodels.ComicDetailViewModelFactory
import kotlinx.android.synthetic.main.fragment_comic_detail.*


/**
 * A fragment representing a single [Comic] detail screen
 *
 */
//TODO: Make injectable
class ComicDetailFragment : Fragment() {
    private lateinit var comicDetailViewModelFactory: ComicDetailViewModelFactory
    private lateinit var comicRepository: ComicRepository
    private var comicDetailViewModel: ComicDetailViewModel? = null
    private var comicId: String? = "14" //TODO: Default to null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            comicId = it.getString(Constants.ARG_COMIC_ID)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        comicRepository = InjectorUtils.getComicRepository(requireContext())


        comicId?.let {comicId ->
            comicDetailViewModelFactory = ComicDetailViewModelFactory(comicId, comicRepository)
            comicDetailViewModel = ViewModelProvider(this, comicDetailViewModelFactory)
                .get(ComicDetailViewModel::class.java)

            comicDetailViewModel?.comic?.observe(viewLifecycleOwner, Observer { comic ->
                updateViews(comic) }
            )
        }
    }

    private fun updateViews(comic: Comic?) {
        comicText.text = comic?.toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_detail, container, false)
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
