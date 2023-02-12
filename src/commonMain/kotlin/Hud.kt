import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import tileMap.*

fun Container.hud(tileMap: TileMap, fps: () -> Int) = Hud(tileMap, fps).addTo(this)

class Hud(private val tileMap: TileMap, val fps: () -> Int): Container() {
    private val shownText get() = "Zoom: ${tileMap.zoom} | FPS: ${fps()} | Map Offset: ${tileMap.worldOffset} | Mouse Coordinates: ${tileMap.mouseWorldCoordinates}"

    private val textView = text(shownText, 24.0, Colors.RED)

    init {
        addUpdater {
            textView.text = shownText
        }
    }
}
