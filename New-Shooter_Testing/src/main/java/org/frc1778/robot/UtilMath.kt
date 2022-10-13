package org.frc1778.robot

object UtilMath {
    fun clamp(x: Double, min: Double, max: Double) = if(x < min) min else if(x > max) max else x
    fun wrap(x: Double, min: Double, max: Double, threshold: Double) = if(x < min - threshold) max else if(x > max + threshold) min else x
    fun wrap(x: Double, min: Double, max: Double) = wrap(x, min, max, 0.0)
    
}