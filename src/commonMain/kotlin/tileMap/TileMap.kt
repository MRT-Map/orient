package tileMap

import com.soywiz.kmem.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korio.async.*
import com.soywiz.korma.geom.*
import kotlinx.coroutines.*

fun Container.tileMap(settings: TileSettings, views: Views) = TileMap(settings, views).addTo(this)

class TileMap(var settings: TileSettings, val views: Views) : Container() {
    var zoom = 8.0
    private var tiles = mutableMapOf<TileCoord, Deferred<Tile>>()

    val mouseWorldCoordinates
        get() =
            Point(
                views.globalMouseX - views.virtualWidth / 2,
                views.globalMouseY - views.virtualHeight / 2
            ) / 4 + worldOffset
    val worldOffset
        get() = Point(globalX, globalY) / -4

    init {
        launch(Dispatchers.Unconfined) { updateTilesShown() }
    }

    private suspend fun updateTilesShown() {
        val centerTileCoord = TileCoord(8, worldOffset.x.toInt() / 32, worldOffset.y.toInt() / 32)
        val worldWidth = views.virtualWidth / 64
        val worldHeight = views.virtualHeight / 64

        val newTiles = mutableMapOf<TileCoord, Deferred<Tile>>()
        for (x in
            (centerTileCoord.x - worldWidth / 2)..(centerTileCoord.x + worldWidth / 2)) for (y in
            (centerTileCoord.y - worldHeight / 2)..(centerTileCoord.y + worldHeight / 2)) {
            val tileCoord = TileCoord(8, x, -y)
            newTiles[tileCoord] =
                tiles[tileCoord] ?: async(Dispatchers.Unconfined) { tile(tileCoord) }
        }

        tiles
            .filter { (k, _) -> !newTiles.containsKey(k) }
            .values
            .forEach { if (it.isCompleted) it.await().removeFromParent() else it.cancel() }
        tiles = newTiles
    }

    override fun onParentChanged() {
        val view: View = parent ?: this

        mouse {
            draggable(selector = view, autoMove = true)
            upAnywhere { launch(Dispatchers.Unconfined) { updateTilesShown() } }
            scrollAnywhere {
                zoom += it.scrollDeltaYLines
                zoom = zoom.clamp(0.0, 10.0)
            }
        }
    }
}
