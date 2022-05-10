package me.spica.note.ui.editor

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import me.spica.note.R
import me.spica.note.base.BindingActivity
import me.spica.note.databinding.ActivityEditBinding


/**
 * 编辑页
 */
class EditActivity : BindingActivity<ActivityEditBinding>() {

    private var optionsMenu: Menu? = null

    override fun initializer() {
        supportActionBar?.title = getString(R.string.editor)
        viewBinding.etTitle.apply {
            enableUndoRedo(this@EditActivity)
            setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            doOnTextChanged { text, start, before, count ->  }
            setOnFocusChangeListener { view, b ->  }
            setOnCanUndoRedoListener { canUndo, canRedo ->
                viewBinding.bottomToolbar.menu?.run {
                    findItem(R.id.action_undo).isEnabled = canUndo
                    findItem(R.id.action_redo).isEnabled = canRedo
                }
            }
        }
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ActivityEditBinding = ActivityEditBinding.inflate(inflater, parent, true)


    @Suppress("DEPRECATION")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)

        optionsMenu = menu

        for (index in 0 until menu.size()) {
            val drawable = menu.getItem(index).icon
            drawable?.colorFilter = PorterDuffColorFilter(
                Color.WHITE,
                PorterDuff.Mode.SRC_IN
            )
        }
        return true
    }


    override val contentNeedsScrollableContainer: Boolean
        get() = false

}