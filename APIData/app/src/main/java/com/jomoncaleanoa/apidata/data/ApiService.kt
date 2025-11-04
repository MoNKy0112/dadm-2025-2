package com.jomoncaleanoa.apidata.data

import retrofit2.http.GET
import retrofit2.http.Path

data class MapInfo(
    val id: Int,
    val name: String,
    val description: String,
    val departmentId: Any?,
    val urlImages: List<String>,
    val urlSource: String,
    val department: Any?
)

interface ApiService {
    @GET("Map/{id}")
    suspend fun getMapById(@Path("id") id: Int): MapInfo
}
