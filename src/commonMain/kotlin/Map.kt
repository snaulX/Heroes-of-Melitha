import com.soywiz.klock.hr.hrMilliseconds
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.kmem.clamp
import com.soywiz.korev.Key
import com.soywiz.korge.animate.waitStop
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.input.onOut
import com.soywiz.korge.input.onOver
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tween.duration
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.async
import com.soywiz.korio.async.launch
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.lang.closeable
import com.soywiz.korio.serialization.xml.readXml
import com.soywiz.korma.geom.Point
import kotlin.math.pow
import kotlin.random.Random

class Map(val dependency: Dependency) : Scene() {
    val player
        get() = MainModule.hero
    private val speed = player.speed
    private val curSpeed: Point = Point()
    private var dx = MapParser.player.x
    private var dy = MapParser.player.y
    private val sprite: Sprite
        get() = player.sprite

    override suspend fun Container.sceneInit() {
        var blockLeft = false
        var blockRight = false
        var blockUp = false
        var blockDown = false
        val portal = Sprite(SpriteAnimation(resourcesVfs["images\\portals.png"].readBitmap(),
                spriteWidth = 64,
                spriteHeight = 64,
                marginLeft = 0,
                marginTop = 0,
                columns = 3,
                rows = 1,
                offsetBetweenColumns = 0,
                offsetBetweenRows = 0))
        val boxes: MutableList<Image> = mutableListOf()
        val crystals: MutableList<Image> = mutableListOf()
        val mobs: MutableList<Image> = mutableListOf()
        val w: Double = views.actualWidth.toDouble()
        val h: Double = views.actualHeight.toDouble()
        position(dx + w/2, dy + h/2)
        val load = async {
            views.clearColor = Colors.BLACK
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
                boxes.add(image(box) {
                    xy(b.x, b.y)
                    addUpdater {
                        blockDown = false
                        blockLeft = false
                        blockRight = false
                        blockUp = false
                        if (collidesWith(sprite)) {
                            println("" + sprite.x + ", " + sprite.y)
                            println(hitTest(400, 600))
                            if (sprite.y - sprite.height <= y)
                                    //&& hitTest(sprite.x, sprite.y - sprite.height) != null)
                                blockDown = true
                            if (sprite.y >= y - height) blockUp = true
                            if (sprite.x - sprite.width <= x) blockRight = true
                            if (sprite.x >= x - width) blockLeft = true
                        }
                    }
                })
            }
            for (c in MapParser.crystals) {
                crystals.add(image(crystal) {
                    xy(c.x, c.y)
                })
            }
            for (g in MapParser.goblins) {
                mobs.add(image(goblin) {
                    xy(g.x, g.y)
                })
            }

            // Portal
            portal.xy(MapParser.portal.x, MapParser.portal.y)
            portal.spriteDisplayTime = 150.milliseconds
            portal.playAnimationLooped()
            addChild(portal)

            // Player
            sprite.scale = 1.0
            addChild(sprite.xy(MapParser.player.x, MapParser.player.y))
        }
        if (!MainModule.dynamicLoad) load.await()
        sprite.onCollision {
            if (it == portal) {
                if (views.input.keys[Key.E]) {
                    launch { sceneContainer.changeTo<MainMenu>() }
                }
            }
        }
        sprite.
            addHrUpdater {
                val scale = if (it == 0.hrMilliseconds) 0.0 else (it / 16.666666.hrMilliseconds)
                /*blockDown = false
                blockLeft = false
                blockRight = false
                blockUp = false
                for (box in boxes) {
                    if (box.collidesWith(sprite)) {
                        println("" + sprite.x + ", " + sprite.y)
                        println(box.pos)
                        if (sprite.y + sprite.height + speed <= box.y
                                && box.y >= sprite.y + sprite.height) blockDown = true
                        if (box.y + box.height >= sprite.y
                                && box.y + box.height <= sprite.y + speed) blockUp = true
                        if (box.x <= sprite.x + sprite.width) blockRight = true
                        if (box.x + box.width >= sprite.x) blockLeft = true
                    }
                }*/
                when {
                    views.input.keys[Key.RIGHT] && !blockRight -> {
                        dx = this@Map.speed
                        dy = 0.0
                    }
                    views.input.keys[Key.LEFT] && !blockLeft -> {
                        dx = -this@Map.speed
                        dy = 0.0
                    }
                    views.input.keys[Key.UP] && !blockUp -> {
                        dy = -this@Map.speed
                        dx = 0.0
                    }
                    views.input.keys[Key.DOWN] && !blockDown -> {
                        dy = this@Map.speed
                        dx = 0.0
                    }
                    else -> {
                        dy = 0.0
                        dx = 0.0
                    }
                }
                curSpeed.x = dx * scale
                curSpeed.y = dy * scale
                xy(x + curSpeed.x, y + curSpeed.y)
                this@sceneInit.position(w/2 - x, h/2 - y)
            }
    }
}
