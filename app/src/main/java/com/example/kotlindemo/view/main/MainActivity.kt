package com.example.kotlindemo.view.main

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.base.BaseFragmentPagerAdapter
import com.example.kotlindemo.databinding.ActivityMainBinding
import com.example.kotlindemo.view.component.ComponentContainerFragment
import com.example.kotlindemo.view.jetpack.JetpackContainerFragment
import com.example.kotlindemo.view.other.OtherContainerFragment
import com.example.kotlindemo.view.source_code.SourceCodeContainerFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.uniqlo.circle.extension.activityViewModelProvider


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var tabTitleList: List<String>
    private lateinit var tabIconList: List<Drawable?>
    private var mFragments = mutableListOf<Fragment>()
    private var mAdapter: BaseFragmentPagerAdapter? = null
    private lateinit var tvMainTab: TextView
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun getLayoutId(): Int {
        return R.layout.activity_main;
    }

    override fun initView() {
        initTabData()
        initViewPager()
        mBinding.toolbar.setOnClickListener {
            viewModelFactory = defaultViewModelProviderFactory
            viewModel = activityViewModelProvider(viewModelFactory)
        }

    }

    private fun initViewPager() {
        mAdapter = BaseFragmentPagerAdapter(mFragments, supportFragmentManager)
        mBinding.vpMain.adapter = mAdapter

        for (index in tabIconList.indices) {
            val view: View = LayoutInflater.from(this).inflate(R.layout.item_main_tab, null)
            tvMainTab = view.findViewById(R.id.tv_main_tab)
            val tab = mBinding.tabMain.newTab()
            tvMainTab.text = tabTitleList[index]

            tabIconList[index]?.setBounds(
                0,
                0,
                tabIconList[index]?.minimumWidth!!,
                tabIconList[index]?.minimumHeight!!
            )
            tvMainTab.setCompoundDrawables(null, tabIconList[index], null, null)
            tab.customView = view
            mBinding.tabMain.addTab(tab)
        }
        mBinding.vpMain.offscreenPageLimit = mFragments.size - 1
        mBinding.tabMain.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mBinding.vpMain.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        mBinding.vpMain.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mBinding.tabMain.setScrollPosition(position, 0f, true)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun initTabData() {
        tabTitleList = mutableListOf(
            resources.getString(R.string.main_tab_component_title),
            resources.getString(R.string.main_tab_jetpack_title),
            resources.getString(R.string.main_tab_source_code_title),
            resources.getString(R.string.main_tab_other_title)
        )
        tabIconList = mutableListOf(
            ContextCompat.getDrawable(this, R.drawable.selector_main_tab_item_component),
            ContextCompat.getDrawable(this, R.drawable.selector_main_tab_item_jetpack),
            ContextCompat.getDrawable(this, R.drawable.selector_main_tab_item_source_code),
            ContextCompat.getDrawable(this, R.drawable.selector_main_tab_item_other)
        )
        mFragments.add(ComponentContainerFragment.newInstance())
        mFragments.add(JetpackContainerFragment.newInstance())
        mFragments.add(SourceCodeContainerFragment.newInstance())
        mFragments.add(OtherContainerFragment.newInstance())
    }

    override fun initData() {

    }
}
