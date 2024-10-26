import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static com.google.common.truth.Truth.assertThat;

public class BinaryTreeTest {
    //@BeforeEach
    BinaryTree<String> strBT1 = BinaryTree.sampleTree1();
    BinaryTree<String> strBT2 = BinaryTree.sampleTree2();
    BinaryTree<String> strBT3 = BinaryTree.sampleTree3();
    BinaryTree<String> strBT4 = BinaryTree.sampleTree4();
    BinaryTree<Integer> intBT1 = BinaryTree.fibTree(0);

    @Test
    public void sampleHeightTest() {
        BinaryTree<String> emptyTree = new BinaryTree<>();
        assertThat(emptyTree.height()).isEqualTo(0);

        BinaryTree<String> sample1 = BinaryTree.sampleTree1();
        assertThat(sample1.height()).isEqualTo(2);
    }

    @Test
    public void testHeight() {
        assertThat(strBT1.height()).isEqualTo(2);
        assertThat(strBT2.height()).isEqualTo(4);
        assertThat(strBT3.height()).isEqualTo(4);
        assertThat(strBT4.height()).isEqualTo(3);
    }

    @Test
    public void testIsBalanced() {
        assertThat(strBT1.isCompletelyBalanced()).isTrue();
        assertThat(strBT2.isCompletelyBalanced()).isFalse();
        assertThat(strBT3.isCompletelyBalanced()).isFalse();
        assertThat(strBT4.isCompletelyBalanced()).isTrue();
    }

}