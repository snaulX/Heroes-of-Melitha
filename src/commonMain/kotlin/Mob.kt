import com.soywiz.korge.view.Sprite

interface Mob {
    var hp: Double
    var hit: Double
    val sprite: Sprite
    fun move()
}