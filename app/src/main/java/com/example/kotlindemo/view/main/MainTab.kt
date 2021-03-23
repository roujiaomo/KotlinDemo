package com.uniqlo.circle.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.kotlindemo.R
import com.example.kotlindemo.view.component.ComponentContainerFragment
import com.example.kotlindemo.view.jetpack.JetpackContainerFragment
import com.example.kotlindemo.view.other.OtherContainerFragment
import com.example.kotlindemo.view.source_code.SourceCodeContainerFragment

class MainTab(val itemType: TabItemType) {

    companion object {
        internal const val COMPONENT_TAB_POSITION = 0
        internal const val JETPACK_TAB_POSITION = 1
        internal const val SOURCE_CODE_TAB_POSITION = 2
        internal const val OTHER_TAB_POSITION = 3
    }


    /**
     * TabItemType
     */
    enum class TabItemType(@DrawableRes val icon: Int,@StringRes val title:Int) {
        COMPONENT(R.drawable.selector_main_tab_item_component, R.string.main_tab_component_title),
        JETPACK(R.drawable.selector_main_tab_item_jetpack,R.string.main_tab_jetpack_title),
        SOURCE_CODE(R.drawable.selector_main_tab_item_source_code,R.string.main_tab_source_code_title),
        OTHER(R.drawable.selector_main_tab_item_other, R.string.main_tab_other_title),
    }

    /**
     * Method return item of tab each position
     */
    fun getItem(): androidx.fragment.app.Fragment? = when (itemType) {
        TabItemType.COMPONENT -> ComponentContainerFragment.newInstance()
       TabItemType.JETPACK -> JetpackContainerFragment.newInstance()
        TabItemType.SOURCE_CODE -> SourceCodeContainerFragment.newInstance()
        TabItemType.OTHER -> OtherContainerFragment.newInstance()
    }
}
