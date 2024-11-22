package kodeflap.com.scroll.data.repositories

import User
import kodeflap.com.scroll.data.network.ApiInterface
import kodeflap.com.scroll.data.network.SafeApiRequest

class HomeRepository(
    private val api: ApiInterface
) : SafeApiRequest() {

    suspend fun getUsers(limit: Int, skip: Int): List<User> {
        // Passing the limit and skip to the API call
        return api.getUsers(limit, skip).users
    }
}