import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onOut
import com.soywiz.korge.input.onOver
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.textButton
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import heroes.Arwald

class ChooseHero(val dependency: Dependency) : Scene() {
    val heroes = listOf(Arwald)
    private var i = 0
    private val hero = heroes[i]

    override suspend fun Container.sceneInit() {
        val width = views.actualWidth.toDouble()
        val height = views.actualHeight.toDouble()
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
        addChild(Sprite(resourcesVfs["open_magic_book.png"].readBitmap()).apply { //images\might.png
            scale = 0.2
            onOver { this.alpha = 1.0 }
            onOut { this.alpha = 0.7 }
            xy(100, 100)
        })
        addChild(text(hero.might_strength.toString(), 20.0) {
            xy(100.0, 120.0 + lastChild!!.height)
        })
        hero.sprite = Sprite(resourcesVfs["open_magic_book.png"].readBitmap()).apply {
            scale = 0.5
            xy(width/2 - this.width/2, height/2 - this.height/2)
            text(hero.name, 48.0) {
                xy(width/2 - this.width - this@apply.width, height/2)
            }
            /*text(hero.description, 24.0) {
                xy(0.0, height/2 + 100.0)
            }*/
        }//images\heroes\${hero.name}.png
        addChild(hero.sprite)
    }

    override suspend fun sceneDestroy() {
        super.sceneDestroy()
        Arwald.sprite.scale = 1.0
    }
}
