@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

package com.uniqlo.circle.extension

import android.app.Activity
import android.app.ActivityManager
import android.app.KeyguardManager
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.PowerManager
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uniqlo.circle.ui.base.BaseFragment

/**
 * Toast message
 * @param message: CharSequence to show on toast
 * @param duration: Int, default is Toast.LENGTH_SHORT
 */
internal fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

/**
 * Extension method to inflate layout for ViewGroup.
 */
internal fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

internal fun FragmentActivity.replaceFragment(
    @IdRes containerId: Int, fragment: Fragment,
    t: (transaction: FragmentTransaction) -> Unit = {},
    isAddBackStack: Boolean = false
) {
    if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null) {
        val transaction = supportFragmentManager.beginTransaction()
        t.invoke(transaction)
        transaction.replace(containerId, fragment, fragment.javaClass.simpleName)
        if (isAddBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction.commitAllowingStateLoss()
    }
}

internal fun FragmentActivity.addFragment(
    @IdRes containerId: Int, fragment: BaseFragment<ViewDataBinding>,
    t: (transaction: FragmentTransaction) -> Unit = {}, backStackString: String? = null
) {
    if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null) {
        (getCurrentFragment(containerId) as? BaseFragment<*>)?.onMoveToNextScreen()
        val transaction = supportFragmentManager.beginTransaction()
        t.invoke(transaction)
        transaction.add(containerId, fragment, fragment.javaClass.simpleName)
        if (backStackString != null) {
            transaction.addToBackStack(backStackString)
        }
        transaction.commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }
}

internal fun FragmentActivity.addFragment(
    @IdRes containerId: Int, fragment: BaseFragment<ViewDataBinding>,
    t: (transaction: FragmentTransaction) -> Unit = {}, backStackString: String? = null,
    tag: String
) {
    if (supportFragmentManager.findFragmentByTag(tag) == null) {
        val transaction = supportFragmentManager.beginTransaction()
        t.invoke(transaction)
        transaction.add(containerId, fragment, tag)
        if (backStackString != null) {
            transaction.addToBackStack(backStackString)
        }
        transaction.commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }
}

internal fun FragmentActivity.getCurrentFragment(@IdRes containerId: Int) =
    supportFragmentManager.findFragmentById(containerId)

internal fun FragmentActivity.popFragment() {
    supportFragmentManager.popBackStackImmediate()
}

internal fun Context.getWidthScreen(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    val dimension = DisplayMetrics()
    wm?.defaultDisplay?.getMetrics(dimension)
    return dimension.widthPixels
}

internal fun Context.getHeightScreen(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    val dimension = DisplayMetrics()
    wm?.defaultDisplay?.getMetrics(dimension)
    return dimension.heightPixels
}

//internal fun Context.getStatusBarHeight(): Int {
//    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
//    return if (resourceId > 0)
//        dimen(resourceId)
//    else
//        Math.ceil(
//            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                resources.getInteger(R.integer.defaultStatusBarLowerAndroidM)
//            else R.integer.defaultStatusBarHigherAndroidM) * resources.displayMetrics.density.toDouble()
//        ).toInt()
//}

internal fun Context.getNavigationBarHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceId > 0) {
        return resources.getDimensionPixelSize(resourceId)
    }
    return 0
}

internal fun Context.hasNavBar(): Boolean {
    val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    return id > 0 && resources.getBoolean(id)
}

internal fun Context.hideKeyboard(view: View?) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
}

internal fun Context.hideKeyboard(activity: Activity) {
    val focusedView = activity.currentFocus ?: View(activity)
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(focusedView.windowToken, 0)
}

internal fun Context.showKeyboard(view: View) {
    if (view.requestFocus()) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        view.postDelayed({
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }, 100)
    }
}

internal fun Context.showKeyboardNotRequestFocus(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    view.postDelayed({
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }, 100)
}




internal fun Context.isScreenOff(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
        val dm = (getSystemService(Context.DISPLAY_SERVICE) as? DisplayManager)
        dm?.displays?.forEach {
            return it.state == Display.STATE_OFF
        }
    }
    val powerManager = getSystemService(Context.POWER_SERVICE) as? PowerManager
    powerManager?.let {
        return !it.isScreenOn
    }
    return false
}

internal fun Context.isApplicationBroughtToBackground(): Boolean {
    var isInBackground = true
    val am = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
        val runningProcesses = am?.runningAppProcesses ?: return false
        runningProcesses
            .filter { it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND }
            .forEach {
                it.pkgList
                    .filter { it == packageName }
                    .forEach { isInBackground = false }
            }
    } else {
        if (am == null) {
            return false
        }
        val taskInfo = am.getRunningTasks(1)
        val componentInfo = taskInfo[0].topActivity
        if (componentInfo?.packageName == packageName) {
            isInBackground = false
        }
    }
    return isInBackground
}

internal fun Context.screenWasLocked() =
    (getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager)?.inKeyguardRestrictedInputMode()
        ?: false

internal fun Context.px2dp(px: Float): Float {
    val resources = this.resources
    val metrics = resources.displayMetrics
    return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}


internal fun Context.getHeightScreenHasNav() =
    if (hasNavBar()) getHeightScreen() + getNavigationBarHeight() else getHeightScreen()

inline fun <reified VM : ViewModel> FragmentActivity.activityViewModelProvider(
    factory: ViewModelProvider.Factory
) = ViewModelProvider(this.viewModelStore,factory).get(VM::class.java)
/**
 * @param action action for positive button clicked.
 * @param cancelAction cancel action if we need.
 * */
//internal fun Context.showAlertWithAction(
//    title: String? = null,
//    message: String,
//    cancelable: Boolean = true,
//    @StringRes positiveButton: Int = R.string.shared_dialog_buttonYes,
//    @StringRes negativeButton: Int = R.string.shared_dialog_buttonCancel,
//    action: () -> Unit? = {},
//    cancelAction: () -> Unit? = {}
//) {
//    MaterialAlertDialogBuilder(this).apply {
//        title?.let {
//            setTitle(title)
//        }
//        setCancelable(cancelable)
//        setMessage(message)
//        setNegativeButton(negativeButton) { _, _ ->
//            cancelAction()
//        }
//        setPositiveButton(positiveButton) { _, _ ->
//            action()
//        }
//    }.show()
//}

