package mobs

import Mob
import com.soywiz.korge.view.Image
import Map
import com.soywiz.korge.view.collidesWith
import com.soywiz.korge.view.xy

class Goblin: Mob {
    override lateinit var map: Map
    override var hp: Double = 600.0
    override var hit: Double = 0.7
    override lateinit var image: Image
    override val coin: Int = 1
    var dx: Double = 0.0
    var dy: Double = 0.0

    override fun move(scale: Double) {
        if (!MainModule.hard) {
            if (!image.collidesWith(map.floor) || image.collidesWith(map.boxes)) {
                dy = if (map.sprite.y > image.y) 1.0
                else if (map.sprite.y == image.y) 0.0
                else -1.0
                dx = if (map.sprite.x > image.x) 1.0
                else if (map.sprite.x == image.x) 0.0
                else -1.0
            }
        } else {
            dy = if (map.sprite.y > image.y) 1.0
            else if (map.sprite.y == image.y) 0.0
            else -1.0
            dx = if (map.sprite.x > image.x) 1.0
            else if (map.sprite.x == image.x) 0.0
            else -1.0
        }
        image.xy(image.x + dx * scale, image.y + dy * scale)
    }
}