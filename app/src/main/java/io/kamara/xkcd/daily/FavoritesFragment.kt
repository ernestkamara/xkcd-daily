package io.kamara.xkcd.daily

import android.os.Bundle
import android.view.*


class FavoritesFragment : BaseFragment() {
    override fun toolbarTitle(): Int {
        return R.string.toolbar_title_favorites
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorites, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_explanation -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
