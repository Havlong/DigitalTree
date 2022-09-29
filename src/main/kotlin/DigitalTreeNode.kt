import java.util.*

data class DigitalTreeNode(val nextNodes: SortedMap<Char, Int> = sortedMapOf(), var isTerminal: Boolean = false)
