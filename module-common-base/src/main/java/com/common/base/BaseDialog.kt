package com.common.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.aiden.base.R

abstract class BaseDialog<VB : ViewBinding, VM : ViewModel>(
    context: Context?,
    private val viewModelStore: ViewModelStoreOwner? = null
) :
    AppCompatDialog(context, R.style.SampleTheme) {

    private var _viewModel: VM? = null
    val mViewModel by lazy { checkNotNull(_viewModel) }


    private var _viewBinging: VB? = null
    private val mViewBinging by lazy { checkNotNull(_viewBinging) }

    open fun onCreateViewModel(): Class<VM>? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parentView = window?.decorView
        _viewBinging = onCreateViewBinding(layoutInflater, parentView)
        createViewModel()
        setContentView(mViewBinging.root)
        val attributes = window?.attributes
        attributes?.gravity = getGravity()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            attributes?.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window?.attributes = attributes
        window?.setDimAmount(getDimAmount())
        if (getAnimation() != 0) {
            window?.setWindowAnimations(getAnimation())
        }
    }

    private fun createViewModel() {
        _viewModel = viewModelStore?.let {
            val cls = onCreateViewModel()
            if (cls != null) {
                ViewModelProvider(it).get(cls)
            } else {
                null
            }
        }
    }

    protected open fun getAnimation() = if (getGravity() == Gravity.BOTTOM) {
        R.style.full_bottom_dialog_animation
    } else if (getGravity() == Gravity.TOP) {
        R.style.full_top_dialog_animation
    } else {
        0
    }


    private fun getDimAmount(): Float {
        return 0.5F
    }

    abstract fun onCreateViewBinding(layoutInflater: LayoutInflater, parentView: View?): VB

    open fun getGravity() = Gravity.CENTER

    open fun getDialogStyle(): Int {
        return R.style.SampleTheme
    }
}