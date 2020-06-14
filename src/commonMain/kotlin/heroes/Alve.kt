package heroes

import Hero
import Spell
import com.soywiz.korge.view.Sprite

object Alve: Hero {
    override var hp: Double = 70.0
    override var speed: Double = 1.3
    override var armour: Double = 20.0
    override var bow_speed: Double = 0.8
    override var might_speed: Double = 0.5
    override var bow_strength: Double = 40.0
    override var might_strength: Double = 20.0
    override val name: String = "Alve"
    override lateinit var sprite: Sprite
    override var haveCrystal: Boolean = false
    override var mana: Int = 25
    override val spells: MutableList<Spell> = mutableListOf()
}