package io.kamara.xkcd.daily

import android.app.Application
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.kamara.xkcd.daily.data.ComicDao
import io.kamara.xkcd.daily.utils.addFragment
import io.kamara.xkcd.daily.utils.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(),
    HasSupportFragmentInjector,
    NavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject lateinit var context: Application
    @Inject lateinit var comicDao: ComicDao

    override fun supportFragmentInjector() = dispatchingAndroidInjector
    private var currentFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        setupDrawerLayout()
    }

    private fun loadFragment(itemId: Int) {
        when (itemId) {
            R.id.nav_home -> {
                val tag = ComicDetailFragment::class.java.simpleName
                var fragment = supportFragmentManager.findFragmentByTag(tag)
                if (fragment == null) {
                    fragment = ComicDetailFragment.newInstance("100")
                }
                replaceFragment(fragment as BaseFragment)
            }

            R.id.nav_favorites -> {
                val tag = FavoritesFragment::class.java.simpleName
                var fragment = supportFragmentManager.findFragmentByTag(tag)
                if (fragment == null) {
                    fragment =  FavoritesFragment.newInstance()
                }
                replaceFragment(fragment as BaseFragment)
            }
        }
    }

    private fun replaceFragment(fragment: BaseFragment) {
        if (fragment != currentFragment) {
            replaceFragment(fragment, R.id.fragmentContainer)
            currentFragment = fragment

            val toolbarTitleRes = fragment.toolbarTitle()
            title = (getString(toolbarTitleRes))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        loadFragment(item.itemId)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setupDrawerLayout() {
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val tag = ComicDetailFragment::class.java.simpleName
        currentFragment = supportFragmentManager.findFragmentByTag(tag) as BaseFragment?
        if (currentFragment == null) {
            currentFragment = ComicDetailFragment.newInstance("100") // TODO: fetch latest comic
        }
        addFragment(currentFragment!!, R.id.fragmentContainer)

        navView.setNavigationItemSelectedListener(this)

        onNavigationItemSelected(navView.menu.findItem(R.id.nav_home))
        navView.setCheckedItem(R.id.nav_home)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
