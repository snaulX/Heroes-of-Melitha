package mobs

import Mob
import com.soywiz.korge.view.Sprite

class Goblin: Mob {
    override var hp: Double = 20.0
    override var hit: Double = 10.0
    override lateinit var sprite: Sprite
    override val coin: Int = 1
    override var speed: Double = 1.0

    override fun move() {
        TODO("Not yet implemented")
    }
}