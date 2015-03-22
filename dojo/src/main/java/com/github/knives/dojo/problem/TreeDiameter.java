package com.github.knives.dojo.problem;

import com.github.knives.dojo.datastructure.TreeNode;

import java.util.List;

/**
 * The diameter of a tree (sometimes called the width) is the
 * number of nodes on the longest path between two leaves in the tree
 *
 * 1. longest path between two leaves go through root node of tree or subtree.
 * 2. height is the longest path to a leave of tree / subtree.
 * 3. height function be cached, otherwise the performance like fibonanci computation
 */
public class TreeDiameter {
    public static <T> int diameter(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }

        final int lheight = height(root.getLeft());
        final int rheight = height(root.getRight());

        // max between diamater of current node
        // or diameter of left node
        // or diameter of right node
        return Math.max(lheight + rheight + 1,
                Math.max(diameter(root.getLeft()), diameter(root.getRight())));
    }

    public static <T> int height(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(height(root.getLeft()), height(root.getRight()));
    }

    public static <T> List<T> diamaterPath(TreeNode<T> root) {
        // TODO:
        return null;
    }
}
