package tileMap

import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.net.http.*

suspend fun TileMap.tile(coord: TileCoord) = Tile(coord).addTo(this).update(this.settings)

class Tile(private val coord: TileCoord): Container() {
    suspend fun update(settings: TileSettings): Tile {
        xy(
            coord.x * Zoom(coord.z.toFloat()).mapSize(settings) - 0.5,
            -coord.y * Zoom(coord.z.toFloat()).mapSize(settings) + settings.maxZoomRange + 0.5
        )
        val url = coord.url(settings)
        val client = HttpClient()
        image(PNG.read(client.readBytes(url), filename = coord.toString()))
            .size(Zoom(coord.z.toFloat()).mapSize(settings), Zoom(coord.z.toFloat())
                .mapSize(settings))

        return this
    }
}
