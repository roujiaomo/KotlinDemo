package com.uniqlo.circle.ui.base

import android.app.Activity.RESULT_CANCELED
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.uniqlo.circle.extension.getCurrentFragment
import com.uniqlo.circle.extension.hideKeyboard
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Use this class to create base function for all fragments in this app
 */
abstract class BaseFragment<DB: ViewDataBinding> : Fragment(), ChangeDisplayStatusListener {

    companion object {

        private const val WAITING_TIME_FOR_HIDE_KEYBOARD = 300L
        internal const val SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192
    }

    internal var moveToNextScreen = false
    internal var currentChildFragment: BaseFragment<*>? = null
    private val subscription: CompositeDisposable = CompositeDisposable()
    private val subscriptionSystemEvent: CompositeDisposable = CompositeDisposable()
    private var requestCode: Int? = null
    private var resultCode: Int = RESULT_CANCELED
    private var result: Intent? = null
     lateinit  var binding: DB
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.addOnBackStackChangedListener {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        view.isClickable = true
        if (!shouldHookBaseHideKeyboard()) {
            hideKeyBoard()
        }
        addBackStackChangeListener()
    }

    override fun onPause() {
        super.onPause()
        subscription.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearSubscriptionSystemEvent()
    }

    override fun onComeBack(fragment: Fragment?) {
        // Call when come back to this screen. This screen going to subscribe event for FA.
    }

    override fun onComeBackForResult(
        fragment: Fragment?,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
    }

    override fun onMoveToNextScreen() {
        // Call when move to other screen. This screen going to unSubscribe events for FA.
        moveToNextScreen = true
        clearSubscriptionSystemEvent()
    }

    override fun onBackPressFragment() = Unit

    /**
     * Unsubscribe events for FA.
     */
    internal fun clearSubscriptionSystemEvent() {
        subscriptionSystemEvent.clear()
    }

    /**
     * Get container id.
     */
    @IdRes
    internal open fun getContainerId(): Int = -1

    internal fun setRequestCode(requestCode: Int) {
        this.requestCode = requestCode
    }

    internal fun setResult(resultCode: Int, data: Intent?) {
        this.resultCode = resultCode
        this.result = data
    }


    internal open fun isShowFooterBar() = false

    protected fun addDisposables(vararg ds: Disposable) {
        ds.forEach { subscription.add(it) }
    }

    protected open fun hideKeyBoard() {
        Handler().postDelayed({

            if (android.os.Build.VERSION.SDK_INT > 26) {
                context?.hideKeyboard(view?.rootView)
            } else {
                context?.hideKeyboard(requireActivity())
            }
        }, WAITING_TIME_FOR_HIDE_KEYBOARD)
    }

    private fun addBackStackChangeListener() {
        childFragmentManager.addOnBackStackChangedListener {
                val current = getCurrentFragment(getContainerId()) as? BaseFragment<*>
                current?.let {
                    if (!it.moveToNextScreen) {
                        return@let
                    }
                    if (currentChildFragment?.requestCode == null) {
                        current.onComeBack(currentChildFragment)
                    } else {
                        current.onComeBackForResult(
                            currentChildFragment,
                            currentChildFragment!!.requestCode!!,
                            currentChildFragment?.resultCode
                                ?: RESULT_CANCELED,
                            currentChildFragment?.result
                        )
                    }
                }
                currentChildFragment?.onMoveToNextScreen()
                currentChildFragment = current
        }
    }

    private fun addDisposablesSystemEvent(vararg ds: Disposable) {
        ds.forEach { subscriptionSystemEvent.add(it) }
    }

    abstract fun getLayoutId(): Int


    open fun shouldHookBaseHideKeyboard(): Boolean = false


}
