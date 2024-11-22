package kodeflap.com.scroll.ui.userdetail

import User
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kodeflap.com.scroll.data.repositories.UserDetailRepository
import kodeflap.com.scroll.utils.ApiException
import kodeflap.com.scroll.utils.NoInternetException
import kodeflap.com.scroll.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailViewModel(
    private val repository: UserDetailRepository
) : ViewModel() {

    private val _userDetailLiveData = MutableLiveData<State<User>>()
    val userDetailLiveData: LiveData<State<User>>
        get() = _userDetailLiveData
    private lateinit var userDetailResponse: User

    fun getUserDetail(id: Int) {
        _userDetailLiveData.postValue(State.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userDetailResponse = repository.getUserDetails(id)
                withContext(Dispatchers.Main) {
                    _userDetailLiveData.postValue(State.success(userDetailResponse))
                }
            } catch (e: ApiException) {
                withContext(Dispatchers.Main) {
                    _userDetailLiveData.postValue(State.error(e.message!!))
                }
            } catch (e: NoInternetException) {
                withContext(Dispatchers.Main) {
                    _userDetailLiveData.postValue(State.error(e.message!!))
                }
            }
        }
    }

}