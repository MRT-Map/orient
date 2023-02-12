import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korio.net.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tileMap.*

suspend fun main() =
    Korge(bgcolor = Colors["#2b2b2b"]) {
        val sceneContainer = sceneContainer()

        sceneContainer.changeTo({ MyScene() })
    }

class MyScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        val client = HttpClient()
        val config =
            Json.decodeFromString<Configuration>(
                client.readString(
                    "https://dynmap.minecartrapidtransit.net/standalone/dynmap_config.json"
                )
            )
        println(config)
        // Json.decodeFromStream<Configuration>()
        val tileMap = tileMap(TileSettings.default, views)
        hud(tileMap) { gameWindow.fps }
    }
}
