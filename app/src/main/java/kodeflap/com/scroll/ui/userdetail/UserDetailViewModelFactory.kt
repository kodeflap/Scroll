package kodeflap.com.scroll.ui.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kodeflap.com.scroll.data.repositories.UserDetailRepository

@Suppress("UNCHECKED_CAST")
class UserDetailViewModelFactory(
    private val repository: UserDetailRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserDetailViewModel(repository) as T
    }
}