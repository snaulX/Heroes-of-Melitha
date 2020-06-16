# What I need for add something to game?
You need to know Kotlin programming language and have IDE for comfort.
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
## Map
.. folder src/commonMain/resources/maps and create file _your map name_.xml with current content
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Map name="name of your map" width="width of map" height="height of map">
  <!--Content-->
</Map>
```
and ... how to run own map?
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
After you have created a hero you must add it to the menu with the choice of heroes. Open src/commonMain/kotlin/ChooseHero.kt and add to variable heroes (to list) your hero. This variable avialable on [14 line](https://github.com/snaulX/Heroes-of-Melitha/blob/198d91bd6b84d2283ac4fc7a880e1d3b7b6d5423/src/commonMain/kotlin/ChooseHero.kt#L14). But even now, something is missing, namely the image of the hero. Draw image of hero with resolution 64x64 and add this to folder src/commonMain/resources/images/heroes/_name of your hero_.png  
It's all! Build game and test it! If you have issues write there are -> https://github.com/snaulX/Heroes-of-Melitha/issues
