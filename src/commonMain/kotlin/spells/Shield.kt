package spells

import DynamicSpell
import com.soywiz.korge.view.Image
import Map
import com.soywiz.klock.seconds
import com.soywiz.korio.async.delay

class Shield: DynamicSpell {
    override val actionTime: Double = 2.0
    override val chance: Double = -1.0
    override lateinit var sprite: Image
    override lateinit var map: Map
    override val cost: Int = 10
    override val range: Double = 1.0
    override val castTime: Double = 2.0

    override suspend fun attack(x: Double, y: Double) {
        delay(castTime.seconds)
        val a = map.player.armour
        map.player.armour = 60.0
        delay(actionTime.seconds)
        map.player.armour = a
    }
}