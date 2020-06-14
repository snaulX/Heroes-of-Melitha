import com.soywiz.korau.sound.NativeSoundChannel
import com.soywiz.korge.Korge

suspend fun main() = Korge(Korge.Config(MainModule))

class Dependency(val channel: NativeSoundChannel) {
    init {
        channel.volume = 0.0
    }
}