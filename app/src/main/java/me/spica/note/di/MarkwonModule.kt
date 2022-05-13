package me.spica.note.di

import android.content.Context
import android.text.util.Linkify
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import io.noties.markwon.*
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.handler.EmphasisEditHandler
import io.noties.markwon.editor.handler.StrongEmphasisEditHandler
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import io.noties.markwon.movement.MovementMethodPlugin
import me.saket.bettermovementmethod.BetterLinkMovementMethod
import me.spica.note.R
import me.spica.note.markwon.*


@Module
@InstallIn(ActivityComponent::class)
object MarkwonModule {
    @Provides
    @ActivityScoped
    fun provideMarkwon(@ActivityContext context: Context): Markwon {
        return Markwon.builder(context)
            .usePlugin(LinkifyPlugin.create(Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS))
            .usePlugin(SoftBreakAddsNewLinePlugin.create())
            .usePlugin(MovementMethodPlugin.create(BetterLinkMovementMethod.getInstance()))
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(CoilImagesPlugin.create(context))
            .usePlugin(TablePlugin.create(context))
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
                    builder.linkResolver(LinkResolverDef())
                }
            })
            .apply {
                val mainColor = ContextCompat.getColor(context, R.color.colorMarkdownTask)
                usePlugin(
                    TaskListPlugin.create(
                        mainColor,
                        mainColor,
                        ContextCompat.getColor(context, R.color.window_background)
                    )
                )
            }
            .build()
    }

    @Provides
    @ActivityScoped
    fun provideMarkwonEditor(markwon: Markwon): MarkwonEditor {
        return MarkwonEditor.builder(markwon)
            .useEditHandler(EmphasisEditHandler())
            .useEditHandler(StrongEmphasisEditHandler())
            .useEditHandler(CodeHandler())
            .useEditHandler(CodeBlockHandler())
            .useEditHandler(BlockQuoteHandler())
            .useEditHandler(StrikethroughHandler())
            .useEditHandler(HeadingHandler())
            .build()
    }
}