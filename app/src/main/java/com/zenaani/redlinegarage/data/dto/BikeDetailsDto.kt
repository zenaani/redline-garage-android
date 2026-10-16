package com.zenaani.redlinegarage.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BikeDetailsDto(
    val id: Long,
    val brandName: String,
    val modelName: String,
    val variant: String,
    val segment: String,
    val launchYear: String,
    val glbModel: String,
    val engine: EngineDto,
    val performance: PerformanceDto
)

@Serializable
data class EngineDto(
    val engineCc: Double,
    val engineType: String,
    val maxPowerBhp: Double,
    val powerRpm: Int,
    val maxTorqueNm: Double,
    val torqueRpm: Int,
    val transmission: String,
    val kerbWeightKg: Double
)

@Serializable
data class PerformanceDto(
    val id: Long,
    val zeroToSixtyBest: Double,
    val zeroToSixtyAvg: Double,
    val zeroToHundredBest: Double,
    val zeroToHundredAvg: Double,
    val topSpeedTestedKmh: Double,
    val quarterMileBest: Double,
    val quarterMileAvg: Double
)