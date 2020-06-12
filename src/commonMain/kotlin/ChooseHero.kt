import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onOut
import com.soywiz.korge.input.onOver
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.textButton
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import heroes.*

class ChooseHero(val dependency: Dependency) : Scene() {
    val heroes = listOf(Alve, Arwald)
    private var i = 0
    private val hero
            get() = heroes[i]

    override suspend fun Container.sceneInit() {
        val width = views.actualWidth.toDouble()
        val height = views.actualHeight.toDouble()

        suspend fun update() {
            (getChildAt(3) as Text).text = hero.might_strength.toString()
            hero.sprite = Sprite(resourcesVfs["images\\heroes\\${hero.name}.png"].readBitmap()).apply {
                scale = 8.0
                smoothing = false
                xy(width/2 - this.width/2, height/2 - this.height/2)
            }
            removeChild(getChildAt(4))
            addChildAt(hero.sprite, 4)
        }
        
        textButton(256.0, 64.0, "Back To Main Menu")
                .xy(10, 10)
                .onClick {
                    sceneContainer.changeTo<MainMenu>()
                }
        textButton(400.0, 70.0, "Go")
                .xy(width/2.0 - 100.0, height - 70.0)
                .onClick {
                    MainModule.hero = hero
                    dependency.channel.stop()
                }
        /*val portalsMap = SpriteAnimation(resourcesVfs["images\\portals.png"].readBitmap(),
        spriteWidth = 64,
        spriteHeight = 64,
        marginLeft = 0,
        marginTop = 0,
        columns = 3,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0)
        val portal = sprite(portalsMap).xy(200, 100)
        portal.scale = 3.0
        portal.onOver { portal.alpha = 1.0 }
        portal.onOut { portal.alpha = 0.7 }
        portal.spriteDisplayTime = 150.milliseconds
        portal.playAnimationLooped()*/
        addChild(Sprite(resourcesVfs["images\\mights.png"].readBitmap()).apply {
            smoothing = false
            scale = 3.0
            onOver { this.alpha = 1.0 }
            onOut { this.alpha = 0.7 }
            xy(100, 100)
        })
        addChild(text(hero.might_strength.toString(), 20.0) {
            xy(100.0, 120.0 + lastChild!!.height)
        })
        hero.sprite = Sprite(resourcesVfs["images\\heroes\\${hero.name}.png"].readBitmap()).apply {
            scale = 8.0
            smoothing = false
            xy(width/2 - this.width/2, height/2 - this.height/2)
        }
        addChild(hero.sprite)
        text(hero.name, 48.0) {
            xy(width/2 - this.width/2, 10.0)
        }
        addChild(Sprite(resourcesVfs["images\\menu_arrow.png"].readBitmap()).apply {
            scale = 2.0
            xy(this.width, height/2 + this.height)
            onOver {
                if (i > 0) this.alpha = 1.0
            }
            onOut { this.alpha = 0.6 }
            onClick {
                if (i > 0) {
                    i--
                    update()
                }
            }
            this.rotationDegrees = 180.0
        })
        addChild(Sprite(resourcesVfs["images\\menu_arrow.png"].readBitmap()).apply {
            scale = 2.0
            xy(width - this.width, height/2)
            onOver {
                if (i < heroes.size - 1) this.alpha = 1.0
            }
            onOut { this.alpha = 0.6 }
            onClick {
                if (i < heroes.size - 1) {
                    i++
                    println(this@sceneInit.children.joinToString())
                    update()
                }
            }
        })
    }
}
