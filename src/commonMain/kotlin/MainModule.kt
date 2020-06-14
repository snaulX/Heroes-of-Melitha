import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korau.sound.readMusic
import com.soywiz.korge.scene.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.file.std.resourcesVfs
import kotlin.reflect.KClass

object MainModule: Module() {
    override val mainScene: KClass<out Scene> = MainMenu::class
    override val title: String = "Heroes of Melitha"
    var currentMap: String = "StartMap"
    var money: Int = 0
    lateinit var hero: Hero
    override val bgcolor: RGBA
        get() = Colors.CORAL
    var hard = false

    override suspend fun AsyncInjector.configure() {
        mapInstance(Dependency(resourcesVfs["music\\menu_theme.wav"]
                .readMusic()
                .play(PlaybackTimes.INFINITE)))
        mapPrototype { MainMenu(get()) }
        mapPrototype { ChooseHero(get()) }
        mapPrototype { Map(get()) }
    }
}