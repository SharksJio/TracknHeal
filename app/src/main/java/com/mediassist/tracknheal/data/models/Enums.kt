package com.mediassist.tracknheal.data.models

enum class ParameterType {
    GLUCOSE,
    BLOOD_PRESSURE,
    CHOLESTEROL,
    HBA1C;

    fun getDisplayName(): String {
        return when (this) {
            GLUCOSE -> "Glucose"
            BLOOD_PRESSURE -> "Blood Pressure"
            CHOLESTEROL -> "Cholesterol"
            HBA1C -> "HbA1c"
        }
    }
}

enum class MeasurementUnit {
    MG_DL,    // mg/dL
    MMOL_L,   // mmol/L
    MMHG,     // mmHg
    PERCENT;  // %

    fun getDisplayName(): String {
        return when (this) {
            MG_DL -> "mg/dL"
            MMOL_L -> "mmol/L"
            MMHG -> "mmHg"
            PERCENT -> "%"
        }
    }

    companion object {
        fun getDefaultUnit(parameterType: ParameterType): MeasurementUnit {
            return when (parameterType) {
                ParameterType.GLUCOSE -> MG_DL
                ParameterType.BLOOD_PRESSURE -> MMHG
                ParameterType.CHOLESTEROL -> MG_DL
                ParameterType.HBA1C -> PERCENT
            }
        }
    }
}

enum class HealthStatus {
    NORMAL,
    ELEVATED,
    LOW;

    fun getDisplayName(): String {
        return when (this) {
            NORMAL -> "Normal"
            ELEVATED -> "Elevated"
            LOW -> "Low"
        }
    }
}
