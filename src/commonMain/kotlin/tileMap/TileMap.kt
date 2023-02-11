package tileMap

import com.soywiz.korge.view.*
import com.soywiz.korio.async.*
import com.soywiz.korma.geom.*
import kotlinx.coroutines.*

fun Container.tileMap(settings: TileSettings) = TileMap(settings).addTo(this)

class TileMap(var settings: TileSettings): Container() {
    val view = Point(0, 0)
    init {
        launch(Dispatchers.Unconfined) { tile(TileCoord(8, 0, 0)) }
        launch(Dispatchers.Unconfined) { tile(TileCoord(8, 0, -1)) }
    }
}
