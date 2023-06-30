import statemachine.Trie

fun main() {
	val text: String = readln()
	val num: Int = readln().toInt()
	val patterns = mutableListOf<String>()

	(1..num).forEach {
		patterns.add(readln())
	}

	val trie = Trie()
	trie.addStrings(patterns)
	trie.getSuffixLinks()
	val res = trie.findAllPatterns(text)

	res.forEach {
		println("${it.first} ${it.second}")
	}
}
