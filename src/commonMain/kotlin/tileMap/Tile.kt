package tileMap

import com.soywiz.klock.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.net.http.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*

suspend fun TileMap.tile(coord: TileCoord) = Tile(coord).addTo(this).updatePosition(this).updateImage(this.settings)

val imageCache = mutableMapOf<TileCoord, Bitmap>()

class Tile(private val coord: TileCoord) : Container() {
    suspend fun updateImage(settings: TileSettings): Tile {
        val url = coord.url(settings)
        val client = HttpClient()
        try {
            val bitmap = imageCache.getOrPut(coord) {
                println(url)
                PNG.read(client.readBytes(url), filename = coord.toString()).asumePremultiplied()
            }
            image(bitmap)
                .size(128, 128)
                .anchor(Anchor.TOP_LEFT)
                .alpha(0).let {
                    it.tween(it::alpha[1], time = 0.5.seconds, easing = Easing.LINEAR)
                }
        } catch (e: Http.HttpException) {
            if (e.statusCode != 404) throw e
        }

        return this
    }

    fun updatePosition(map: TileMap): Tile {
        xy(
            (coord.x * 128 + map.views.virtualWidth / 2).toDouble(),
            (-coord.y * 128 - map.settings.maxZoomRange * 8.0 + map.views.virtualHeight / 2)
        )
        return this
    }
}
