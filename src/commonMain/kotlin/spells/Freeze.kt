package spells

import DynamicSpell
import com.soywiz.klock.seconds
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.Image
import com.soywiz.korge.view.onCollision
import com.soywiz.korio.async.delay
import Map
import Mob
import com.soywiz.korma.interpolation.Easing

class Freeze: DynamicSpell {
    override val actionTime: Double = 4.0
    override val chance: Double = -1.0
    override lateinit var sprite: Image
    override lateinit var map: Map
    override val cost: Int = 20
    override val range: Double = 1.2
    override val castTime: Double = 0.5
    //-val frozenMobs: MutableList<Mob> = mutableListOf()
    //val speedMobs: MutableList<Double> = mutableListOf()

    override suspend fun attack(x: Double, y: Double) {
        sprite.tween(sprite::scale[range], time = castTime.seconds, easing = Easing.EASE_OUT)
        sprite.onCollision {
            val i = map.mobimgs.indexOf(it)
            if (i != -1) {
                //val have = !frozenMobs.contains(map.mobs[i])
                //if (have) {
                    //speedMobs.add(map.mobs[i].speed)
                //}
                map.mobs[i].speed = 0.0
                //if (have) {
                    //frozenMobs.add(map.mobs[i])
                //}
            }
        }
        delay(actionTime.seconds)
        //for (i in 0..frozenMobs.size) {
            //frozenMobs[i].speed = speedMobs[i]
        //}
    }
}