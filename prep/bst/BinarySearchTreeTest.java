package prep.bst;

import java.util.ArrayList;

import prep.Test;

public class BinarySearchTreeTest extends Test {
  public void runTests() {
    runTest(this::test1, "Insert, contain, min, max, nodeCount, height and inorder traversal check");
    runTest(this::test2, "Getting successor check");
    runTest(this::test3, "Removal and getting successor check");
  }

  private void test1() {
    var tree = new BinarySearchTree<Integer>();
    tree.insert(5);
    tree.insert(2);
    tree.insert(1);
    tree.insert(3);
    tree.insert(4);
    tree.insert(6);

    for (int i = 1; i <= 6; i += 1) {
      assertion(tree.contains(i), "Tree doesn't contain an inserted element");
    }

    assertEquals(tree.getMin(), 1, "The minimum element is incorrect");
    assertEquals(tree.getMax(), 6, "The maximum element is incorrect");
    assertEquals(tree.getNodeCount(), 6, "The number of nodes is incorrect");
    assertEquals(tree.getHeight(), 4, "The tree height is incorrect");

    assertEquals(tree.inorder().toString(), "[1, 2, 3, 4, 5, 6]", "Error with inorder traversal or insertion");
  }

  private void test2() {
    var tree1 = new BinarySearchTree<Integer>();
    tree1.insert(5);
    tree1.insert(2);
    tree1.insert(1);
    tree1.insert(3);
    tree1.insert(4);
    tree1.insert(7);
    tree1.insert(8);
    tree1.insert(6);

    for (int i = 0; i < 8; i += 1) {
      assertEquals(tree1.getSuccessor(i), i + 1, "Tree has a wrong successor to number " + i);
    }
    assertEquals(tree1.getSuccessor(8), null, "Tree has a wrong successor to the last element (6)");
    assertEquals(tree1.getSuccessor(-1), 1, "Tree has a wrong successor to the element before the first one (-1)");

    var tree2 = new BinarySearchTree<Integer>();
    tree2.insert(8);
    tree2.insert(4);
    tree2.insert(2);
    tree2.insert(6);
    tree2.insert(12);
    tree2.insert(10);
    tree2.insert(14);

    for (int i = 1; i <= 7; i += 1) {
      assertEquals(tree2.getSuccessor(i * 2 - 1), (i * 2), "Tree has a wrong successor");
    }
  }

  private void test3() {
    for (int i = 1; i <= 7; i += 1) {
      test3Helper(i);
    }
  }

  private void test3Helper(int valueToRemove) {
    var tree = new BinarySearchTree<Integer>();
    tree.insert(4);
    tree.insert(2);
    tree.insert(1);
    tree.insert(3);
    tree.insert(6);
    tree.insert(5);
    tree.insert(7);

    tree.delete(valueToRemove);

    assertEquals(tree.getMin(), (valueToRemove == 1) ? 2 : 1, "The minimum element is incorrect after deleting an element");
    assertEquals(tree.getMax(), (valueToRemove == 7) ? 6 : 7, "The maximum element is incorrect after deleting an element");
    assertEquals(tree.getNodeCount(), 6, "The number of nodes is incorrect after deleting an element");
    assertEquals(tree.getHeight(), 3, "The tree height is incorrect after deleting an element");

    if (valueToRemove != 7) {
      assertEquals(tree.getSuccessor(valueToRemove - 1), valueToRemove + 1, "Tree has a wrong successor to the element before the deleted one");
      assertEquals(tree.getSuccessor(valueToRemove), valueToRemove + 1, "Tree has a wrong successor to the deleted element");
      assertEquals(tree.getSuccessor(valueToRemove + 1), (valueToRemove == 6) ? null : valueToRemove + 2, "Tree has a wrong successor to the element after the deleted one");
    } else {
      assertEquals(tree.getSuccessor(valueToRemove - 1), null, "Tree has a wrong successor to the element before the deleted one");
      assertEquals(tree.getSuccessor(valueToRemove), null, "Tree has a wrong successor to the deleted element");
      assertEquals(tree.getSuccessor(valueToRemove + 1), null, "Tree has a wrong successor to the element after the deleted one");
    }

    for (int i = 1; i <= 7; i += 1) {
      if (i == valueToRemove) {
        continue;
      }
      assertion(tree.contains(i), "Tree doesn't contain an inserted element after deleting another element");
    }
    assertion(!tree.contains(valueToRemove), "Tree still contains an element after deleting it");

    var correctInorderList = new ArrayList<Integer>();
    for (int i = 1; i <= 7; i += 1) {
      if (i != valueToRemove) {
        correctInorderList.add(i);
      }
    }

    assertEquals(tree.inorder().toString(), correctInorderList.toString(), "Incorrect inorder traversal after deleting an element");

    if (haveAssertionsFailed()) {
      System.out.println("Failed assertions when removing value " + valueToRemove);
    }
  }
}
