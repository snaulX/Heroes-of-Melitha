import com.soywiz.korio.serialization.xml.Xml
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.Rectangle

object MapParser {
    val boxes: MutableSet<Point> = mutableSetOf()
    var player: Point = Point(0)
    var portal: Point = Point(0)
    val floor: MutableSet<Rectangle> = mutableSetOf()
    val crystals: MutableSet<Point> = mutableSetOf()
    val manas: MutableSet<Point> = mutableSetOf()
    var width = 0
    var height = 0
    var name = ""
    val goblins: MutableList<Point> = mutableListOf()
    val demons: MutableList<Point> = mutableListOf()
    var music = ""

    fun parse(xml: Xml) {
        if (xml.name == "Map") {
            width = xml.int("width") * 64
            height = xml.int("height") * 64
            name = xml.attribute("name") ?: "Untitled Map"
            music = xml.attribute("music") ?: "menu_theme"
            val pl = xml.child("Player") ?: throw RuntimeException("Map haven`t player")
            player = Point(pl.int("x") * 64, pl.int("y") * 64)
            val pt = xml.child("Portal") ?: throw RuntimeException("Map haven`t portal")
            portal = Point(pt.int("x") * 64, pt.int("y") * 64)
            for (box in xml.children("Box")) {
                boxes.add(Point(box.int("x") * 64, box.int("y") * 64))
            }
            for (crystal in xml.children("Crystal")) {
                crystals.add(Point(crystal.int("x") * 64, crystal.int("y") * 64))
            }
            for (rect in xml.children("Floor")) {
                floor.add(Rectangle(rect.int("x") * 64, rect.int("y") * 64,
                        rect.int("width"), rect.int("height")))
            }
            for (goblin in xml.children("Goblin")) {
                goblins.add(Point(goblin.int("x") * 64, goblin.int("y") * 64))
            }
            for (demon in xml.children("Demon")) {
                demons.add(Point(demon.int("x") * 64, demon.int("y") * 64))
            }
            for (mana in xml.children("Mana")) {
                manas.add(Point(mana.int("x") * 64, mana.int("y") * 64))
            }
        } else {
            throw RuntimeException("${xml.name} is not valid name of root node in map")
        }
    }

    fun clear() {
        boxes.clear()
        player = Point()
        portal = Point()
        floor.clear()
        crystals.clear()
        width = 0
        height = 0
        name = ""
        goblins.clear()
        demons.clear()
        manas.clear()
        music = ""
    }
}