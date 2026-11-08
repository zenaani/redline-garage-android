package com.zenaani.redlinegarage.data.repository

import com.zenaani.redlinegarage.data.dto.AccessoryDto
import com.zenaani.redlinegarage.data.dto.AccessoryPagedDto
import com.zenaani.redlinegarage.data.dto.BikeDetailsDto
import com.zenaani.redlinegarage.data.remote.ApiService
import com.zenaani.redlinegarage.data.dto.BrandPagedDto
import com.zenaani.redlinegarage.data.dto.ModelPagedDto
import com.zenaani.redlinegarage.data.dto.UserReviewPagedDto
import javax.inject.Inject

class BrandRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getBrands(): Result<BrandPagedDto> = runCatching {
        val response = apiService.getBrands()
        if(response.isSuccessful) {
            response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("API Error: ${response.code()}")
        }
    }

    suspend fun getModels(brandId: Int): Result<ModelPagedDto> = runCatching {
        val response = apiService.getModels(brandId)
        if(response.isSuccessful) {
            response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("API Error: ${response.code()}")
        }
    }

    suspend fun getBikeDetails(modelId : Int) : Result<BikeDetailsDto> = runCatching {
        val response = apiService.getBikeDetails(modelId)
        if(response.isSuccessful) {
            response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("API Error: ${response.code()}")
        }
    }

    suspend fun getAccessories(bikeId: Int) : Result<AccessoryPagedDto> = runCatching {
        val response = apiService.getAccessories(bikeId)
        if(response.isSuccessful) {
            response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("API Error: ${response.code()}")
        }
    }

    suspend fun getUserReviews(accessoryId: Int) : Result<UserReviewPagedDto> = runCatching {
        val response = apiService.getUserReviews(accessoryId)
        if(response.isSuccessful) {
            response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("API Error: ${response.code()}")
        }
    }

    suspend fun getAccessoryDetails(accessoryId: Int) : Result<AccessoryDto> = runCatching {
        val response = apiService.getAccessory(accessoryId)
        if(response.isSuccessful) {
            response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("API Error: ${response.code()}")
        }
    }

}