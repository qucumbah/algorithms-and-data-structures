package prep.bst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
  private class TreeNode {
    public T value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(T value) {
      this.value = value;
    }
  }

  private TreeNode head;

  public void insert(T value) {
    head = insert(value, head);
  }
  private TreeNode insert(T valueToInsert, TreeNode where) {
    if (where == null) {
      return new TreeNode(valueToInsert);
    }

    if (valueToInsert.compareTo(where.value) < 0) {
      where.left = insert(valueToInsert, where.left);
    } else if (valueToInsert.compareTo(where.value) > 0) {
      where.right = insert(valueToInsert, where.right);
    }

    return where;
  }

  public int getNodeCount() {
    return getNodeCount(head);
  }
  private int getNodeCount(TreeNode root) {
    if (root == null) {
      return 0;
    }

    return 1 + getNodeCount(root.left) + getNodeCount(root.right);
  }

  public List<T> inorder() {
    var result = new ArrayList<T>();
    inorder(result, head);
    return result;
  }
  private void inorder(ArrayList<T> result, TreeNode root) {
    if (root == null) {
      return;
    }

    inorder(result, root.left);
    result.add(root.value);
    inorder(result, root.right);
  }

  public boolean contains(T valueToFind) {
    return contains(head, valueToFind);
  }
  private boolean contains(TreeNode root, T valueToFind) {
    if (root == null) {
      return false;
    }

    if (valueToFind.compareTo(root.value) < 0) {
      return contains(root.left, valueToFind);
    } else if (valueToFind.compareTo(root.value) > 0) {
      return contains(root.right, valueToFind);
    } else {
      return true;
    }
  }

  public int getHeight() {
    return getHeight(head);
  }
  private int getHeight(TreeNode root) {
    if (root == null) {
      return 0;
    }

    return 1 + Math.max(getHeight(root.left), getHeight(root.right));
  }

  public T getMin() {
    return getMin(head);
  }
  private T getMin(TreeNode root) {
    if (root == null) {
      return null;
    }

    if (root.left == null) {
      return root.value;
    }

    return getMin(root.left);
  }

  public T getMax() {
    return getMax(head);
  }
  private T getMax(TreeNode root) {
    if (root == null) {
      return null;
    }

    if (root.right == null) {
      return root.value;
    }

    return getMax(root.right);
  }

  public void delete(T value) {
    head = delete(head, value);
  }
  private TreeNode delete(TreeNode root, T valueToDelete) {
    if (root == null) {
      return null;
    }

    if (valueToDelete.compareTo(root.value) < 0) {
      root.left = delete(root.left, valueToDelete);
      return root;
    } else if (valueToDelete.compareTo(root.value) > 0) {
      root.right = delete(root.right, valueToDelete);
      return root;
    } else {
      if (root.left == null) {
        return root.right;
      }
      if (root.right == null) {
        return root.left;
      }

      T minInRightSubtree = getMin(root.right);
      var newRoot = new TreeNode(minInRightSubtree);
      newRoot.left = root.left;
      newRoot.right = delete(root.right, minInRightSubtree);

      return newRoot;
    }
  }

  public T getSuccessor(T value) {
    return getSuccessor(value, head);
  }
  private T getSuccessor(T value, TreeNode root) {
    if (root == null) {
      return null;
    }

    if (value.compareTo(root.value) < 0) {
      T result = getSuccessor(value, root.left);
      return (result == null) ? root.value : result;
    }

    if (value.compareTo(root.value) > 0) {
      return getSuccessor(value, root.right);
    }

    return getMin(root.right);
  }

  @Override
  public Iterator<T> iterator() {
    return inorder().iterator();
  }
}
