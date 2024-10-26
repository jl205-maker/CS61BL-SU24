import jh61b.utils.Reflection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {
    public static ArrayDeque61B deque;
    public static ArrayDeque61B deque2;
    @BeforeEach
    public void setUp() {
        deque = new ArrayDeque61B();
        deque2 = new ArrayDeque61B();
    }

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }
    @Test
    public void add_first_from_empty() {
        deque.addFirst(1);
        assertThat(deque.size()).isEqualTo(1);
        deque2.addFirst("middle");
        deque2.addLast("back");
        assertThat(deque2.toList().size()).isEqualTo(2);
    }

    @Test
    public void add_last_from_empty() {
        deque.removeFirst();
        deque.addLast(2);
        assertThat(deque.size()).isEqualTo(1);
    }

    @Test
    public void add_first_nonempty() {
        deque.addFirst(1);
        deque.addFirst(16);
        deque.addFirst(31);
        assertThat(deque.size()).isEqualTo(3);
        deque2.addFirst(0);
        deque2.addFirst(1);
        deque2.addFirst(2);
        deque2.addLast(-1);
        deque2.addLast(-2);
        assertThat(deque2.toList().size()).isEqualTo(5);

    }

    @Test
    public void add_last_nonempty() {
        deque.addFirst(1);
        deque.addLast(16);
        deque.addLast(31);
        assertThat(deque.size()).isEqualTo(3);
        deque2.addLast(0);
        deque2.addLast(0);
        assertThat(deque2.size()).isEqualTo(2);
    }

    @Test
    public void add_first_trigger_resize() {
        for (int i = 0; i < 10; i++) {
            deque.addFirst(3);
        }
        assertThat(deque.size()).isEqualTo(10);
        deque.addFirst(5);
        assertThat(deque.size()).isEqualTo(11);
        assertThat(deque.get(11)).isEqualTo(null);
    }

    @Test
    public void add_last_trigger_resize() {
        for (int i = 0; i < 10; i++) {
            deque.addLast(4);
        }
        assertThat(deque.size()).isEqualTo(10);
        deque.addLast(5);
        deque.addLast(8);
        assertThat(deque.size()).isEqualTo(12);
        assertThat(deque.get(11)).isEqualTo(8);
        assertThat(deque.get(12)).isEqualTo(null);
    }

    @Test
    public void add_first_after_remove_to_empty() {
        for (int i = 0; i < 10; i++) {
            deque.addLast(4);
        }
        assertThat(deque.size()).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            deque.removeFirst();
        }
        assertThat(deque.size()).isEqualTo(0);
        deque.addFirst(16);
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(16);
    }

    @Test
    public void add_last_after_remove_to_empty() {
        for (int i = 0; i < 10; i++) {
            deque.addLast(4);
        }
        assertThat(deque.size()).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            deque.removeFirst();
        }
        assertThat(deque.size()).isEqualTo(0);
        deque.addLast(16);
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(deque.size()-1)).isEqualTo(16);
    }

    @Test
    public void remove_first() {
        int toAdd = 1;
        for (int i = 0; i < 10; i++) {
            deque.addLast(toAdd);
            toAdd++;
        }
        assertThat(deque.size()).isEqualTo(10);
        deque.removeFirst();
        assertThat(deque.size()).isEqualTo(9);
        assertThat(deque.get(0)).isEqualTo(2);
    }

    @Test
    public void remove_last() {
        int toAdd = 1;
        for (int i = 0; i < 10; i++) {
            deque.addLast(toAdd);
            toAdd++;
        }
        assertThat(deque.size()).isEqualTo(10);
        deque.removeLast();
        assertThat(deque.size()).isEqualTo(9);
        assertThat(deque.get(deque.size())).isEqualTo(null);
        assertThat(deque.get(deque.size()-2)).isEqualTo(8);
    }

    @Test
    public void remove_first_to_empty() {
        int toAdd = 1;
        for (int i = 0; i < 10; i++) {
            deque.addLast(toAdd);
            toAdd++;
        }
        assertThat(deque.isEmpty()).isFalse();
        for (int i = 0; i < 10; i++) {
            deque.removeFirst();
        }
        assertThat(deque.isEmpty()).isTrue();
    }


    @Test
    public void remove_last_to_empty() {
        int toAdd = 1;
        for (int i = 0; i < 10; i++) {
            deque.addLast(toAdd);
            toAdd++;
        }
        assertThat(deque.isEmpty()).isFalse();
        for (int i = 0; i < 10; i++) {
            deque.removeLast();
        }
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void remove_first_to_one() {
        int toAdd = 1;
        for (int i = 0; i < 10; i++) {
            deque.addLast(toAdd);
            toAdd++;
        }
        assertThat(deque.isEmpty()).isFalse();
        for (int i = 0; i < 9; i++) {
            deque.removeFirst();
        }
        assertThat(deque.isEmpty()).isFalse();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(10);
    }

    @Test
    public void remove_last_to_one() {
        int toAdd = 1;
        for (int i = 0; i < 10; i++) {
            deque.addLast(toAdd);
            toAdd++;
        }
        assertThat(deque.isEmpty()).isFalse();
        for (int i = 0; i < 9; i++) {
            deque.removeLast();
        }
        assertThat(deque.isEmpty()).isFalse();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(1);
    }

    @Test
    public void remove_first_trigger_resize() {
        for (int i = 0; i < 10; i++) {
            deque.addLast(4);
        }
        assertThat(deque.size()).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            deque.addLast(16);
        }
        assertThat(deque.size()).isEqualTo(20);
        for (int i = 0; i < 16; i++) {
            deque.removeFirst();
        }
        assertThat(deque.size()).isEqualTo(4);
    }

    @Test
    public void remove_last_trigger_resize() {
        for (int i = 0; i < 10; i++) {
            deque.addLast(4);
        }
        assertThat(deque.size()).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            deque.addLast(16);
        }
        assertThat(deque.size()).isEqualTo(20);
        for (int i = 0; i < 16; i++) {
            deque.removeLast();
        }
        assertThat(deque.size()).isEqualTo(4);
    }

    @Test
    public void get_valid() {
        int toAdd = 1;
        for (int i = 0; i < 10; i++) {
            deque.addLast(toAdd);
            toAdd++;
        }
        assertThat(deque.get(0)).isEqualTo(1);
        assertThat(deque.get(5)).isEqualTo(6);
        assertThat(deque.get(9)).isEqualTo(10);
    }

    @Test
    public void get_oob_large() {
        int toAdd = 1;
        for (int i = 0; i < 10; i++) {
            deque.addLast(toAdd);
            toAdd++;
        }
        assertThat(deque.get(16)).isEqualTo(null);
    }

    @Test
    public void get_oob_neg() {
        int toAdd = 1;
        for (int i = 0; i < 10; i++) {
            deque.addLast(toAdd);
            toAdd++;
        }
        assertThat(deque.get(-7)).isEqualTo(null);
    }

    @Test
    public void valid_size() {
        for (int i = 0; i < 10; i++) {
            deque.addLast(4);
        }
        assertThat(deque.size()).isEqualTo(10);
        for (int i = 0; i < 5; i++) {
            deque.removeFirst();
        }
        assertThat(deque.size()).isEqualTo(5);
    }

    @Test
    public void size_after_remove_to_empty() {
        for (int i = 0; i < 10; i++) {
            deque.addLast(4);
        }
        assertThat(deque.size()).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            deque.removeFirst();
        }
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void size_after_remove_from_empty() {
        deque.removeFirst();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void is_empty_true() {
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void is_empty_false() {
        deque.addFirst(16);
        assertThat(deque.isEmpty()).isFalse();
    }

    @Test
    public void to_list_empty() {
        int listLength = deque.toList().size();
        assertThat(listLength).isEqualTo(0);
    }

    @Test
    public void to_list_nonempty() {
        deque.addFirst(5);
        deque.addLast(8);
        deque.removeFirst();
        int listLength = deque.toList().size();
        assertThat(listLength).isEqualTo(1);
    }

    @Test
    public void resize_up_and_resize_down() {
        for (int i = 0; i < 10; i++) {
            deque.addLast(4);
        }
        assertThat(deque.size()).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            deque.addLast(16);
        }
        assertThat(deque.size()).isEqualTo(20);
        for (int i = 0; i < 16; i++) {
            deque.removeFirst();
        }
        assertThat(deque.size()).isEqualTo(4);
    }

}
