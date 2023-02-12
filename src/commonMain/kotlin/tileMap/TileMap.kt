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
    private var tiles = mutableListOf<Tile>()

    val mouseWorldCoordinates get() = Point(views.globalMouseX - views.virtualWidth / 2, views.globalMouseY - views.virtualHeight / 2) / 4 + worldOffset
    val worldOffset get() = Point(globalX, globalY) / -4
    
    init {
        for (tileCoord in arrayOf(TileCoord(8, 0, 0), TileCoord(8, 0, -1), TileCoord(8, -1, 0), TileCoord(8, -1, -1))) {
            launch(Dispatchers.Unconfined) { tiles.add(tile(tileCoord)) }
        }
    }

    override fun onParentChanged() {
        val view: View = parent ?: this

        mouse {
            draggable(selector = view, autoMove = true)
            scrollAnywhere {
                zoom += it.scrollDeltaYLines
                zoom = zoom.clamp(0.0, 10.0)
            }
        }
    }
}
