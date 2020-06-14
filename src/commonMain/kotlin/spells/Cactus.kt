package spells

import DynamicSpell
import com.soywiz.korge.view.Image
import Map
import com.soywiz.klock.seconds
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.onCollision
import com.soywiz.korio.async.delay

class Cactus: DynamicSpell {
    override val actionTime: Double = 3.0
    override val speed: Double = -1.0
    override lateinit var sprite: Image
    override lateinit var map: Map
    override val cost: Int = 15
    override val range: Double = 1.4
    override val castTime: Double = 0.8

    override suspend fun attack(x: Double, y: Double) {
        sprite.tween(sprite::scale[range], time = castTime.seconds)
        sprite.onCollision {
            val i = map.mobimgs.indexOf(it)
            if (i != -1) map.mobs[i].hp -= 1.0
        }
        delay(actionTime.seconds)
    }
}