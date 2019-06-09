package io.kamara.xkcd.daily

import android.app.Application
import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.kamara.xkcd.daily.data.ComicDao
import io.kamara.xkcd.daily.di.Injectable
import io.kamara.xkcd.daily.utils.Constants.Companion.PARAM_NAVIGATION_ID
import io.kamara.xkcd.daily.utils.InjectorUtils
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject lateinit var context: Application
    @Inject lateinit var comicDao: ComicDao

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        loadFragment(item.itemId)
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.navView)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        val navigationId = intent.getIntExtra(PARAM_NAVIGATION_ID, R.id.navigation_home)
        navView.selectedItemId = navigationId

        // TODO: Should be removed when network fetching is implemented
        InjectorUtils.seedDatabase(context, comicDao)
    }


    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        val fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.navigation_home -> {
                ComicDetailFragment.newInstance("1")
            }
            R.id.navigation_search -> {
                ComicDetailFragment.newInstance("2")
            }
            R.id.navigation_favorites -> {
                ComicDetailFragment.newInstance("14")
            }
            else -> {
                null
            }
        }

        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit()
        }
    }
}
