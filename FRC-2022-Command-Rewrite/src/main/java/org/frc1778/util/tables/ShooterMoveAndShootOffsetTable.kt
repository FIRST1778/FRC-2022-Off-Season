package org.frc1778.util.tables

import com.beust.klaxon.Parser
import com.github.salomonbrys.kotson.double
import com.google.gson.JsonObject
import edu.wpi.first.wpilibj.Filesystem
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.inches
import org.json.simple.JSONObject
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
    private var tableJson by Delegates.notNull<JsonObject>()

    private const val h = 1.905
    private const val acel = -.05
    private const val g = -9.8 + acel

    fun generateJSON() {
        val json = JSONObject()
        var totalTime = 0.0
        var count = 0

        for(distance in 60..300) {
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

        PrintWriter(FileWriter("ShooterMoveAndShootOffsetTable.json", Charset.defaultCharset()))
            .use { it.write(json.toString()) }
        println("Total Calculation Time(s): $totalTime\t|\t Average Calculation Time(s): ${totalTime/count}")
    }

    private fun parse(name: String): Any? {
        val cls = Parser::class.java

        return cls.getResourceAsStream(name)?.let { inputStream ->
            return Parser.default().parse(inputStream)
        }
    }

    fun loadJson() {

//        val file = Filesystem.getDeployDirectory().toPath().toString() + "/ShooterMoveAndShootOffsetTable.json"
        val file = "/ShooterMoveAndShootOffsetTable.json"
        tableJson = parse(file)!! as JsonObject
    }

    private fun round(list: DoubleArray) {
        list[0] = list[0].roundToInt().toDouble()
        list[1] = (list[1] * 10).roundToInt().toDouble() / 10
        list[2] = list[2].roundToInt().toDouble()
    }



    operator fun get(k: List<Double>): Double {
        val key = k.toDoubleArray()
        round(key)

        return tableJson[key.asList().toString()].asDouble
    }

}