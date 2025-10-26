package com.mediassist.tracknheal.data.models

data class HealthMetric(
    val parameter: ParameterType,
    val value: Double,
    val secondaryValue: Double? = null, // For BP systolic/diastolic
    val unit: MeasurementUnit,
    val status: HealthStatus
) {
    fun getFormattedValue(): String {
        return if (secondaryValue != null) {
            "${value.toInt()}/${secondaryValue.toInt()}"
        } else {
            value.toInt().toString()
        }
    }

    fun getFullValueWithUnit(): String {
        return "${getFormattedValue()} ${unit.getDisplayName()}"
    }

    companion object {
        fun calculateStatus(parameter: ParameterType, value: Double, secondaryValue: Double? = null): HealthStatus {
            return when (parameter) {
                ParameterType.GLUCOSE -> {
                    when {
                        value < 70 -> HealthStatus.LOW
                        value <= 100 -> HealthStatus.NORMAL
                        else -> HealthStatus.ELEVATED
                    }
                }
                ParameterType.BLOOD_PRESSURE -> {
                    val systolic = value
                    val diastolic = secondaryValue ?: 0.0
                    when {
                        systolic < 90 || diastolic < 60 -> HealthStatus.LOW
                        systolic < 120 && diastolic < 80 -> HealthStatus.NORMAL
                        else -> HealthStatus.ELEVATED
                    }
                }
                ParameterType.CHOLESTEROL -> {
                    when {
                        value < 200 -> HealthStatus.NORMAL
                        else -> HealthStatus.ELEVATED
                    }
                }
                ParameterType.HBA1C -> {
                    when {
                        value < 5.7 -> HealthStatus.NORMAL
                        else -> HealthStatus.ELEVATED
                    }
                }
            }
        }

        fun getIdealRange(parameter: ParameterType): String {
            return when (parameter) {
                ParameterType.GLUCOSE -> "70-100"
                ParameterType.BLOOD_PRESSURE -> "<120/80"
                ParameterType.CHOLESTEROL -> "<200"
                ParameterType.HBA1C -> "<5.7"
            }
        }
    }
}
