package com.common.viewmodel

import androidx.lifecycle.ViewModel

/**
 * 因为activity fragment dialog dialogFragment基类都需要一个泛型参数的ViewModel如果实在不需要用到ViewModel时则可以使用这个ViewModel
 * */
class InvalidViewModel : ViewModel()