package tileMap

data class TileSettings(val url: String, val maxTileZoom: Short, val maxZoomRange: Double) {
    companion object {
        val default get() = TileSettings("https://dynmap.minecartrapidtransit.net/tiles/new/flat", 8, 16.0)
    }
}
