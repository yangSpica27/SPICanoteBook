package me.spica.note.ui

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import me.spica.note.R
import me.spica.note.ui.editor.EditActivity
import me.spica.note.ui.main.MainActivity
import java.util.*
import kotlin.reflect.KClass


val ACTIVITIES = arrayListOf(
    Page(R.string.home,MainActivity::class),
    Page(R.string.editor,EditActivity::class)
)


data class Page(@StringRes val title: Int, val demoClass: KClass<out AppCompatActivity>) {
    val id: UUID = UUID.randomUUID()
}