package heroes

import Hero
import com.soywiz.korge.view.Sprite

object Alve: Hero {
    override var hp: Double = 70.0
    override var speed: Double = 1.1
    override var armour: Double = 20.0
    override var bow_speed: Double = 2.0
    override var might_speed: Double = 0.6
    override var bow_strength: Double = 70.0
    override var might_strength: Double = 20.0
    override val name: String = "Alve"
    override lateinit var sprite: Sprite
    override val description: String = "Brave and great elf. He hate mights and love own bow and forest."
}