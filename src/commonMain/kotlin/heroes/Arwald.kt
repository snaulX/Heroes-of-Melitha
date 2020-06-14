package heroes

import Hero
import com.soywiz.korge.view.Sprite

object Arwald: Hero {
    override var hp: Double = 60.0
    override var speed: Double = 1.0
    override var armour: Double = 40.0
    override var bow_speed: Double = 0.8
    override var might_speed: Double = 0.7
    override var bow_strength: Double = 30.0
    override var might_strength: Double = 50.0
    override val name: String = "Arwald"
    override lateinit var sprite: Sprite
    override val description: String = "Strong and brave knight." +
            " He hates magic, but at close range he is very dangerous."
    override var haveCrystal: Boolean = false
}