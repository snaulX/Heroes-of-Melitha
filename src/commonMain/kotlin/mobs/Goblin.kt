package mobs

import Mob
import com.soywiz.korge.view.Image
import Map
import com.soywiz.korge.view.collidesWith
import com.soywiz.korge.view.xy
import kotlin.random.Random

class Goblin: Mob {
    override lateinit var map: Map
    override var hp: Double = 20.0
    override var hit: Double = 0.6
    override lateinit var image: Image
    override val coin: Int = 1
    private var dx: Double = 0.0
    private var dy: Double = 0.0

    override fun move(scale: Double) {
        try {
            if (image.collidesWith(map.floor) && !image.collidesWith(map.boxes)) {
                when (Random.nextInt(4)) {
                    0 -> {
                        dx = 1.0
                        dy = 0.0
                    }
                    1 -> {
                        dx = -1.0
                        dy = 0.0
                    }
                    2 -> {
                        dy = -1.0
                        dx = 0.0
                    }
                    3 -> {
                        dy = 1.0
                        dx = 0.0
                    }
                    else -> {
                        dy = 0.0
                        dx = 0.0
                    }
                }
            } else {
                dx = -dx
                dy = -dy
            }
            image.xy(image.x + dx * scale, image.y + dy * scale)
        } catch (e: RuntimeException) {
            //nothing to do
        }
    }
}