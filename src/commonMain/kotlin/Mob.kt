import com.soywiz.korge.view.Image

interface Mob {
    var speed: Double
    var map: Map
    var hp: Double
    var hit: Double
    var image: Image
    fun move(scale: Double)
}