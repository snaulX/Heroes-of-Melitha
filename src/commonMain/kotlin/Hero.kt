import com.soywiz.korge.view.Sprite

interface Hero {
    var hp: Double
    var speed: Double
    var armour: Double
    var bow_speed: Double
    var might_speed: Double
    var bow_strength: Double
    var might_strength: Double
    val name: String
    var sprite: Sprite
    val description: String
    var haveCrystal: Boolean
}