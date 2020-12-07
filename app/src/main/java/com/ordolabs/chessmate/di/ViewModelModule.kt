package com.ordolabs.chessmate.di

import com.ordolabs.chessmate.viewmodel.base.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        HomeViewModel(
            // there will be usecases
        )
    }
}