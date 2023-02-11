package tileMap

import com.soywiz.korma.geom.*
import kotlin.math.*

data class TileCoord(val z: Short, val x: Int, val y: Int) {

    fun url(settings: TileSettings): String {
        val zz = 2.0.pow(settings.maxTileZoom - z)
        val xy = Point(x, y)

        val group = (xy * zz / settings.maxZoomRange).floor().asInt()

        val numInGroup = (xy * zz).asInt()

        var zzz = ""
        var i = settings.maxTileZoom
        while (i > z) {
            zzz += "z"
            i--
        }

        if (zzz.isNotEmpty()) zzz += "_"

        return "${settings.url}/${group.x}_${group.y}/$zzz${numInGroup.x}_${numInGroup.y}.png"
    }
}
