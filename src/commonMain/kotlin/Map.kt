import com.soywiz.klock.hr.hrMilliseconds
import com.soywiz.kmem.clamp
import com.soywiz.korev.Key
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tiled.readTiledMap
import com.soywiz.korge.tiled.tiledMapView
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addHrUpdater
import com.soywiz.korge.view.camera
import com.soywiz.korge.view.position
import com.soywiz.korio.file.std.resourcesVfs
import kotlin.math.pow

class Map(val dependency: Dependency) : Scene() {
    suspend override fun Container.sceneInit() {
        val tiledMap = resourcesVfs["maps\\${MainModule.currentMap}.tmx"].readTiledMap()
        val camera = camera {
            tiledMapView(tiledMap) {
                //pass
            }
        }
        var dx = 0.0
        var dy = 0.0
        addHrUpdater {
            //val scale = 1.0 / (it / 16.666666.hrMilliseconds)
            val scale = if (it == 0.hrMilliseconds) 0.0 else (it / 16.666666.hrMilliseconds)
            if (views.input.keys[Key.RIGHT]) dx -= 1.0
            if (views.input.keys[Key.LEFT]) dx += 1.0
            if (views.input.keys[Key.UP]) dy += 1.0
            if (views.input.keys[Key.DOWN]) dy -= 1.0
            dx = dx.clamp(-10.0, +10.0)
            dy = dy.clamp(-10.0, +10.0)
            camera.x += dx * scale
            camera.y += dy * scale
            dx *= 0.9.pow(scale)
            dy *= 0.9.pow(scale)
        }
    }
}
