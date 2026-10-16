package com.zenaani.redlinegarage.data.repository

import com.zenaani.redlinegarage.data.dto.BikeDetailsDto
import com.zenaani.redlinegarage.data.remote.ApiService
import com.zenaani.redlinegarage.data.dto.BrandResponseDto
import com.zenaani.redlinegarage.data.dto.ModelResponseDto
import javax.inject.Inject

class BrandRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getBrands(): Result<BrandResponseDto> = runCatching {
        val response = apiService.getBrands()
        if(response.isSuccessful) {
            response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("API Error: ${response.code()}")
        }
    }

    suspend fun getModels(brandId: Int): Result<ModelResponseDto> = runCatching {
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

}