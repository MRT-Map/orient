package tileMap

import com.soywiz.klock.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.net.http.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import kotlin.math.pow

suspend fun TileMap.tile(coord: TileCoord) =
    Tile(coord, this.settings).addTo(this).updatePosition(this).updateImage()

val imageCache = mutableMapOf<TileCoord, Bitmap>()

class Tile(private val coord: TileCoord, private val settings: TileSettings) : Container() {
    private var image: Image? = null
    private val imageSize
        get() = 2.0.pow(15 - coord.z)

    suspend fun updateImage(): Tile {
        val url = coord.url(settings)
        val client = HttpClient()
        try {
            val bitmap =
                imageCache.getOrPut(coord) {
                    println(url)
                    PNG.read(client.readBytes(url), filename = coord.toString())
                        .asumePremultiplied()
                }
            image =
                image(bitmap).size(imageSize, imageSize).anchor(Anchor.TOP_LEFT).alpha(0).also {
                    it.tween(it::alpha[1], time = 0.5.seconds, easing = Easing.LINEAR)
                }
        } catch (e: Http.HttpException) {
            if (e.statusCode != 404) throw e
        }

        return this
    }

    fun updatePosition(map: TileMap): Tile {
        xy(
            (coord.x * imageSize + map.views.virtualWidth / 2),
            (-coord.y * imageSize - map.settings.maxZoomRange * 8.0 + map.views.virtualHeight / 2)
        )
        return this
    }
}
