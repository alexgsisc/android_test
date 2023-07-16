package io.parrotsoftware.qatest.common.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

/**
 * BaseViewModel
 * @author (c) 2023, QA Test
 */
abstract class BaseViewModel: ViewModel() {

    @CallSuper
    open fun onStart(bundle: Bundle?) {
    }

    @CallSuper
    open fun onStart() {
    }
}