package com.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    private var _viewModel: VM? = null
    val mViewModel by lazy { checkNotNull(_viewModel) }

    private var _binding: VB? = null
    val mBinding by lazy { checkNotNull(_binding) }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = onCreateViewBinding(LayoutInflater.from(this))
        _viewModel = ViewModelProvider(this).get(onCreateViewModel())
        setContentView(mBinding.root)
        mBinding.bindView(mViewModel)
    }

    abstract fun onCreateViewBinding(layoutInflater: LayoutInflater): VB
    abstract fun onCreateViewModel(): Class<VM>
    abstract fun VB.bindView(viewModel: VM)
}