interface DynamicSpell: Spell {
    val actionTime: Double //in seconds
    val speed: Double //in seconds. -1.0 if spell always active
}