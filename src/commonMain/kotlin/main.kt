import com.soywiz.korau.sound.NativeSoundChannel
import com.soywiz.korge.Korge

suspend fun main() = Korge(Korge.Config(MainModule))

class Dependency(var channel: NativeSoundChannel)