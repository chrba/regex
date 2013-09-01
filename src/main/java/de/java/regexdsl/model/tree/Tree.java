package de.java.regexdsl.model.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple tree implementation
 * 
 * @author Christian Bannes
 */
public class Tree<T> {
	
	private TreeNode<T> rootNode;
	
	public Tree(final TreeNode rootNode) {
		this.rootNode = rootNode;
	}
	
	
	public static class TreeNode<T> {
		private final T value;
		private final String name;
		private final Map<String, TreeNode<T>> namedChildNodes = new HashMap<String, TreeNode<T>>();
		
		public TreeNode(final String name, final T value) {
			this.name = name;
			this.value = value;
		}
		
		/**
		 * Adds the given node to the child node list.
		 * 
		 * @param name the name of the child node
		 * @param childNode the node to add
		 */
		public void addChild(final String name,  final TreeNode<T> childNode) {
			this.namedChildNodes.put(name, childNode);
		}
		
		/**
		 * Returns the child node with the given name, or null if it does not exist.
		 * 
		 * @param name the name of the child node
		 * @return the child node, or null
		 */
		public TreeNode<T> getChild(final String name) {
			return this.namedChildNodes.get(name);
		}
		
		
	}
}
