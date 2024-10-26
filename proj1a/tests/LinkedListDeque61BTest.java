import com.google.common.truth.Truth;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void removeFirstAndRemoveLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(1);
        int removed = lld1.removeFirst();
        assertThat(removed).isEqualTo(1);
        assertThat(lld1.size()).isEqualTo(0);
        lld1.addLast(1);
        int removed2 = lld1.removeLast();
        assertThat(removed2).isEqualTo(1);
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void removeLastToOne() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.get(0)).isEqualTo(1);
    }
    @Test
    public void getTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        //get valid
        lld1.addFirst(1);
        lld1.addLast(2);
        assertThat(lld1.get(1)).isEqualTo(2);

        //index oob and negative index
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();

        lld2.addFirst(1);
        assertThat(lld1.get(2)).isNull();
        assertThat(lld1.get(-3)).isNull();
    }

    @Test
    public void getRecursiveValid() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        assertThat(lld1.getRecursive(1)).isEqualTo(2);

        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        lld2.addFirst(1);
        assertThat(lld1.getRecursive(2)).isNull();

        Deque61B<Integer> lld3 = new LinkedListDeque61B<>();
        lld3.addFirst(1);
        assertThat(lld3.getRecursive(-1)).isNull();
    }

    @Test
    public void size() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        assertThat(lld1.size()).isEqualTo(2);
    }

    @Test
    public void sizeAfterRemoveToEmpty() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        lld1.removeFirst();
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void sizeAfterRemoveFromEmpty() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void isEmptyTrue() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void isEmptyFalse() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(1);
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void toListNonEmpty() {
        LinkedListDeque61B<Integer> deque;
        deque = new LinkedListDeque61B<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        List<Integer> list = deque.toList();
        Truth.assertThat(list).containsExactly(3, 1, 2).inOrder();
    }
    @Test
    public void toListEmpty() {
        LinkedListDeque61B<Integer> deque;
        deque = new LinkedListDeque61B<>();
        List<Integer> list = deque.toList();
        Truth.assertThat(list).isEmpty();
    }

    @Test
    public void toListAfterRemove() {
        LinkedListDeque61B<Integer> deque;
        deque = new LinkedListDeque61B<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.removeFirst();
        List<Integer> list = deque.toList();
        Truth.assertThat(list).containsExactly(2);
    }
}
