# What I need for add something to game?
**Must know** - Kotlin.
_Should have_ IDE (InteliJ IDEA) and _know_ [KorGE](https://korge.org/)
# How to add something to game?
1. Create fork of this repository
2. Add what do you want.
3. Create pull request.
# What I can add to game?
1. Own spell(s)
2. Own map(s)
3. Own hero(es)
4. Maybe anything else  
**!!ATTENTION FOR CREATORS!!** If you add not your music or image, please add license for this music or image to LICENSE file. And don't add music or image without free license!
# How to add own
After open project in IDE go to ..
## Spell
.. folder src/commonMain/kotlin/spells/ and create Kotlin file _your spell name_.kt by template:
```kt
package spells

import Spell
import Map
import com.soywiz.klock.seconds
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.Image

class <your-name-of-spell>: Spell {
    override lateinit var sprite: Image
    override lateinit var map: Map
    override val cost: Int = <spell cost>
    override val range: Double = <range of cost>
    override val castTime: Double = <cast time of spell>

    override suspend fun attack(x: Double, y: Double) {
        sprite.tween(sprite::scale[range], time = castTime.seconds) //or your animation while cast -> https://korlibs.soywiz.com/korge/animation/
        sprite.onCollision {
            val i = map.mobimgs.indexOf(it) // if i != -1 that menas that map.mobs[i] in this spell
            if (i != -1) map.mobs[i].hp -= 1.0 //for example mob that touches our spell loose one health point
        }
    }
}
```
For add this spell you need open src/commonMain/kotlin/Map.kt and add to 220 line this string
```kt
player.spells.add(<your-name-of-spell>().apply {
                map = this@Map
                sprite = Sprite(resourcesVfs["images\\spells\\<your-image-of-spell>.png"].readBitmap())
            })
```  
It's all! Build game and test it! If you have issues write there are -> https://github.com/snaulX/Heroes-of-Melitha/issues
## Map
.. folder src/commonMain/resources/maps and create file _your map name_.xml with current content
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Map name="name of your map" width="width of map" height="height of map">
  <!--Content-->
</Map>
```
and ... how to run own map? Go to src/commonMain/kotlin/MainModule.kt and [line 13](https://github.com/snaulX/Heroes-of-Melitha/blob/7a7a2e93f063985d50d172bbedb0b0bf523584ef/src/commonMain/kotlin/MainModule.kt#L13) and you see list `maps`. Add after "StartMap" string with name of your XML file with map that we created upper. Run game, pass first map and.. Something wrong: game was crashing with exception. For fix this we need to open our file with map again and change content. How I can did this?  
So that map don't crashing we need to add player and portal. Deleting comment and write this nodes:
```xml
<Player x="x coordinate of player" y="y coordinate of player" />
<Portal x="x coordinate of portal" y="y coordinate of portal" />
```
Save file, test map and.. we haven't exception but player cannot move and map cannot be passed. We need to add floor to player can move and add crystal to map can be pass. Node `<Crystal x="x coordinate of crystal" y="y coordinate of crystal" />` adds crystal to map. But unlike the portal and the player, we can put crystals how many we want. Node `<Floor x="x coordinate of the left-top point of floor" y="y coordinate of the left-top point of floor" width="width of floor" height="height of floor" />` adds floor to map. We can adds floors how many we want.  
Okay we create playable map but something is missing, namely obstacles (boxes) and enemies (goblins). Node `<Goblin x="x coordinate of goblin" y="y coordinate of goblin" />` adds goblin to map. We can adds goblins how many we want. Node `<Box x="x coordinate of box" y="y coordinate of box" />` adds box to map. We can adds boxes how many we want.
It's all! Build game and test it! If you have issues write there are -> https://github.com/snaulX/Heroes-of-Melitha/issues
## Hero
.. folder src/commonMain/kotlin/heroes/ and create Kotlin file with object and name of your hero. Fill this file how this template:
```kt
package heroes

import Hero
import Spell
import com.soywiz.korge.view.Sprite

object <short name of hero>: Hero {
    override var hp: Double = <health points of your hero>
    override var speed: Double = <speed of your hero>
    override var armour: Double = <armour of your hero>
    override var bow_speed: Double = <speed of arrow of your hero>
    override var might_speed: Double = <speed of might of your hero>
    override var bow_strength: Double = <strength of arrow of your hero>
    override var might_strength: Double = <strength of might of your hero>
    override val name: String = "<full name of your hero>"
    override lateinit var sprite: Sprite
    override var haveCrystal: Boolean = false
    override var mana: Int = <number of mana on start of the game of your hero>
    override val spells: MutableList<Spell> = mutableListOf()
}
```
For example check this file: https://github.com/snaulX/Heroes-of-Melitha/blob/master/src/commonMain/kotlin/heroes/Arwald.kt
After you have created a hero you must add it to the menu with the choice of heroes. Open src/commonMain/kotlin/ChooseHero.kt and add to variable `heroes` (to list) your hero. This variable avialable on [14 line](https://github.com/snaulX/Heroes-of-Melitha/blob/198d91bd6b84d2283ac4fc7a880e1d3b7b6d5423/src/commonMain/kotlin/ChooseHero.kt#L14). But even now, something is missing, namely the image of the hero. Draw image of hero with resolution 64x64 and add this to folder src/commonMain/resources/images/heroes/_name of your hero_.png  
It's all! Build game and test it! If you have issues write there are -> https://github.com/snaulX/Heroes-of-Melitha/issues
