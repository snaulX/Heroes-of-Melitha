package spells

import Spell
import Map
import com.soywiz.klock.seconds
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*

class Tornado: Spell {
    override lateinit var sprite: Image
    override lateinit var map: Map
    override val cost: Int = 10
    override val range: Double = 2.5
    override val castTime: Double = 1.5

    override suspend fun attack(x: Double, y: Double) {
        sprite.tween(sprite::scale[range], time = castTime.seconds)
        sprite.onCollision {
            val i = map.mobimgs.indexOf(it)
            if (i != -1) map.mobs[i].speed = -1.0
        }
    }
}