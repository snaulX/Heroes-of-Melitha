import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors

class MainMenu(val dependency: Dependency): Scene() {
    lateinit var play: TextButton
    lateinit var lore: TextButton

    override suspend fun Container.sceneInit() {
        text("Heroes of Melitha", 48.0).xy(width/2, 20.0)
        play = textButton(128.0, 64.0, "Play").onClick {
            sceneContainer.changeTo<ChooseHero>()
        }!!
        play.xy(20, 100)
        play.textColor = Colors.BLUEVIOLET
        lore = textButton(128.0, 64.0, "Lore").onClick {
            println("Lore")
        }!!
        lore.xy(20, 200)
        lore.textColor = Colors.BLUEVIOLET
    }
}