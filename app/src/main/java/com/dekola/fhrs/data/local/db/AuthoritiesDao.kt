package com.dekola.fhrs.data.local.db

import androidx.room.*
import com.dekola.fhrs.data.model.AuthoritiesEntity

@Dao
interface AuthoritiesDao {

    @Transaction
    suspend fun updateAuthorities(users: List<AuthoritiesEntity?>) {
        deleteAllAuthorities()
        saveAuthorities(users)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAuthorities(users: List<AuthoritiesEntity?>)

    @Query("DELETE FROM authorities_table")
    suspend fun deleteAllAuthorities()

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun saveAuthorities(authoritiesEntity... AuthoritiesEntity)

    @Query("SELECT * FROM authorities_table")
    suspend fun getAllAuthorities(): List<AuthoritiesEntity>

}