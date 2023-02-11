package tileMap

import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korio.async.*
import kotlinx.coroutines.*

fun Container.tileMap(settings: TileSettings, views: Views) = TileMap(settings, views).addTo(this)

class TileMap(var settings: TileSettings, val views: Views) : Container() {
    private var tiles = mutableListOf<Tile>()

    init {
        for (tileCoord in arrayOf(TileCoord(8, 0, 0), TileCoord(8, 0, -1), TileCoord(8, 1, 0), TileCoord(8, 1, -1))) {
            launch(Dispatchers.Unconfined) { tiles.add(tile(tileCoord)) }
        }
    }

    override fun onParentChanged() {

        val view: View = parent ?: this

        mouse {
            draggable(selector = view, autoMove = true)
            scrollAnywhere {

            }
        }
    }
}
