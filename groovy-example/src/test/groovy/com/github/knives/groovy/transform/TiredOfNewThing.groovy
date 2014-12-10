package com.github.knives.groovy.transform

/**
 * @Newify
 * 
 * allows
 * 1. create an instance with new() class method
 * 2. ommit new keyword together (but need to specify class)
 */

@Newify([])
rubyLikeNew() {
	assert Integer.new(42) == 42
}

rubyLikeNew()


class Tree {
	def elements
	Tree(Object... elements) { this.elements = elements as List }
}

class Leaf {
	def value
	Leaf(value) { this.value = value }
}

def buildTree() {
	new Tree(new Tree(new Leaf(1), new Leaf(2)), new Leaf(3))
}

@Newify([Tree, Leaf]) 
def buildTree2() {
	Tree(Tree(Leaf(1), Leaf(2)), Leaf(3))
}

buildTree()
buildTree2()