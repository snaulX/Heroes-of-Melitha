package mobs

import Mob
import Map
import com.soywiz.korge.view.Image
import com.soywiz.korge.view.collidesWith
import com.soywiz.korge.view.xy

class Demon: Mob {
    override var speed: Double = 1.5
    override lateinit var map: Map
    override var hp: Double = 900.0
    override var hit: Double = 1.0
    override lateinit var image: Image
    var dx: Double = 0.0
    var dy: Double = 0.0

    override fun move(scale: Double) {
        if (!MainModule.hard) {
            if (!image.collidesWith(map.floor) || image.collidesWith(map.boxes)) {
                dy = if (map.sprite.y > image.y) speed
                else if (map.sprite.y == image.y) 0.0
                else -speed
                dx = if (map.sprite.x > image.x) speed
                else if (map.sprite.x == image.x) 0.0
                else -speed
            }
        } else {
            dy = if (map.sprite.y > image.y) speed
            else if (map.sprite.y == image.y) 0.0
            else -speed
            dx = if (map.sprite.x > image.x) speed
            else if (map.sprite.x == image.x) 0.0
            else -speed
        }
        image.xy(image.x + dx * scale, image.y + dy * scale)
    }
}