package com.uniqlo.circle.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kotlindemo.R
import com.uniqlo.circle.ui.base.BaseFragment

internal fun Fragment.addChildFragment(
    @IdRes containerId: Int, fragment: BaseFragment, backStack: String? = null,
    t: (transaction: FragmentTransaction) -> Unit = {}
) {
    if (childFragmentManager.findFragmentByTag(backStack) == null) {
        (getCurrentFragment(containerId) as? BaseFragment)?.onMoveToNextScreen()
        val transaction = childFragmentManager.beginTransaction()
        t.invoke(transaction)
        transaction.add(containerId, fragment, fragment.javaClass.simpleName)
        if (backStack != null) {
            transaction.addToBackStack(backStack)
        }
        transaction.commitAllowingStateLoss()
    }
}

internal fun Fragment.addChildFragmentForResult(
    @IdRes containerId: Int, fragment: BaseFragment, requestCode: Int, backStack: String? = null,
    t: (transaction: FragmentTransaction) -> Unit = {}
) {
    (getCurrentFragment(containerId) as? BaseFragment)?.onMoveToNextScreen()
    val transaction = childFragmentManager.beginTransaction()
    t.invoke(transaction)
    transaction.add(containerId, fragment, fragment.javaClass.simpleName)
    fragment.setRequestCode(requestCode)
    if (backStack != null) {
        transaction.addToBackStack(backStack)
    }
    transaction.commitAllowingStateLoss()
}

internal var isMaintenanceShowing = false

internal fun Fragment.popChildFragment() {
    childFragmentManager.popBackStack()
}

internal fun Fragment.replaceChildFragment(
    @IdRes containerId: Int, fragment: BaseFragment, backStack: String? = null,
    t: (transaction: FragmentTransaction) -> Unit = {}
) {
    val transaction = childFragmentManager.beginTransaction()
    t.invoke(transaction)
    transaction.replace(containerId, fragment, backStack)
    if (backStack != null) {
        transaction.addToBackStack(backStack)
    }
    transaction.commitAllowingStateLoss()
}

internal fun Fragment.getCurrentFragment(@IdRes containerId: Int): Fragment? {
    if (isAdded) {
        return childFragmentManager.findFragmentById(containerId)
    }
    return null
}

internal fun FragmentTransaction.animFadeInFadeOut() {
    setCustomAnimations(R.anim.splash_fade_in, R.anim.splash_fade_out, 0, 0)
}

internal fun FragmentTransaction.animSlideInRightSlideOutRight() {
    setCustomAnimations(R.anim.slide_in_right, 0, 0, R.anim.slide_out_right)
}

internal fun FragmentTransaction.animSlideBottomSheet() {
    setCustomAnimations(R.anim.bottom_sheet_slide_up, 0, 0, R.anim.bottom_sheet_slide_down)
}

internal fun FragmentTransaction.animSlideInRight() {
    setCustomAnimations(R.anim.slide_in_right, 0, 0, R.anim.nothing)
}


