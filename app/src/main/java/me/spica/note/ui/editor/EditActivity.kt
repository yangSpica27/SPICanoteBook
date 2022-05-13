package me.spica.note.ui.editor

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.MarkwonEditorTextWatcher
import me.spica.note.R
import me.spica.note.base.BindingActivity
import me.spica.note.databinding.ActivityEditBinding
import me.spica.note.markwon.MarkdownSpan
import me.spica.note.markwon.addListItemListener
import me.spica.note.markwon.insertMarkdown
import me.spica.note.tools.hideKeyboard
import me.spica.note.tools.show
import java.util.concurrent.Executors
import javax.inject.Inject


/**
 * 编辑页
 */
@AndroidEntryPoint
class EditActivity : BindingActivity<ActivityEditBinding>() {


    //-------------------解析器------------------
    @Inject
    lateinit var markwon: Markwon

    @Inject
    lateinit var markwonEditor: MarkwonEditor
    //------------------------------------------

    private lateinit var markwonTextWatcher: TextWatcher


    // 使用拥有焦点
    private var contentHasFocus: Boolean = false

    private var optionsMenu: Menu? = null

    override fun initializer() {
        supportActionBar?.title = getString(R.string.editor)
        setupEditTexts()
        setupMarkdown()
        setupListeners()
        updateEditMode()
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ActivityEditBinding = ActivityEditBinding.inflate(inflater, parent, true)


    private fun setupMarkdown() {
        markwonTextWatcher = MarkwonEditorTextWatcher.withPreRender(
            markwonEditor, Executors.newCachedThreadPool(),
            viewBinding.editTextContent
        )
        enableMarkdownTextWatcher()
    }

    // 初始化编辑框
    private fun setupEditTexts() = with(viewBinding) {
        editTextTitle.apply {
            imeOptions = EditorInfo.IME_ACTION_NEXT
            setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)

            setOnEditorActionListener { v, actionId, event ->
                false
            }

            doOnTextChanged { text, start, before, count ->
                // Only listen for meaningful changes
                return@doOnTextChanged
            }
        }

        editTextContent.apply {
            enableUndoRedo(this@EditActivity)
            setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            doOnTextChanged { text, start, before, count ->
                // Only listen for meaningful changes, we do not care about empty text
                return@doOnTextChanged
            }
            setOnFocusChangeListener { v, hasFocus ->
                contentHasFocus = hasFocus
            }

            setOnEditorActionListener(addListItemListener)

            setOnCanUndoRedoListener { canUndo, canRedo ->
                viewBinding.bottomToolbar.menu?.run {
                    findItem(R.id.action_undo).isEnabled = canUndo
                    findItem(R.id.action_redo).isEnabled = canRedo
                }
            }
        }

        viewBinding.linearLayout.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) v.hideKeyboard()
        }

    }

    private fun enableMarkdownTextWatcher() = with(viewBinding) {
        if (!editTextContent.isMarkdownEnabled) {
            // TextWatcher is created and currently not attached to the EditText, we attach it
            editTextContent.addTextChangedListener(markwonTextWatcher)

            // Re-set text to notify the listener
            editTextContent.withOnlyTextWatcher<MarkwonEditorTextWatcher> {
                setText(text)
            }

            editTextContent.isMarkdownEnabled = true
        }
    }

    private fun disableMarkdownTextWatcher() = with(viewBinding) {
        if (editTextContent.isMarkdownEnabled) {
            // TextWatcher is created and currently attached to the EditText, we detach it
            editTextContent.removeTextChangedListener(markwonTextWatcher)
            val text = editTextContent.text.toString()

            editTextContent.text?.clearSpans()
            editTextContent.withoutTextWatchers {
                setText(text)
            }

            editTextContent.isMarkdownEnabled = false
        }
    }

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


    private fun setupListeners() = with(viewBinding) {
        bottomToolbar.setOnMenuItemClickListener {
            val span = when (it.itemId) {
                R.id.action_insert_bold -> MarkdownSpan.BOLD
                R.id.action_insert_italics -> MarkdownSpan.ITALICS
                R.id.action_insert_strikethrough -> MarkdownSpan.STRIKETHROUGH
                R.id.action_insert_code -> MarkdownSpan.CODE
                R.id.action_insert_quote -> MarkdownSpan.QUOTE
                R.id.action_insert_heading -> MarkdownSpan.HEADING
                R.id.action_insert_link -> {

                    null
                }
                R.id.action_insert_image -> {

                    null
                }
                R.id.action_insert_table -> {

                    null
                }
                R.id.action_toggle_check_line -> {

                    null
                }
                R.id.action_scroll_to_top -> {

                    null
                }
                R.id.action_scroll_to_bottom -> {

                    null
                }
                R.id.action_undo -> {
                    editTextContent.undo()
                    null
                }
                R.id.action_redo -> {
                    editTextContent.redo()
                    null
                }
                else -> return@setOnMenuItemClickListener false
            }
            editTextContent.insertMarkdown(span ?: return@setOnMenuItemClickListener false)
            true
        }
    }


    private fun updateEditMode(inEditMode: Boolean = true) = with(viewBinding) {

        editTextTitle.show()
        editTextContent.show()

    }


}