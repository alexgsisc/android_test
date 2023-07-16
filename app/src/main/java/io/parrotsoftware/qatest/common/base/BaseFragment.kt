package io.parrotsoftware.qatest.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * BaseFragment
 * @author (c) 2023, QA Test
 */
abstract class BaseFragment<VB: ViewBinding,VM : BaseViewModel> : Fragment(){
    abstract val binding: VB

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelsObserve()
    }

    protected open fun initView() = Unit

    protected open fun viewModelsObserve() = Unit
}