@file:Suppress("SpellCheckingInspection")

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import tileMap.TileSettings

@Serializable
data class Configuration(
    @SerialName("confighash") val configHash: Int,
    @SerialName("updaterate") val updateRate: Float,
    @SerialName("showplayerfacesinmenu") val showPlayerFacesInMenu: Boolean,
    @SerialName("joinmessage") val joinMessage: String,
    @SerialName("quitmessage") val quitMessage: String,
    @SerialName("spammessage") val spamMessage: String,
    @SerialName("webprefix") val webPrefix: String,
    @SerialName("defaultzoom") val defaultZoom: Int,
    @SerialName("sidebaropened") val sidebarOpened: String,
    @SerialName("dynmapversion") val dynMapVersion: String,
    @SerialName("coreversion") val coreVersion: String,
    val cyrillic: Boolean,
    @SerialName("showlayercontrol") val showLayerControl: String,
    @SerialName("grayplayerswhenhidden") val grayPlayersWhenHidden: Boolean,
    @SerialName("login-enabled") val loginEnabled: Boolean,
    @SerialName("loginrequired") val loginRequired: Boolean? = null,
    val title: String,
    @SerialName("msg-maptypes") val msgMapTypes: String,
    @SerialName("msg-players") val msgPlayers: String,
    @SerialName("msg-chatrequireslogin") val msgChatRequiresLogin: String,
    @SerialName("msg-chatnotallowed") val msgChatNotAllowed: String,
    @SerialName("msg-hiddennamejoin") val msgHiddenNameJoin: String,
    @SerialName("msg-hiddennamequit") val msgHiddenNameQuit: String,
    @SerialName("maxcount") val maxCount: Int,
    val worlds: Set<World>,
    @SerialName("defaultworld") val defaultWorld: String,
    @SerialName("defaultmap") val defaultMap: String,
    @SerialName("followmap") val followMap: String? = null,
    @SerialName("followzoom") val followZoom: Int? = null,
    @SerialName("allowwebchat") val allowWebChat: Boolean,
    @SerialName("allowchat") val allowChat: Boolean,
    @SerialName("webchat-interval") val webchatInterval: Float,
    @SerialName("webchat-requires-login") val webchatRequiresLogin: Boolean,
    @SerialName("chatlengthlimit") val chatLengthLimit: Int,
    val components: Set<JsonObject>,
    @SerialName("jsonfile") val jsonFile: Boolean? = null,
    @SerialName("loggedin") val loggedIn: Boolean? = null
)

@Serializable
data class World(
    val name: String,
    val title: String,
    val protected: Boolean,
    val center: Center,
    @SerialName("extrazoomout") val extraZoomOut: Int,
    @SerialName("sealevel") val seaLevel: Int,
    @SerialName("worldheight") val worldHeight: Int,
    val maps: Set<Map>
)

@Serializable data class Center(val x: Double, val y: Double, val z: Double)

@Serializable
data class Map(
    val type: String,
    val name: String,
    val title: String,
    val icon: String?,
    val prefix: String,
    val background: String?,
    @SerialName("backgroundday") val backgroundDay: String?,
    @SerialName("backgroundnight") val backgroundNight: String?,
    @SerialName("bigmap") val bigMap: Boolean,
    @SerialName("mapzoomout") val mapZoomOut: Byte,
    @SerialName("mapzoomin") val mapZoomIn: Byte,
    @SerialName("boostzoom") val boostZoom: Byte,
    @SerialName("tilescale") val tileScale: Byte,
    val protected: Boolean,
    @SerialName("image-format") val imageFormat: String,
    @SerialName("append_to_world") val appendToWorld: String? = null,
    val lighting: String,
    @SerialName("nightandday") val nightAndDay: Boolean,
    val shader: String,
    val perspective: String,
    val azimuth: Double,
    val inclination: Double,
    val scale: Int,
    @SerialName("worldtomap") val worldToMap: List<Double>,
    @SerialName("maptoworld") val mapToWorld: List<Double>,
    @SerialName("compassview") val compassView: String
) {
    fun tileSettings(url: String) = TileSettings(url, this.mapZoomOut, 128.0 / this.scale)
}
