package com.dekola.fhrs.data.local.dataSource

import com.dekola.fhrs.data.local.db.AuthoritiesDao
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.data.toEntity
import com.dekola.fhrs.data.toPresentation
import javax.inject.Inject

class AuthoritiesLocalDataSource @Inject constructor(private val authoritiesDao: AuthoritiesDao) :
    IAuthoritiesLocalDataSource {

    override suspend fun saveAuthorities(data: List<AuthoritiesPresentation?>?) {
        data?.map { it?.toEntity() }?.let { authoritiesDao.updateAuthorities(it) }
    }

    override suspend fun fetchSavedAuthorities(): List<AuthoritiesPresentation?> {
        return authoritiesDao.getAllAuthorities().map { it.toPresentation() }
    }

}