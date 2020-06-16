package spells

import DynamicSpell
import com.soywiz.klock.seconds
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.Image
import com.soywiz.korge.view.onCollision
import com.soywiz.korio.async.delay
import Map
import kotlin.random.Random

class Poison: DynamicSpell {
    override val actionTime: Double = 10.0
    override val chance: Double = 0.8
    override lateinit var sprite: Image
    override lateinit var map: Map
    override val cost: Int = 15
    override val range: Double = 1.5
    override val castTime: Double = 0.8

    override suspend fun attack(x: Double, y: Double) {
        sprite.tween(sprite::scale[range], time = castTime.seconds)
        sprite.onCollision {
            val i = map.mobimgs.indexOf(it)
            if (i != -1 && Random.nextDouble() <= chance) map.mobs[i].hp -= 1.1
        }
        delay(actionTime.seconds)
    }
}