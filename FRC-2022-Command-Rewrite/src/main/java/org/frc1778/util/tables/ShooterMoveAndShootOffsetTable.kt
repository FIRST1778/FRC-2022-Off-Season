package org.frc1778.util.tables

import org.ghrobotics.lib.mathematics.units.derived.degrees
import kotlin.math.tan

object ShooterMoveAndShootOffsetTable {
    private lateinit var table: HashMap<Array<Double>, Double>




    fun initialize() {
        for(v in -400..400){
            val velocity = v.toDouble() / 100
            for(distance in 0..300) {
                for(turretAngle in -180..180) {
                    var minDist = Double.MAX_VALUE
                    for(offset in -250..250) {
                        val d1 = 0.0
                        val d2 = distance * tan(offset.degrees.value)


                    }
                }

            }
        }
    }



    operator fun get(k: Array<Double>) = table[k]

}