package spells

import Spell
import com.soywiz.korge.view.Image
import Map
import com.soywiz.klock.seconds
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.onCollision

class Bomb: Spell {
    override lateinit var sprite: Image
    override lateinit var map: Map
    override val cost: Int = 15
    override val range: Double = 1.0
    override val castTime: Double = 0.5

    override suspend fun attack(x: Double, y: Double) {
        sprite.tween(sprite::scale[range], time = castTime.seconds)
        sprite.onCollision {
            val i = map.mobimgs.indexOf(it)
            if (i != -1) map.mobs[i].hp -= 30000
        }
    }
}