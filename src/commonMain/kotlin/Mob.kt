import com.soywiz.korge.view.Image

interface Mob {
    var speed: Double
    var map: Map
    var hp: Double
    var hit: Double
    var image: Image
    val coin: Int
    fun move(scale: Double)
}