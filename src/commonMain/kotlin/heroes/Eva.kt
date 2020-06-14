package heroes

import Hero
import Spell
import com.soywiz.korge.view.Sprite

object Eva: Hero {
    override var hp: Double = 90.0
    override var speed: Double = 1.5
    override var armour: Double = 0.0
    override var bow_speed: Double = 0.75
    override var might_speed: Double = 0.45
    override var bow_strength: Double = 35.0
    override var might_strength: Double = 25.0
    override val name: String = "Eva"
    override lateinit var sprite: Sprite
    override var haveCrystal: Boolean = false
    override var mana: Int = 50
    override val spells: MutableList<Spell> = mutableListOf()
}