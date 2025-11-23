package com.example.lvlup.repository

import com.example.lvlup.data.UserEntity
import com.example.lvlup.data.AddressEntity
import retrofit2.http.*

interface UserApiService {
    @POST("login")
    suspend fun login(@Query("email") email: String, @Query("password") password: String): UserEntity?

    @POST("users")
    suspend fun register(@Body user: UserEntity): UserEntity

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserEntity?

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: UserEntity)

    @GET("users/{id}/addresses")
    suspend fun getAddressesForUser(@Path("id") userId: Int): List<AddressEntity>

    @POST("addresses")
    suspend fun insertAddress(@Body address: AddressEntity)

    @PUT("addresses/{id}")
    suspend fun updateAddress(@Path("id") id: Int, @Body address: AddressEntity)

    @DELETE("addresses/{id}")
    suspend fun deleteAddress(@Path("id") id: Int)
}