package com.tilikki.training.unimager.demo.database

import androidx.room.*
import io.reactivex.Observable

@Dao
interface UserDao {
    @Query("select * from users where id LIKE :userId")
    fun getUserById(userId: String): Observable<EntityUser>

    @Query("select * from users where username LIKE :username")
    fun getUserByUsername(username: String): Observable<EntityUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: EntityUser)

    @Transaction
    @Query("select * from users where username LIKE :username")
    fun getUserPhotosByUsername(username: String): Observable<UserPhotoRelationship>
}