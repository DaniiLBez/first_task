package statemachine

class Node {
	var output: MutableList<String> = mutableListOf()
	var move: MutableMap<Char, Node> = mutableMapOf()
	var sufLink: Node? = null
}
