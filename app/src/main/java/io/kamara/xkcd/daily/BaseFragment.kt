package io.kamara.xkcd.daily


import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun toolbarTitle(): Int
}