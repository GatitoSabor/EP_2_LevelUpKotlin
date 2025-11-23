package com.example.lvlup.repository

import com.example.lvlup.data.CouponEntity
import retrofit2.http.*

interface CouponApiService {
    @POST("coupons")
    suspend fun insertCoupon(@Body coupon: CouponEntity)

    @GET("coupons/unused")
    suspend fun getUnusedCoupons(@Query("userId") userId: Int): List<CouponEntity>

    @PUT("coupons/{id}")
    suspend fun updateCoupon(@Path("id") id: Int, @Body coupon: CouponEntity)
}