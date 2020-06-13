import com.soywiz.klock.hr.hrMilliseconds
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.kmem.clamp
import com.soywiz.korev.Key
import com.soywiz.korge.animate.waitStop
import com.soywiz.korge.input.onOut
import com.soywiz.korge.input.onOver
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
import kotlin.random.Random

class Map(val dependency: Dependency) : Scene() {
    val player
        get() = MainModule.hero
    private val speed = player.speed
    var dx = MapParser.player.x
    var dy = MapParser.player.y

    override suspend fun Container.sceneInit() {
        position(dx, dy)
        val load = async {
            MapParser.parse(resourcesVfs["maps\\${MainModule.currentMap}.xml"].readXml())
            val stone = resourcesVfs["maps\\floor\\stone_floor${Random.nextInt(1, 5)}.png"]
                    .readBitmap()
            val box = resourcesVfs["maps\\boxes\\wood_box.png"].readBitmap()
            val crystal = resourcesVfs["maps\\score\\crystal.png"].readBitmap()
            val goblin = resourcesVfs["images\\mobs\\goblin.png"].readBitmap()
            for (f in MapParser.floor) {
                for (i in 1..f.height.toInt()) {
                    for (j in 1..f.width.toInt()) {
                        image(stone) {
                            xy(f.x + j * 64, f.y + i * 64)
                        }
                    }
                }
            }
            for (b in MapParser.boxes) {
                image(box) {
                    xy(b.x, b.y)
                }
            }
            for (c in MapParser.crystals) {
                image(crystal) {
                    xy(c.x, c.y)
                }
            }
            for (g in MapParser.goblins) {
                image(goblin) {
                    xy(g.x, g.y)
                }
            }

            // Portal
            val portalsMap = SpriteAnimation(resourcesVfs["images\\portals.png"].readBitmap(),
                    spriteWidth = 64,
                    spriteHeight = 64,
                    marginLeft = 0,
                    marginTop = 0,
                    columns = 3,
                    rows = 1,
                    offsetBetweenColumns = 0,
                    offsetBetweenRows = 0)
            val portal = sprite(portalsMap).xy(MapParser.portal.x, MapParser.portal.y)
            portal.spriteDisplayTime = 150.milliseconds
            portal.playAnimationLooped()

            // Player
            player.sprite.scale = 1.0
            addChild(player.sprite.xy(MapParser.player.x, MapParser.player.y))
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
