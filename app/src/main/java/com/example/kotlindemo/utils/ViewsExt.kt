package com.example.kotlindemo.utils

import android.app.Activity
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


/**
 * Views
 *
 */


fun View.gone() {
    this.visibility = View.GONE
}

fun kotlin.Any.gones(vararg views: View) {
    views.forEach {
        it.visibility = View.GONE
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun kotlin.Any.visibles(vararg views: View) {
    views.forEach {
        it.visibility = View.VISIBLE
    }
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

fun kotlin.Any.isViewGone(vararg views: View): Boolean {
    views.forEach {
        if (it.visibility != View.GONE) return false
    }
    return true
}

fun kotlin.Any.inVisibles(vararg views: View) {
    views.forEach {
        it.visibility = View.INVISIBLE
    }
}






/**
 * 扩展点击事件，参数为listener
 **/
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/**
 * 扩展点击事件，参数为方法
 **/
fun <T : View> T.onClick(method: (T) -> Unit): View {
    setOnClickListener { method(this) }
    return this
}

fun Activity.bindClick(vararg views: View, action: (View) -> Unit) {
    views.forEach {
        it.setOnClickListener {
            action(it)
        }
    }
}

fun Activity.bindLongClick(vararg views: View, action: (View) -> Boolean) {
    views.forEach {
        it.setOnLongClickListener {
            action(it)
        }
    }
}

fun Fragment.bindClick(vararg views: View?, action: (View) -> Unit) {
    views.forEach {
        it?.setOnClickListener {
            action(it)
        }
    }
}


fun EditText.text2Str(): String = this.text.toString()

fun EditText.text2TrimStr(): String = this.text.toString().trim()

private val onBeforeChanged: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> }
private val onChanged: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> }
private val onAfterChanged: (s: Editable?) -> Unit = { _ -> }

/**
 *  EditText addTextChangedListener 扩展
 */
fun EditText.onTextChanged(beforeChanged: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = onBeforeChanged,
                           changed: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = onChanged,
                           afterChanged: (s: Editable?) -> Unit = onAfterChanged): TextWatcher {
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterChanged(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeChanged(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            changed(s, start, before, count)
        }

    }
    addTextChangedListener(watcher)
    return watcher
}


