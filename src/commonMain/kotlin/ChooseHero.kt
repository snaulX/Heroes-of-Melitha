import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.textButton
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors

class ChooseHero(val dependency: Dependency) : Scene() {
    suspend override fun Container.sceneInit() {
        solidRect(width, height, Colors.BROWN) {
            position(width/2, height/2)
        }
        textButton(256.0, 64.0, "Back To Main Menu")
                .onClick {
                    sceneContainer.changeTo<MainMenu>()
                }!!
                .xy(20, 100)
        textButton(400.0, 70.0, "Go")
                .onClick {
                    dependency.channel.stop()
                }
    }
}
