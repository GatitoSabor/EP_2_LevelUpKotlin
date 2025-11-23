package com.example.lvlup.repository

import com.example.lvlup.data.AddressEntity
import com.example.lvlup.data.UserDao
import com.example.lvlup.data.UserEntity

class UserRepository(private val api: UserApiService) {
    suspend fun login(email: String, password: String): UserEntity? =
        api.login(email, password)

    suspend fun register(user: UserEntity) =
        api.register(user)

    suspend fun getUserById(userId: Int): UserEntity? =
        api.getUserById(userId)

    suspend fun updateUser(user: UserEntity) =
        api.updateUser(user.id, user)

    suspend fun getAddresses(userId: Int): List<AddressEntity> =
        api.getAddressesForUser(userId)

    suspend fun addAddress(userId: Int, value: String) =
        api.insertAddress(AddressEntity(userId = userId, value = value))

    suspend fun updateAddress(address: AddressEntity) =
        api.updateAddress(address.id, address)

    suspend fun removeAddress(address: AddressEntity) =
        api.deleteAddress(address.id)
}
