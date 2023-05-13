package com.common.base;


import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.common.network.R
import kotlin.math.min

abstract class BaseDIalogFragment<VB : ViewBinding, VM : ViewModel> : DialogFragment() {

    private var _binding: VB? = null
    private var _viewModel: VM? = null
    val mViewModel by lazy { checkNotNull(_viewModel) }
    val mBinding: VB get() = checkNotNull(_binding) { "初始化binding失败" }
    protected abstract fun onCreateViewBinding(): VB
    protected abstract fun onCreateViewModel(): Class<VM>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = onCreateViewBinding()
        _viewModel = ViewModelProvider(this).get(onCreateViewModel())
        mBinding.init()
    }

    protected abstract fun VB.init()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AppCompatDialog(activity, getDialogStyle()).apply {
            setContentView(mBinding.root)
            window?.run {
                val lp = attributes
                lp.gravity = Gravity.BOTTOM
                lp.width = min(
                    requireContext().resources.displayMetrics.widthPixels,
                    requireContext().resources.displayMetrics.heightPixels
                )
                lp.height = getDialogHeight()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    lp.layoutInDisplayCutoutMode =
                        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                }
                attributes = lp
                setDimAmount(getDimAmount())
                setWindowAnimations(R.style.full_bottom_dialog_animation)
            }
        }
    }

    fun show(manager: FragmentManager) {
        if (isAdded) {
            dialog?.show()
            return
        }
        super.show(manager, this::javaClass.name);
    }

    fun getDimAmount(): Float {
        return 0.5f
    }

    open fun getDialogStyle() = R.style.SampleTheme

    open fun getDialogHeight() = WindowManager.LayoutParams.WRAP_CONTENT
}
