package com.example.lvlup.repository

import com.example.lvlup.data.CouponEntity

class CouponRepository(private val api: CouponApiService) {
    suspend fun insertCoupon(coupon: CouponEntity) =
        api.insertCoupon(coupon)

    suspend fun getUnusedCoupons(userId: Int): List<CouponEntity> =
        api.getUnusedCoupons(userId)

    suspend fun useCoupon(coupon: CouponEntity) =
        api.updateCoupon(coupon.id, coupon.copy(isUsed = true))
}