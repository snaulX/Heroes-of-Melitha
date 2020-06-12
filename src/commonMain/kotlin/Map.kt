import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tiled.readTiledMap
import com.soywiz.korge.tiled.tiledMapView
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.camera
import com.soywiz.korge.view.position
import com.soywiz.korio.file.std.resourcesVfs

class Map(val dependency: Dependency) : Scene() {
    suspend override fun Container.sceneInit() {
        val tiledMap = resourcesVfs["maps\\${MainModule.currentMap}.tmx"].readTiledMap()
        val camera = camera {
            tiledMapView(tiledMap) {
                //pass
            }
        }
    }
}
