import com.soywiz.korge.scene.*
import com.soywiz.korinject.AsyncInjector
import kotlin.reflect.KClass

object MainModule: Module() {
    override val mainScene: KClass<out Scene> = MainMenu::class
    override val title: String = "Heroes of Melitha"

    override suspend fun AsyncInjector.configure() {
        mapInstance(Dependency())
        mapPrototype { MainMenu(get()) }
    }
}