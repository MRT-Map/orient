package tileMap

data class TileSettings(val url: String, val maxTileZoom: Byte, val maxZoomRange: Double) {
    companion object {
        val default
            get() = TileSettings("https://dynmap.minecartrapidtransit.net/tiles/new/flat", 8, 32.0)
    }
}
