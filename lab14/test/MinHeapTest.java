import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MinHeapTest {

    @Test
    public void testAddInsert() {
        MinHeap<Character> heap1 = new MinHeap<>();
        assertThat(heap1.size()).isEqualTo(0);
        heap1.insert('f');
        heap1.insert('h');
        heap1.insert('d');
        heap1.insert('b');
        heap1.insert('c');
        assertThat(heap1.size()).isEqualTo(5);
    }

    @Test
    public void testRemoveMin() {
        MinHeap<Integer> heap1 = new MinHeap<>();
        assertThat(heap1.size()).isEqualTo(0);
        heap1.insert(1);
        heap1.insert(2);
        heap1.insert(3);
        heap1.insert(4);
        assertThat(heap1.size()).isEqualTo(4);
        int currMin = heap1.removeMin();
        assertThat(heap1.size()).isEqualTo(3);
        assertThat(currMin).isEqualTo(1);
        int currMin2 = heap1.removeMin();
        assertThat(heap1.size()).isEqualTo(2);
        assertThat(currMin2).isEqualTo(2);

    }
}
