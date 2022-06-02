package org.frc1778.util.tables

import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.inches
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.charset.Charset
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.math.tan
import kotlin.properties.Delegates
import kotlin.system.exitProcess

object ShooterMoveAndShootOffsetTable {
    private var tableJson by Delegates.notNull<JSONObject>()
    private data class RobotInfo(val state: DoubleArray, val offset: Double) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as RobotInfo

            if (!state.contentEquals(other.state)) return false
            if (offset != other.offset) return false

            return true
        }

        override fun hashCode(): Int {
            var result = state.contentHashCode()
            result = 31 * result + offset.hashCode()
            return result
        }
    }

    private const val h = 1.905
    private const val acel = -.05
    private const val g = -9.8 + acel

    fun generateJSON() {
        val json = JSONObject()
        var totalTime = 0.0
        var count = 0

        for(distance in 60..300 step 3) {
            val d = distance.inches.value

            val a = (30 * (0.75 + (3.302/d))).degrees.value

            fun arc(x: Double) : Double = (-(h/d.pow(2)) - (tan(a)/d) * x.pow(2)) + (((2*h/d) + tan(a)) * x)


            val midPoint = (-((2*h/d) + tan(a))/(2 * (-(h/d.pow(2)) - (tan(a)/d))))
            val heightMax = arc(midPoint)
            val vy = sqrt(-2 * g * heightMax)
            val vx = ((midPoint - (.5 * acel * (vy/g).pow(2)))/(vy/-g))

            if(midPoint.isNaN() || heightMax.isNaN() || vy.isNaN() || vx.isNaN()) {
                println("MidPoint: $midPoint")
                println("heightMax: $heightMax")
                println("vy: $vy")
                println("vx: $vx")
            }

            val t = d/vx
            for(velocity in -40..40){
                val vr = (velocity.toDouble() / 10)
                for(turretAngle in -180..180) {
                    val startTime = System.currentTimeMillis()
                    var minValue by Delegates.notNull<Double>()
                    var bestOffset = 0.0

                    fun vxf(x: Double) : Double = max(vx + (vr * cos((turretAngle + x).degrees.value)), 0.0)
                    fun vz(x: Double) : Double = -vxf(x) * tan(x.degrees.value)
                    fun vp(x: Double) : Double = vr * tan(x.degrees.value)

//                    println("Testing Functions: vxf: ${vxf(0.1)} \t|\t vz: ${vz(0.1)} \t|\t vp${vp(0.1)}")

                    val diffPositive = abs(((vp(0.1) + vz(0.1)) * t) + (0.5 * (acel) * t.pow(2)) - (d * tan(0.1.degrees.value)))
                    val diffNegative = abs(((vp(-0.1) + vz(-0.1)) * t) + (0.5 * (acel) * t.pow(2)) - (d * tan((-0.1).degrees.value)))
                    if(diffPositive.isNaN() || diffNegative.isNaN()) {
                        println(cos((turretAngle + 0.1).degrees.value))
                        println("diffPositive: $diffPositive \t|\t diffNegative: $diffNegative")
                        println("d: $d | vr: $vr | turretAngle: $turretAngle")
                        exitProcess(1)
                    }
//                    println("diffPositive: $diffPositive \t|\t diffNegative: $diffNegative")

                    if(diffPositive < diffNegative) {
                        minValue = diffPositive
                        var lastValue = minValue

                        for(offset in 1..250) {
                            val theta = offset.toDouble()/10.0

                            val diff = abs(((vp(theta) + vz(theta)) * t) + (0.5 * (acel) * t.pow(2)) - (d * tan(theta.degrees.value)))
                            if(diff < minValue) {
                                minValue = diff
                                bestOffset = theta
                            }
                            if(diff > lastValue) break
                            lastValue = diff
                        }
                    } else {
                        minValue = diffNegative
                        var lastValue = minValue

                        for(offset in -1 downTo -250) {
                            val theta = (offset.toDouble()/10.0)

                            val diff = abs(((vp(theta) + vz(theta)) * t) + (0.5 * (acel) * t.pow(2)) - (d * tan(theta.degrees.value)))
                            if(diff < minValue) {
                                minValue = diff
                                bestOffset = theta
                            }
                            if(diff > lastValue) break
                            lastValue = diff
                        }
                    }
                    val endTime = System.currentTimeMillis()
                    if(minValue.isNaN()) {
                        println("d: $d | vr: $vr | turretAngle: $turretAngle")
                        exitProcess(1)
                    }
//                    println("Calculation $count of ${(300-75)*80*360} Complete in Time: ${endTime-startTime} | with a angle of $bestOffset")
                    totalTime += endTime - startTime
                    count++

                    json[listOf(d,vr,turretAngle.toDouble())] = bestOffset
                }

            }
        }

        PrintWriter(FileWriter("FRC-2022-Command-Rewrite/src/main/deploy/ShooterMoveAndShootOffsetTable.json", Charset.defaultCharset()))
            .use { it.write(json.toJSONString()) }
        println("Total Calculation Time(s): $totalTime\t|\t Average Calculation Time(s): ${totalTime/count}")
    }


    fun loadJson() {
        tableJson = JSONParser().parse(FileReader("FRC-2022-Command-Rewrite/src/main/deploy/ShooterMoveAndShootOffsetTable.json")) as JSONObject

    }

    private fun round(list: DoubleArray) {
        list[0] = list[0].roundToInt().toDouble()
        list[1] = (list[1] * 10).roundToInt().toDouble() / 10
        list[2] = list[2].roundToInt().toDouble()
    }



    operator fun get(k: List<Double>): Double {
        val key = k.toDoubleArray()
        round(key)

        return tableJson[key.asList().toString()] as Double
    }

}