package kodeflap.com.scroll.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kodeflap.com.scroll.data.repositories.HomeRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: HomeRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}