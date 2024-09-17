package com.example.coffetech.model

data class UnitMeasure(
    val unit_of_measure_id: Int,
    val name: String,
    val abbreviation: String,
    val unit_of_measure_type: UnitMeasureType
)

data class UnitMeasureType(
    val unit_of_measure_type_id: Int,
    val name: String
)
