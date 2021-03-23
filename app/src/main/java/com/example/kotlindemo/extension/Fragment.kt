package com.uniqlo.circle.extension

import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kotlindemo.R
import com.uniqlo.circle.ui.base.BaseFragment

internal fun Fragment.addChildFragment(
    @IdRes containerId: Int, fragment: BaseFragment<ViewDataBinding>, backStack: String? = null,
    t: (transaction: FragmentTransaction) -> Unit = {}
) {
    if (childFragmentManager.findFragmentByTag(backStack) == null) {
        (getCurrentFragment(containerId) as? BaseFragment<*>)?.onMoveToNextScreen()
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
    @IdRes containerId: Int, fragment: BaseFragment<ViewDataBinding>, requestCode: Int, backStack: String? = null,
    t: (transaction: FragmentTransaction) -> Unit = {}
) {
    (getCurrentFragment(containerId) as? BaseFragment<*>)?.onMoveToNextScreen()
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
    @IdRes containerId: Int, fragment: BaseFragment<ViewDataBinding>, backStack: String? = null,
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

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    factory: ViewModelProvider.Factory
) = ViewModelProvider(this.viewModelStore, factory).get(VM::class.java)

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the Activity.
 */
inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(
    factory: ViewModelProvider.Factory
) = ViewModelProvider(requireActivity().viewModelStore, factory).get(VM::class.java)

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the parent
 * Fragment.
 */
inline fun <reified VM : ViewModel> Fragment.parentViewModelProvider(
    factory: ViewModelProvider.Factory
) = ViewModelProvider(requireParentFragment().viewModelStore, factory).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.getViewModelProviderByFragment(
    fragment: Fragment,
    factory: ViewModelProvider.Factory
) = ViewModelProvider(fragment.viewModelStore, factory).get(VM::class.java)

fun Fragment.lifecycleScopeOfView() = this.viewLifecycleOwner.lifecycleScope

fun Fragment.getTimestamp(): Long {
    return System.currentTimeMillis()
}
