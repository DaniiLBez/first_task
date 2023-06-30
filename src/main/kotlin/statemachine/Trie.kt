package statemachine

import java.util.LinkedList
import java.util.Queue

class Trie {
	private val root = Node()
	private var patterns = mutableListOf<String>()

	fun addStrings(s: List<String>) {
		for (string in s) {
			var x = this.root
			for (sym in string) {
				when (x.move.containsKey(sym)) {
					true -> x = x.move[sym]!!
					false -> { x.move[sym] = Node(); x = x.move[sym]!! }
				}
			}
			x.output.add(string)
			this.patterns.add(string)
		}
	}

	fun getSuffixLinks() {
		val queue: Queue<Node> = LinkedList()
		for (child in this.root.move.values) {
			child.sufLink = this.root
			child.output.addAll(child.sufLink!!.output)
			queue.add(child)
		}

		while (queue.isNotEmpty()) {
			val parent = queue.remove()
			for ((key, unode) in parent.move) {
				queue.add(unode)
				var fnode = parent.sufLink
				while (fnode != null && !fnode.move.containsKey(key)) {
					fnode = fnode.sufLink
				}
				if (fnode != null)
					unode.sufLink = fnode.move[key]
				else
					unode.sufLink = root
				unode.sufLink?.let { unode.output.addAll(it.output) }
			}
		}
	}

	private fun getPatternNumber(s: String) = this.patterns.indexOf(s)

	fun findAllPatterns(content: String): List<Pair<Int, Int>> {
		var iter: Node? = this.root
		val foundSubstrings = mutableListOf<Pair<Int, Int>>()
		content.forEachIndexed { index, value ->
			while (iter != null && value !in iter!!.move.keys) {
				iter = iter?.sufLink
			}

			if (iter == null) {
				iter = this.root
				return@forEachIndexed
			}

			iter = iter!!.move[value]
			for (pattern in iter!!.output) {
				foundSubstrings.add(Pair(index - pattern.length + 2, getPatternNumber(pattern) + 1))
			}
		}
		return foundSubstrings.sortedWith(compareBy({ it.first }, { it.second }))
	}
}
