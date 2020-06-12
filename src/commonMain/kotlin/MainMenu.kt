import com.soywiz.klock.seconds
import com.soywiz.korev.Key
import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.interpolation.Easing

class MainMenu(val dependency: Dependency): Scene() {
    lateinit var play: TextButton
    lateinit var lore: TextButton
    lateinit var pic: Image

    override suspend fun Container.sceneInit() {
        text("Heroes of Melitha", 48.0).xy(width/2, 20.0)
        play = textButton(128.0, 64.0, "Play").onClick {
            println("Let`s Play")
        }!!
        play.xy(20, 100)
        lore = textButton(128.0, 64.0, "Lore").onClick {
            println("Lore")
        }!!
        lore.xy(20, 200)
        pic = image(resourcesVfs["open_magic_book.png"].readBitmap())
                .xy(width/2, height/2)
                .alpha(10.0)
                .onKeyDown {
                    when (it.key) {
                        Key.DOWN -> pic.tween(pic::rotation[90.degrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
                        Key.UP -> pic.tween(pic::rotation[(-90).degrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
                    }
                }
    }
}