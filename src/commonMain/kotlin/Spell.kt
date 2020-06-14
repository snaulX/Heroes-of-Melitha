import com.soywiz.korge.view.Image

interface Spell {
    var sprite: Image
    var map: Map
    val cost: Int
    val range: Double
    val castTime: Double //in seconds

    suspend fun attack(x: Double, y: Double)
}