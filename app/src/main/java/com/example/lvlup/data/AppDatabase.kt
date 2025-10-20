package com.example.lvlup.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
        AddressEntity::class,
        CouponEntity::class
    ],
    version = 11                // Sube la versión si cambiaste las entidades
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun couponDao(): CouponDao
}
