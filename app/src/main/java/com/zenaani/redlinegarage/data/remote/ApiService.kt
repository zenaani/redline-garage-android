package com.zenaani.redlinegarage.data.remote

import com.zenaani.redlinegarage.data.dto.BikeDetailsDto
import com.zenaani.redlinegarage.data.dto.BrandResponseDto
import com.zenaani.redlinegarage.data.dto.ModelResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("brands")
    suspend fun getBrands() : Response<BrandResponseDto>

    @GET("brands/{brandId}/bikes")
    suspend fun getModels(@Path("brandId") brandId: Int) : Response<ModelResponseDto>

    @GET("bikes/{modelId}")
    suspend fun getBikeDetails(@Path("modelId") modelId: Int) : Response<BikeDetailsDto>

}