import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import tileMap.*

fun Container.hud(tileMap: TileMap) = Hud(tileMap).addTo(this)

class Hud(private val tileMap: TileMap): Container() {
    private val shownText get() = "Zoom: ${tileMap.zoom}"

    private val textView = text(shownText, 12.0, Colors.RED)

    init {
        addUpdater {
            textView.text = shownText
        }
    }
}
