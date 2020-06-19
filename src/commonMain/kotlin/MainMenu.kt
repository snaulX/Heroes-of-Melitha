import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.async
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.net.URL

class MainMenu(val dependency: Dependency): Scene() {
    lateinit var play: TextButton
    lateinit var h2p: TextButton
    lateinit var exit: TextButton
    lateinit var h2c: TextButton
    lateinit var report: TextButton
    lateinit var poster: Bitmap

    override suspend fun Container.sceneInit() {
        try {
            async {
                poster = resourcesVfs["images\\poster.png"].readBitmap()
            }.await()
            image(poster) {
                scaleX = views.virtualWidth / 1920.0
                scaleY = views.virtualHeight / 1024.0
            }
        } catch (e: Exception) {
            solidRect(views.virtualWidth.toDouble(), views.virtualHeight.toDouble(), Colors.DARKBLUE)
        }
        text("Heroes of Melitha", 40.0).xy(views.virtualWidth/2.0, 20.0)
        play = textButton(256.0, 64.0, "Play").onClick {
            sceneContainer.changeTo<ChooseHero>()
        }!!
        play.xy(20, 100)
        play.textColor = Colors.BLUEVIOLET
        h2p = textButton(256.0, 64.0, "How To Play").onClick {
            views.gameWindow.browse(URL.invoke(
                    "https://github.com/snaulX/Heroes-of-Melitha/blob/master/README.md#heroes-of-melitha"))
        }!!
        h2p.xy(20, 200)
        h2p.textColor = Colors.BLUEVIOLET
        h2c = textButton(256.0, 64.0, "How To Contribute").onClick {
            views.gameWindow.browse(URL.invoke(
                    "https://github.com/snaulX/Heroes-of-Melitha/blob/master/CONTRIBUTING.md"))
        }!!
        h2c.xy(20, 300)
        h2c.textColor = Colors.BLUEVIOLET
        report = textButton(256.0, 64.0, "Report The Issue").onClick {
            views.gameWindow.browse(URL.invoke(
                    "https://github.com/snaulX/Heroes-of-Melitha/issues/new"))
        }!!
        report.xy(20, 400)
        report.textColor = Colors.BLUEVIOLET
        exit = textButton(256.0, 64.0, "Exit").onClick {
            views.gameWindow.exit()
        }!!
        exit.xy(20, 500)
        exit.textColor = Colors.BLUEVIOLET
    }
}