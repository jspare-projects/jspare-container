/*
 * Copyright 2016 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jspare.core.collections;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;

/**
 * The Class TreeNode.
 *
 * @author pflima
 * @param <T>
 *            the generic type
 * @since 30/03/2016
 */
public class TreeNode<T> implements Iterable<TreeNode<T>> {

	/**
	 * The Enum ProcessStages.
	 *
	 * @author pflima
	 * @since 30/03/2016
	 */
	enum ProcessStages {

		/** The Process parent. */
		ProcessParent,
		/** The Process child cur node. */
		ProcessChildCurNode,
		/** The Process child sub node. */
		ProcessChildSubNode
	}

	/**
	 * The Class TreeNodeIterator.
	 *
	 * @author pflima
	 * @param <T>
	 *            the generic type
	 * @since 30/03/2016
	 */
	@SuppressWarnings("hiding")
	class TreeNodeIterator<T> implements Iterator<TreeNode<T>> {

		/** The tree node. */
		private TreeNode<T> treeNode;

		/** The do next. */
		private ProcessStages doNext;

		/** The next. */
		private TreeNode<T> next;

		/** The children cur node iter. */
		private Iterator<TreeNode<T>> childrenCurNodeIter;

		/** The children sub node iter. */
		private Iterator<TreeNode<T>> childrenSubNodeIter;

		/**
		 * Instantiates a new tree node iterator.
		 *
		 * @param treeNode
		 *            the tree node
		 */
		public TreeNodeIterator(TreeNode<T> treeNode) {
			this.treeNode = treeNode;
			this.doNext = ProcessStages.ProcessParent;
			this.childrenCurNodeIter = treeNode.children.iterator();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {

			if (this.doNext == ProcessStages.ProcessParent) {
				this.next = this.treeNode;
				this.doNext = ProcessStages.ProcessChildCurNode;
				return true;
			}

			if (this.doNext == ProcessStages.ProcessChildCurNode) {
				if (childrenCurNodeIter.hasNext()) {
					TreeNode<T> childDirect = childrenCurNodeIter.next();
					childrenSubNodeIter = childDirect.iterator();
					this.doNext = ProcessStages.ProcessChildSubNode;
					return hasNext();
				}

				else {
					this.doNext = null;
					return false;
				}
			}

			if (this.doNext == ProcessStages.ProcessChildSubNode) {
				if (childrenSubNodeIter.hasNext()) {
					this.next = childrenSubNodeIter.next();
					return true;
				} else {
					this.next = null;
					this.doNext = ProcessStages.ProcessChildCurNode;
					return hasNext();
				}
			}

			return false;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.Iterator#next()
		 */
		@Override
		public TreeNode<T> next() {
			return this.next;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	@Getter
	private T data;

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	@Getter
	private TreeNode<T> parent;

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	@Getter
	private List<TreeNode<T>> children;

	/** The elements index. */
	private List<TreeNode<T>> elementsIndex;

	/**
	 * Instantiates a new tree node.
	 *
	 * @param data
	 *            the data
	 */
	public TreeNode(T data) {
		this.data = data;
		this.children = new LinkedList<TreeNode<T>>();
		this.elementsIndex = new LinkedList<TreeNode<T>>();
		this.elementsIndex.add(this);
	}

	/**
	 * Adds the child.
	 *
	 * @param child
	 *            the child
	 * @return the tree node
	 */
	public TreeNode<T> addChild(T child) {
		TreeNode<T> childNode = new TreeNode<T>(child);
		childNode.parent = this;
		this.children.add(childNode);
		this.registerChildForSearch(childNode);
		return childNode;
	}

	/**
	 * Find tree node.
	 *
	 * @param cmp
	 *            the cmp
	 * @return the tree node
	 */
	public TreeNode<T> findTreeNode(Comparable<T> cmp) {
		for (TreeNode<T> element : this.elementsIndex) {
			T elData = element.data;
			if (cmp.compareTo(elData) == 0) {
				return element;
			}
		}

		return null;
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		if (this.isRoot()) {
			return 0;
		} else {
			return parent.getLevel() + 1;
		}
	}

	/**
	 * Checks if is leaf.
	 *
	 * @return true, if is leaf
	 */
	public boolean isLeaf() {
		return children.size() == 0;
	}

	/**
	 * Checks if is root.
	 *
	 * @return true, if is root
	 */
	public boolean isRoot() {
		return parent == null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<TreeNode<T>> iterator() {
		return new TreeNodeIterator<T>(this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return data != null ? data.toString() : "[data null]";
	}

	/**
	 * To string verbose.
	 *
	 * @return the string
	 */
	public String toStringVerbose() {
		StringBuilder builder = new StringBuilder();
		this.forEach(node -> builder.append("\n").append(StringUtils.repeat("---", node.getLevel())).append(node.getData().toString()));
		return builder.toString();
	}

	/**
	 * Register child for search.
	 *
	 * @param node
	 *            the node
	 */
	private void registerChildForSearch(TreeNode<T> node) {
		elementsIndex.add(node);
		if (parent != null) {
			parent.registerChildForSearch(node);
		}
	}
}