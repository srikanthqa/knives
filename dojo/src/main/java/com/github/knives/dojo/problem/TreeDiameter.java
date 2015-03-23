package com.github.knives.dojo.problem;

import com.github.knives.dojo.datastructure.ConsList;
import com.github.knives.dojo.datastructure.TreeNode;
import com.github.knives.dojo.utils.MathUtils;

import java.util.*;

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

        // normally, this could implement as in OO manner by decorator pattern
        final Map<TreeNode<T>, Integer> heightCache = new HashMap<TreeNode<T>, Integer>();

        return diameter(root, heightCache);
    }

    public static <T> int diameter(TreeNode<T> root, Map<TreeNode<T>, Integer> heightCache) {
        if (root == null) {
            return 0;
        }

        final int lheight = height(root.getLeft(), heightCache);
        final int rheight = height(root.getRight(), heightCache);

        // max between diamater of current node
        // or diameter of left node
        // or diameter of right node
        return MathUtils.max(lheight + rheight + 1, diameter(root.getLeft(), heightCache), diameter(root.getRight(), heightCache));
    }

    public static <T> int height(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(height(root.getLeft()), height(root.getRight()));
    }

    public static <T> int height(TreeNode<T> root, Map<TreeNode<T>, Integer> heightCache) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(
                heightCache.get(root.getLeft()) != null ? heightCache.get(root.getLeft()) : height(root.getLeft(), heightCache),
                heightCache.get(root.getRight()) != null ? heightCache.get(root.getRight()) : height(root.getRight(), heightCache)
        );
    }

    public static <T> List<TreeNode<T>> diamaterPath(TreeNode<T> root) {
        if (root == null) {
            return Collections.emptyList();
        }

        // TODO:

        // normally, this could implement as in OO manner by decorator pattern
        final Map<TreeNode<T>, ConsList<TreeNode<T>>> heightCache = new HashMap<TreeNode<T>, ConsList<TreeNode<T>>>();

        return null;
    }

    public static <T> ConsList<T> diameterPath(TreeNode<T> root, Map<TreeNode<T>, ConsList<TreeNode<T>>> heightCache) {

        return null;
    }
}
