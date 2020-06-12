import com.soywiz.klock.milliseconds
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.textButton
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class ChooseHero(val dependency: Dependency) : Scene() {
    suspend override fun Container.sceneInit() {
        textButton(256.0, 64.0, "Back To Main Menu")
                .xy(20, 100)
                .onClick {
                    sceneContainer.changeTo<MainMenu>()
                }
        textButton(400.0, 70.0, "Go")
                .xy(width/2, views.actualHeight.toDouble() - 70.0)
                .onClick {
                    dependency.channel.stop()
                }
        val portalsMap = SpriteAnimation(resourcesVfs["images\\portals.png"].readBitmap(),
        spriteWidth = 64,
        spriteHeight = 64,
        marginLeft = 0,
        marginTop = 0,
        columns = 3,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0)
        val portal = sprite(portalsMap)
        portal.spriteDisplayTime = 150.milliseconds
        portal.playAnimationLooped()
    }
}
