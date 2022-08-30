package com.dekola.fhrs.data

import com.google.gson.annotations.SerializedName

data class AuthoritiesResponse(

	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("links")
	val links: List<LinksItem?>? = null,

	@field:SerializedName("authorities")
	var authorities: List<AuthoritiesResponseItem>? = null
)

data class Meta(

	@field:SerializedName("extractDate")
	val extractDate: String? = null,

	@field:SerializedName("pageNumber")
	val pageNumber: Int? = null,

	@field:SerializedName("returncode")
	val returncode: String? = null,

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("pageSize")
	val pageSize: Int? = null,

	@field:SerializedName("totalCount")
	val totalCount: Int? = null,

	@field:SerializedName("dataSource")
	val dataSource: String? = null,

	@field:SerializedName("itemCount")
	val itemCount: Int? = null
)

data class LinksItem(

	@field:SerializedName("rel")
	val rel: String? = null,

	@field:SerializedName("href")
	val href: String? = null
)

data class AuthoritiesResponseItem(

	@field:SerializedName("CreationDate")
	val creationDate: String? = null,

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("FriendlyName")
	val friendlyName: String? = null,

	@field:SerializedName("FileName")
	val fileName: String? = null,

	@field:SerializedName("FileNameWelsh")
	val fileNameWelsh: Any? = null,

	@field:SerializedName("Url")
	val url: String? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("EstablishmentCount")
	val establishmentCount: Int? = null,

	@field:SerializedName("LastPublishedDate")
	val lastPublishedDate: String? = null,

	@field:SerializedName("SchemeType")
	val schemeType: Int? = null,

	@field:SerializedName("LocalAuthorityId")
	val localAuthorityId: Int? = null,

	@field:SerializedName("RegionName")
	val regionName: String? = null,

	@field:SerializedName("LocalAuthorityIdCode")
	val localAuthorityIdCode: String? = null,

	@field:SerializedName("links")
	val links: List<LinksItem?>? = null,

	@field:SerializedName("SchemeUrl")
	val schemeUrl: String? = null
)
