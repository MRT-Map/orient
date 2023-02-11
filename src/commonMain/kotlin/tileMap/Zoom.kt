package tileMap

import kotlin.math.*

class Zoom(val z: Float) {
    fun mapSize(settings: TileSettings) = 8.0 * 2.0.pow(settings.maxTileZoom.toDouble() - z) * settings.maxZoomRange
    fun worldSize(settings: TileSettings) = (mapSize(settings) / 8.0).toInt()
}
