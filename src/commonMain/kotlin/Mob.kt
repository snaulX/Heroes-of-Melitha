import com.soywiz.korge.view.Sprite

interface Mob {
    var hp: Double
    var hit: Double
    var speed: Double
    var sprite: Sprite
    val coin: Int
    fun move()
}