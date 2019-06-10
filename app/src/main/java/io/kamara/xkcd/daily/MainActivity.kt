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
import io.kamara.xkcd.daily.utils.InjectorUtils
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        // TODO: Should be removed when network fetching is implemented
        InjectorUtils.seedDatabase(context, comicDao)
        setupDrawerLayout()
    }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        val fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.nav_home -> {
                ComicDetailFragment.newInstance("100")
            }
            R.id.nav_favorites -> {
                FavoritesFragment.newInstance()
            }
            else -> {
                null
            }
        }

        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment as Fragment, tag)
                .commit()

            val toolbarTitleRes = (fragment as BaseFragment).toolbarTitle()
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
