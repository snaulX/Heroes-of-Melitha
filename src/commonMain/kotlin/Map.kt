import com.soywiz.klock.hr.hrMilliseconds
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korau.sound.readMusic
import com.soywiz.korev.Key
import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.launch
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.serialization.xml.readXml
import mobs.*
import spells.*
import kotlin.random.Random

class Map(val dependency: Dependency) : Scene() {
    val player
        get() = MainModule.hero
    private val speed = player.speed
    private var dx = MapParser.player.x
    private var dy = MapParser.player.y
    val sprite: Sprite
        get() = player.sprite
    val boxes: MutableList<Image> = mutableListOf()
    val crystals: MutableList<Image> = mutableListOf()
    val mobs: MutableList<Mob> = mutableListOf()
    val mobimgs: MutableList<Image> = mutableListOf()
    val manas: MutableList<Image> = mutableListOf()
    val floor: MutableList<Image> = mutableListOf()
    val w: Double
        get() = views.actualWidth.toDouble()
    val h: Double
        get() = views.actualHeight.toDouble()
    var spellIndex = -1

    override suspend fun Container.sceneInit() {
        dependency.channel.stop()
        val portal = Sprite(SpriteAnimation(resourcesVfs["images\\portals.png"].readBitmap(),
                spriteWidth = 64,
                spriteHeight = 64,
                marginLeft = 0,
                marginTop = 0,
                columns = 3,
                rows = 1,
                offsetBetweenColumns = 0,
                offsetBetweenRows = 0))
        position(dx + w/2, dy + h/2)
        launch {
            views.clearColor = Colors.BLACK
            MapParser.parse(resourcesVfs["maps\\${MainModule.maps[MainModule.mapIndex]}.xml"].readXml())
            dependency.channel = resourcesVfs["music\\${MapParser.music}.wav"]
                    .readMusic()
                    .play(PlaybackTimes.INFINITE)
            val stone = resourcesVfs["maps\\floor\\stone_floor${Random.nextInt(1, 5)}.png"]
                    .readBitmap()
            val box = resourcesVfs["maps\\boxes\\wood_box.png"].readBitmap()
            val crystal = resourcesVfs["maps\\score\\crystal.png"].readBitmap()
            val goblin = resourcesVfs["images\\mobs\\goblin.png"].readBitmap()
            val might = resourcesVfs["images\\weapon\\might.png"].readBitmap()
            val mana = resourcesVfs["images\\spells\\mana.png"].readBitmap()

            // Other
            for (f in MapParser.floor) {
                for (i in 1..f.height.toInt()) {
                    for (j in 1..f.width.toInt()) {
                        floor.add(image(stone) {
                            xy(f.x + j * 64, f.y + i * 64)
                            onClick {
                                if (spellIndex > -1) {
                                    launch {
                                        val s = player.spells[spellIndex]
                                        if (player.mana >= s.cost) {
                                            player.mana -= s.cost
                                            s.sprite = Image(s.sprite.bitmap).apply {
                                                xy(this@image.x, this@image.y)
                                            }
                                            this@sceneInit.addChild(s.sprite)
                                            s.attack(this.x, this.y)
                                            this@sceneInit.removeChild(s.sprite)
                                            spellIndex = -1
                                        }
                                    }
                                } else {
                                    val arrow = Image(resourcesVfs["images\\weapon\\arrow.png"].readBitmap())
                                            .apply {
                                                xy(sprite.x, sprite.y)
                                                onCollision {
                                                    val i = mobimgs.indexOf(it)
                                                    if (i != -1) mobs[i].hp -= player.bow_strength
                                                }
                                            }
                                    this@sceneInit.addChild(arrow)
                                    arrow.tween(arrow::x[this@image.x], arrow::y[this@image.y],
                                    time = player.bow_speed.seconds)
                                    this@sceneInit.removeChild(arrow)
                                }
                            }
                        })
                    }
                }
            }
            for (b in MapParser.boxes) {
                boxes.add(image(box) {
                    xy(b.x, b.y)
                })
            }
            for (c in MapParser.crystals) {
                crystals.add(image(crystal) {
                    xy(c.x, c.y)
                })
            }
            for (m in MapParser.manas) {
                manas.add(image(mana) {
                    xy(m.x, m.y)
                })
            }
            for (g in MapParser.goblins) {
                mobs.add(Goblin().apply {
                    if (MainModule.hard) {
                        hp = 1000.0
                        hit = 1.0
                    }
                    image = image(goblin) {
                        xy(g.x, g.y)
                        onClick {
                            val arrow = Image(resourcesVfs["images\\weapon\\arrow.png"].readBitmap())
                                    .apply {
                                        xy(sprite.x, sprite.y)
                                        onCollision {
                                            val i = mobimgs.indexOf(it)
                                            if (i != -1) mobs[i].hp -= player.bow_strength
                                        }
                                    }
                            this@sceneInit.addChild(arrow)
                            arrow.tween(arrow::x[this@image.x], arrow::y[this@image.y],
                                    time = player.bow_speed.seconds)
                            this@sceneInit.removeChild(arrow)
                        }
                    }
                    mobimgs.add(image)
                    map = this@Map
                    when (Random.nextInt(4)) {
                        0 -> {
                            dx = 1.0
                            dy = 0.0
                        }
                        1 -> {
                            dx = -1.0
                            dy = 0.0
                        }
                        2 -> {
                            dy = -1.0
                            dx = 0.0
                        }
                        else -> {
                            dy = 1.0
                            dx = 0.0
                        }
                    }
                    image.addHrUpdater {
                        val scale = if (it == 0.hrMilliseconds) 0.0 else (it / 16.666666.hrMilliseconds)
                        if (collidesWith(sprite)) {
                            if (views.keys[Key.SPACE]) launch {
                                val m = image(might)
                                m.tween(m::x[32.0, 0.0],
                                        m::y[32.0, 0.0],
                                        time = player.might_speed.seconds)
                                removeChild(m)
                                hp -= player.might_strength * player.might_speed * scale
                            }
                            if (player.armour > 0.0)
                                player.armour -= hit
                            else
                                player.hp -= hit
                        } else {
                            move(scale)
                        }
                        if (hp <= 0.0) {
                            this@sceneInit.removeChild(this)
                        }
                    }
                })
            }

            // Portal
            portal.xy(MapParser.portal.x, MapParser.portal.y)
            portal.addUpdater {
                alpha = if (player.haveCrystal) 1.0 else 0.7
            }
            portal.spriteDisplayTime = 150.milliseconds
            portal.playAnimationLooped()
            addChild(portal)

            // Player
            player.spells.add(Tornado().apply {
                map = this@Map
                sprite = Sprite(resourcesVfs["images\\spells\\tornado.png"].readBitmap())
            })
            player.spells.add(Cactus().apply {
                map = this@Map
                sprite = Sprite(resourcesVfs["images\\spells\\cactus.png"].readBitmap())
            })
            player.spells.add(Cactus().apply {
                map = this@Map
                sprite = Sprite(resourcesVfs["images\\spells\\boom.png"].readBitmap())
            })
            player.spells.add(Shield().apply {
                map = this@Map
                sprite = Sprite(resourcesVfs["images\\spells\\shield.png"].readBitmap())
            })
            sprite.scale = 1.0
            addChild(sprite.xy(MapParser.player.x, MapParser.player.y))
        }

        onKeyDown {
            // println(spellIndex)
            spellIndex = when (it.key) {
                Key.N0 -> 0
                Key.N1 -> 1
                Key.N2 -> 2
                Key.N3 -> 3
                Key.N4 -> 4
                Key.N5 -> 5
                Key.N6 -> 6
                Key.N7 -> 7
                Key.N8 -> 8
                Key.N9 -> 9
                else -> -1
            }
        }
        sprite.onCollision { target ->
            if (target == portal) {
                if (views.input.keys[Key.E]) {
                    launch {
                        if (player.haveCrystal) {
                            if (MainModule.mapIndex < MainModule.maps.lastIndex) {
                                MainModule.mapIndex++
                                sceneContainer.changeTo<Map>()
                            } else {
                                sceneContainer.changeTo<MainMenu>()
                            }
                        } else {
                            //views.gameWindow.alert("You haven`t crystal for closing portals!!!")
                        }
                    }
                }
            } else if (crystals.contains(target)) {
                if (views.input.keys[Key.E]) {
                    player.haveCrystal = true
                    removeChild(target)
                }
            } else if (manas.contains(target)) {
                if (views.input.keys[Key.E]) {
                    player.mana += 10
                    removeChild(target)
                }
            }
        }
        sprite.
            addHrUpdater {
                if (player.hp <= 0.0) views.gameWindow.exit()
                val scale = if (it == 0.hrMilliseconds) 0.0 else (it / 16.666666.hrMilliseconds)
                if (collidesWith(floor) && !collidesWith(boxes)) {
                    when {
                        views.input.keys[Key.D] -> {
                            dx = this@Map.speed
                            dy = 0.0
                        }
                        views.input.keys[Key.A] -> {
                            dx = -this@Map.speed
                            dy = 0.0
                        }
                        views.input.keys[Key.W] -> {
                            dy = -this@Map.speed
                            dx = 0.0
                        }
                        views.input.keys[Key.S] -> {
                            dy = this@Map.speed
                            dx = 0.0
                        }
                        else -> {
                            dy = 0.0
                            dx = 0.0
                        }
                    }
                } else {
                    dx = -dx
                    dy = -dy
                }
                xy(x + dx * scale, y + dy * scale)
                this@sceneInit.position(w/2 - x, h/2 - y)
            }
    }

    override suspend fun sceneDestroy() {
        super.sceneDestroy()
        MapParser.clear()
        player.haveCrystal = false
        player.spells.clear()
        dependency.channel.stop()
    }
}
