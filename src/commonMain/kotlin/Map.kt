import com.soywiz.klock.hr.hrMilliseconds
import com.soywiz.klock.seconds
import com.soywiz.kmem.clamp
import com.soywiz.korev.Key
import com.soywiz.korge.animate.waitStop
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tween.duration
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.async
import com.soywiz.korio.async.launch
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.serialization.xml.readXml
import kotlin.math.pow

class Map(val dependency: Dependency) : Scene() {
    val speed = MainModule.hero.speed
    var dx = MapParser.player.x
    var dy = MapParser.player.y

    override suspend fun Container.sceneInit() {
        position(dx, dy)
        val load = async {
            MapParser.parse(resourcesVfs["maps\\${MainModule.currentMap}.xml"].readXml())
            for (f in MapParser.floor) {
                for (i in 1..f.height.toInt()) {
                    for (j in 1..f.width.toInt()) {
                        image(resourcesVfs["maps\\floor\\stone_floor1.png"] //${Random.nextInt(1, 5)}
                                .readBitmap()) {
                            xy(f.x + j * 64, f.y + i * 64)
                        }
                    }
                }
            }
        }
        if (!MainModule.dynamicLoad) load.await()

            addHrUpdater {
                val scale = if (it == 0.hrMilliseconds) 0.0 else (it / 16.666666.hrMilliseconds)
                when {
                    views.input.keys[Key.RIGHT] -> dx = -this@Map.speed
                    views.input.keys[Key.LEFT] -> dx = this@Map.speed
                    views.input.keys[Key.UP] -> dy = this@Map.speed
                    views.input.keys[Key.DOWN] -> dy = -this@Map.speed
                    else -> {
                        dy = 0.0
                        dx = 0.0
                    }
                }
                position(pos.x + dx * scale, pos.y + dy * scale)
            }
    }
}
