package ru.tohaman.mytestcomposeapplication.android_auto.auto_screen

import androidx.car.app.Screen
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch

fun Screen.getViewModelStoreOwner(): ViewModelStoreOwner {
    val viewModelStoreOwner = object : ViewModelStoreOwner {
        override val viewModelStore = ViewModelStore()
    }
    lifecycleScope.launch {
        try {
            awaitCancellation()
        } finally {
            viewModelStoreOwner.viewModelStore.clear()
        }
    }
    return viewModelStoreOwner
}

