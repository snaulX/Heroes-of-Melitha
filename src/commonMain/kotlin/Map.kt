import com.soywiz.klock.hr.hrMilliseconds
import com.soywiz.klock.milliseconds
import com.soywiz.kmem.clamp
import com.soywiz.korev.Key
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tiled.readTiledMap
import com.soywiz.korge.tiled.tiledMapView
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.async
import com.soywiz.korio.async.launch
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.serialization.xml.readXml
import com.soywiz.korma.geom.shape.Shape2d
import kotlin.math.pow
import kotlin.random.Random

class Map(val dependency: Dependency) : Scene() {
    val speed = MainModule.hero.speed
    lateinit var camera: Camera
    var dx = MapParser.player.x
    var dy = MapParser.player.y

    suspend override fun Container.sceneInit() {
        launch {
            MapParser.parse(resourcesVfs["maps\\${MainModule.currentMap}.xml"].readXml())
            for (f in MapParser.floor) {
                for (i in 1..f.height.toInt()) {
                    for (j in 1..f.width.toInt()) {
                        image(resourcesVfs["maps\\floor\\stone_floor1.png"]
                                .readBitmap()) {
                            xy(f.x + j * 64, f.y + i * 64)
                        }
                    }
                }
            }
            println("Hey")
        }
        println("Hello") //${Random.nextInt(1, 5)}
        dx = MapParser.player.x
        dy = MapParser.player.y
        MainModule.hero.sprite.scale = 1.0
        camera = camera {
            xy(dx, dy)
            onKeyDown {
                when (it.key) {
                    Key.DOWN -> y -= speed
                    Key.UP -> y += speed
                    Key.RIGHT -> x += speed
                    Key.LEFT -> x -= speed
                }
            }
        }
        /*addUpdater {
            //val scale = if (it == 0.hrMilliseconds) 0.0 else (it / 16.666666.hrMilliseconds)
            camera.x += dx
            camera.y += dy
        }*/
    }
}
