package com.zenaani.redlinegarage.data.remote

import com.zenaani.redlinegarage.data.dto.AccessoryPagedDto
import com.zenaani.redlinegarage.data.dto.BikeDetailsDto
import com.zenaani.redlinegarage.data.dto.BrandPagedDto
import com.zenaani.redlinegarage.data.dto.ModelPagedDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("brands")
    suspend fun getBrands() : Response<BrandPagedDto>

    @GET("brands/{brandId}/bikes")
    suspend fun getModels(@Path("brandId") brandId: Int) : Response<ModelPagedDto>

    @GET("bikes/{modelId}")
    suspend fun getBikeDetails(@Path("modelId") modelId: Int) : Response<BikeDetailsDto>

    @GET("bikes/{bikeId}/accessories")
    suspend fun getAccessories(@Path("bikeId") bikeId: Int) : Response<AccessoryPagedDto>

}