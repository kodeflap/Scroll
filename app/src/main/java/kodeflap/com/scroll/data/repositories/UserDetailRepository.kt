package kodeflap.com.scroll.data.repositories

import User
import kodeflap.com.scroll.data.network.ApiInterface
import kodeflap.com.scroll.data.network.SafeApiRequest

class UserDetailRepository(
    private val api: ApiInterface
) : SafeApiRequest() {

    suspend fun getUserDetails(id: Int): User {
        return api.getUserDetails(id)
    }
}
