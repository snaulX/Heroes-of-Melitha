package heroes

import Hero
import com.soywiz.korge.view.Sprite

class Arwald: Hero {
    override var hp: Double = 100.0
    override var speed: Double = 10.0
    override var armour: Double = 100.0
    override var bow_speed: Double = 20.0
    override var might_speed: Double = 50.0
    override var bow_strength: Double = 30.0
    override var might_strength: Double = 200.0
    override val name: String = "Arwald"
    override val sprite: Sprite
        get() = TODO("Not yet implemented")
    override val description: String = "Strong and brave knight. He hate magic but if magic is need in battles he educate this."
}