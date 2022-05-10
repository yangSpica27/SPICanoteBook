package me.spica.note.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.viewbinding.ViewBinding
import com.microsoft.fluentui.appbarlayout.AppBarLayout
import me.spica.note.R
import me.spica.note.databinding.ActivityBaseBinding
import me.spica.note.ui.ACTIVITIES
import me.spica.note.ui.Page
import java.util.*


abstract class BindingActivity<ViewBindingType : ViewBinding> : AppCompatActivity() {

    companion object {
        const val ACTIVITY_ID = "activity_id"
    }

    private var _binding: ViewBindingType? = null

    private val rootView: CoordinatorLayout by lazy { findViewById(R.id.root_view) }
    private val appBar: AppBarLayout by lazy { findViewById(R.id.app_bar) }
    private val baseDetailScrollableContainer: NestedScrollView by lazy { findViewById(R.id.base_detail_scrollable_container) }
    private val baseDetailContainer: LinearLayout by lazy { findViewById(R.id.base_detail_container) }


    protected val viewBinding
        get() = requireNotNull(_binding)


    protected open val contentNeedsScrollableContainer: Boolean
        get() = true

    private lateinit var containerBinding: ActivityBaseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        containerBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(containerBinding.rootView)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val container = if (contentNeedsScrollableContainer)
            containerBinding.baseDetailScrollableContainer
        else containerBinding.baseDetailContainer

        _binding = setupViewBinding(layoutInflater,container)

        container.removeAllViews()
        container.addView(_binding?.root)

        initializer()
    }


    abstract fun initializer()

    abstract fun setupViewBinding(inflater: LayoutInflater,parent: ViewGroup): ViewBindingType

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}
