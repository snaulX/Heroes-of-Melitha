interface DynamicSpell: Spell {
    val actionTime: Double //in seconds
    val chance: Double //chance of act. If -1.0 always active
}