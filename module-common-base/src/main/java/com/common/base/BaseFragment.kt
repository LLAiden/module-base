package com.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> @JvmOverloads constructor(
    @LayoutRes contentLayoutId: Int,
    private val viewModelBindingActivity: Boolean = false
) : Fragment(contentLayoutId) {

    private var _viewModel: VM? = null
    val mViewModel by lazy { checkNotNull(_viewModel) }

    private var _binding: VB? = null
    val mBinding by lazy { checkNotNull(_binding) }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val onCreateView = super.onCreateView(inflater, container, savedInstanceState)
        checkNotNull(onCreateView) {
            "没有找到fragment布局..."
        }
        _binding = onCreateViewBinding(onCreateView)
        val viewModelStore = if (viewModelBindingActivity) requireActivity() else this
        _viewModel = ViewModelProvider(viewModelStore).get(onCreateViewModel())
        return mBinding.root
    }

    abstract fun onCreateViewBinding(onCreateView: View): VB
    abstract fun onCreateViewModel(): Class<VM>

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.bindView(mViewModel)
    }

    abstract fun VB.bindView(viewModel: VM)

}