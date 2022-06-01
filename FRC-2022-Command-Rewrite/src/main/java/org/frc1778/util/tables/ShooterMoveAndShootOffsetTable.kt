package org.frc1778.util.tables

import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.inches
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.tan

object ShooterMoveAndShootOffsetTable {
    private lateinit var table: HashMap<Array<Double>, Double>


    private const val h = 1.905
    private const val acel = -.05
    private const val g = -9.8 + acel



    fun initialize() {
        for(distance in 0..300) {
            val d = distance.inches.value

            val a = (30 * (0.75 + (3.302/d))).degrees.value

            fun arc(x: Double) : Double = (-(h/d.pow(2)) - (tan(a)/d) * x.pow(2)) + (((2*h/d) + tan(a)) * x)


            val midPoint = (-((2*h/d) + tan(a))/(2 * (-(h/d.pow(2)) - (tan(a)/d))))
            val heightMax = arc(midPoint)
            val vy = sqrt(-2 * g * heightMax)
            val vx = ((midPoint - (.5 * acel * (vy/g).pow(2)))/(vy/-g))

            val t = d/vx
            for(velocity in -400..400){
                val vr = (velocity.toDouble() / 100)
                for(turretAngle in -180..180) {
                    var minDist = Double.MAX_VALUE
                    fun vxf(x: Double) : Double = vx + (vr * cos((turretAngle + x).degrees.value))
                    fun vz(x: Double) : Double = -vxf(x) * tan(x.degrees.value)
                    fun vp(x: Double) : Double = vr * tan(x.degrees.value)
                    for(offset in -250..250) {
                        val theta = offset.toDouble()/10.0

                        val d1 = ((vp(theta) + vz(theta)) * t) + (0.5 * (acel) * t.pow(2))
                        val d2 = d * tan(theta.degrees.value)


                    }
                }

            }
        }
    }



    operator fun get(k: Array<Double>) = table[k]

}