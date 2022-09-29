import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DigitalTreeTest {
    private lateinit var tree: DigitalTree

    @BeforeEach
    fun setUp() {
        tree = DigitalTree()
        tree.addAll("a", "b", "c", "d")
    }

    @Test
    fun add() {
        val word = "wordThatIsLong and can't be already in tree"

        println("Before add(): $tree")
        tree.add(word)
        println("After add(): $tree")

        kotlin.test.assertTrue { word in tree }
    }

    @Test
    fun addAll() {
        val words = listOf("word1", "secondWord", "anotherOne", "4WoRd", "#even@Some^:signs")

        println("Before addAll(): $tree")
        tree.addAll(words)
        println("After addAll(): $tree")

        words.forEach { kotlin.test.assertTrue { it in tree } }
    }

    @Test
    fun clear() {
        val mentionedWords = listOf(
            "a",
            "b",
            "c",
            "d",
            "wordThatIsLong and can't be already in tree",
            "word1",
            "secondWord",
            "anotherOne",
            "4WoRd",
            "#even@Some^:signs"
        )

        println("Before clear(): $tree")
        tree.clear()
        println("After clear(): $tree")

        mentionedWords.forEach { kotlin.test.assertFalse { it in tree } }
    }

    @Test
    fun remove() {
        tree += "alsoWordWithA"
        tree += "a"

        println("Before remove(): $tree")
        tree.remove("a")
        println("After remove(): $tree")

        kotlin.test.assertTrue { "alsoWordWithA" in tree }
        kotlin.test.assertFalse { "a" in tree }
        kotlin.test.assertFalse { "also" in tree }
    }

    @Test
    fun contains() {
        kotlin.test.assertTrue { tree.contains("a") }
        kotlin.test.assertFalse { tree.contains("anotherWordThatIsNotMentioned") }
        kotlin.test.assertTrue { tree.contains("b") }
        kotlin.test.assertTrue { tree.contains("c") }
        kotlin.test.assertFalse { tree.contains("z") }
        kotlin.test.assertTrue { tree.contains("") }
        kotlin.test.assertFalse { tree.contains("bake") }
        kotlin.test.assertFalse { tree.contains("oenophiles") }
    }

    @Test
    fun containsPrefix() {
        kotlin.test.assertTrue { tree.containsPrefix("") }
        kotlin.test.assertTrue { tree.containsPrefix("a") }
        kotlin.test.assertFalse { tree.containsPrefix("ab") }

        val wordPrefixes = "abracadabra".runningFold("", String::plus)

        tree += wordPrefixes.last()
        println("After added \"abracadabra\": $tree")

        wordPrefixes.forEach { kotlin.test.assertTrue { tree.containsPrefix(it) } }
    }

    @Test
    fun dec() {
        val mentionedWords = listOf(
            "a",
            "b",
            "c",
            "d",
            "wordThatIsLong and can't be already in tree",
            "word1",
            "secondWord",
            "anotherOne",
            "4WoRd",
            "#even@Some^:signs"
        )

        println("Before --tree: $tree")
        --tree
        println("After --tree: $tree")

        mentionedWords.forEach { kotlin.test.assertFalse { it in tree } }

        tree += mentionedWords

        println("Before tree--: $tree")
        tree--
        println("After tree--: $tree")

        mentionedWords.forEach { kotlin.test.assertFalse { it in tree } }
    }

    @Test
    fun collectWords() {
        val words = tree.collectWords()
        assertEquals(words.sorted(), words)
    }

    @Test
    fun testToString() {
        assertEquals("DigitalTree: [ '', 'a', 'b', 'c', 'd' ]", tree.toString())
        println(tree)
    }
}