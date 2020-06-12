interface Spell {
    val cost: Int
    var level: Int
    val range: Double
    val castTime: Double //in seconds
    var x: Double
    var y: Double
    fun attack(x: Double, y: Double)
    fun actOn()
}