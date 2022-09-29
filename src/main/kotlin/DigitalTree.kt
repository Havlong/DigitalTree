class DigitalTree {
    private fun newRootNode() = DigitalTreeNode(isTerminal = true)

    private val tree = mutableListOf(newRootNode())

    fun add(word: String) {
        this += word
    }

    fun addAll(words: Iterable<String>) {
        this += words
    }

    fun addAll(vararg words: String) {
        words.forEach(this::plusAssign)
    }

    fun clear() {
        tree.clear()
        tree.add(newRootNode())
    }

    fun remove(word: String) {
        this -= word
    }

    operator fun plusAssign(word: String) {
        if (word.isEmpty()) {
            return
        }
        var nodeID = 0
        for (c in word) {
            val branches = tree[nodeID].nextNodes

            nodeID = branches[c] ?: kotlin.run {
                val newNodeID = tree.size

                val newNode = DigitalTreeNode()
                tree.add(newNode)

                branches[c] = newNodeID
                newNodeID
            }
        }
        tree[nodeID].isTerminal = true
    }

    operator fun plusAssign(words: Iterable<String>) = words.forEach(this::plusAssign)

    operator fun contains(word: String): Boolean {
        var nodeID = 0
        for (c in word) {
            val branches = tree[nodeID].nextNodes
            nodeID = branches[c] ?: return false
        }
        return tree[nodeID].isTerminal
    }

    fun containsPrefix(word: String): Boolean {
        var nodeID = 0
        for (c in word) {
            val branches = tree[nodeID].nextNodes
            nodeID = branches[c] ?: return false
        }
        return true
    }

    operator fun minusAssign(word: String) {
        if (word.isEmpty() || word !in this) {
            return
        }
        var nodeID = 0
        for (c in word) {
            nodeID = tree[nodeID].nextNodes[c]!!
        }
        tree[nodeID].isTerminal = false
    }

    operator fun dec(): DigitalTree {
        clear()
        return this
    }

    private fun collectInNode(nodeID: Int, word: StringBuilder): List<String> = buildList {
        if (tree[nodeID].isTerminal) this += word.toString()
        for ((c, nextNode) in tree[nodeID].nextNodes) {
            word.append(c)
            this += collectInNode(nextNode, word)
            word.deleteAt(word.length - 1)
        }
    }

    fun collectWords(): List<String> = collectInNode(0, StringBuilder())

    override fun toString(): String =
        "DigitalTree: ${collectWords().joinToString(prefix = "[ '", separator = "', '", postfix = "' ]")}"
}