package kodeflap.com.scroll.ui.home

import User
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kodeflap.com.scroll.data.repositories.HomeRepository
import kodeflap.com.scroll.utils.ApiException
import kodeflap.com.scroll.utils.NoInternetException
import kodeflap.com.scroll.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private var pageIndex = 0
    private var pageSize = 10
    private var userList = ArrayList<User>()
    private var userResponse: List<User> = listOf()

    private val _userLiveData = MutableLiveData<State<ArrayList<User>>>()
    val userLiveData: LiveData<State<ArrayList<User>>>
        get() = _userLiveData

    private val _userNameLiveData = MutableLiveData<String>()
    val userNameLiveData: LiveData<String>
        get() = _userNameLiveData

    private val _loadMoreListLiveData = MutableLiveData<Boolean>()
    val loadMoreListLiveData: LiveData<Boolean>
        get() = _loadMoreListLiveData

    init {
        _loadMoreListLiveData.value = false
        _userNameLiveData.value = ""
    }

    fun getUsers() {
        if (pageIndex == 1) {
            userList.clear()
            _userLiveData.postValue(State.loading())
        } else {
            if (userList.isNotEmpty() && userList.last() == null) {
                userList.removeAt(userList.size - 1)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Get all users
                userResponse = repository.getUsers(pageSize, pageIndex * pageSize)
                withContext(Dispatchers.Main) {
                    if (userResponse.isNotEmpty()) {
                        // Filter users based on search query
                        val filteredUsers = if (_userNameLiveData.value.isNullOrEmpty()) {
                            userResponse
                        } else {
                            userResponse.filter { it.firstName.contains(_userNameLiveData.value!!, ignoreCase = true) }
                        }

                        userList.addAll(filteredUsers)
                        pageIndex++  // Increment pageIndex to load next page on next request
                        _userLiveData.postValue(State.success(userList))
                    } else {
                        _loadMoreListLiveData.value = false // No more data to load
                    }
                }
            } catch (e: ApiException) {
                withContext(Dispatchers.Main) {
                    _userLiveData.postValue(State.error(e.message!!))
                    _loadMoreListLiveData.value = false
                }
            } catch (e: NoInternetException) {
                withContext(Dispatchers.Main) {
                    _userLiveData.postValue(State.error(e.message!!))
                    _loadMoreListLiveData.value = false
                }
            }
        }
    }

    fun searchMovie(query: String) {
        _userNameLiveData.value = query
        pageIndex = 1  // Reset page index to 1 for new search
        userList.clear() // Clear existing list to avoid mixing results
        getUsers() // Fetch filtered users based on the search query
    }

    fun loadMore() {
        pageIndex++
        getUsers()
    }

    fun checkForLoadMoreItems(
        visibleItemCount: Int,
        totalItemCount: Int,
        firstVisibleItemPosition: Int
    ) {
        if (!_loadMoreListLiveData.value!! && (totalItemCount < pageSize)) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                _loadMoreListLiveData.value = true
            }
        }
    }
}
