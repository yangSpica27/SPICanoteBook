package me.spica.note.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import me.spica.note.R
import me.spica.note.base.BindingActivity
import me.spica.note.databinding.ActivityMainBinding
import me.spica.note.tools.startActivityWithAnimation
import me.spica.note.ui.editor.EditActivity


/**
 * 主页
 */
class MainActivity : BindingActivity<ActivityMainBinding>() {


    override fun initializer() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = getString(R.string.home)
        initClick()
    }


    private fun initClick() {
        viewBinding.btnCreate.setOnClickListener {
            // 点击进入编辑页
            startActivityWithAnimation<EditActivity>(
                enterResId = android.R.anim.slide_in_left,
                exitResId = android.R.anim.slide_out_right
            )
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater, parent: ViewGroup):
            ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater, parent, true)


    // 该页面不需要自适应滑动
    override val contentNeedsScrollableContainer: Boolean
        get() = false
}