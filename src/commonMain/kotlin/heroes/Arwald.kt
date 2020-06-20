package heroes

import Hero
import Spell
import com.soywiz.korge.view.Sprite

class Arwald: Hero {
    override var hp: Double = 70.0
    override var speed: Double = 1.1
    override var armour: Double = 40.0
    override var bow_speed: Double = 0.5
    override var might_speed: Double = 0.7
    override var bow_strength: Double = 10.0
    override var might_strength: Double = 50.0
    override val name: String = "Arwald"
    override lateinit var sprite: Sprite
    override var haveCrystal: Boolean = false
    override var mana: Int = 20
    override val spells: MutableList<Spell> = mutableListOf()
}