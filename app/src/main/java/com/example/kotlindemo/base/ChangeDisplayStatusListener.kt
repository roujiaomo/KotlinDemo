package com.uniqlo.circle.ui.base

import android.content.Intent
import androidx.fragment.app.Fragment

interface ChangeDisplayStatusListener {

    /**
     * Call when move to next screen.
     * Define to unSubscribe events for FA.
     */
    fun onMoveToNextScreen()

    /**
     * Call on come back.
     * Define to reSubscribe events for FA.
     */
    fun onComeBack(fragment: Fragment? = null)

    /**
     * Call on come back.
     * Define to reSubscribe events for FA and other.
     */
    fun onComeBackForResult(
        fragment: Fragment? = null,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    )

    /**
     * On back press fragment
     */
    fun onBackPressFragment()
}
