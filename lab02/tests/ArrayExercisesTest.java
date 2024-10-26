import org.junit.jupiter.api.*;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArrayExercisesTest {

    @Test
    @Order(0)
    @DisplayName("Test makeDice correctness")
    public void testMakeDice() {
        int[] dice = ArrayExercises.makeDice();
        assertThat(dice).isNotNull();
        assertThat(dice.length).isEqualTo(6);
        for (int i = 0; i < 6; i++) {
            assertThat(dice[i]).isEqualTo(i + 1);
        }
    }

    @Test
    @Order(1)
    @DisplayName("Test takeOrder correctness")
    public void testTakeOrder() {
        String[] order1 = ArrayExercises.takeOrder("Circle");
        String[] order2 = ArrayExercises.takeOrder("Erik");

        assertThat(order1).isNotNull();
        assertThat(order2).isNotNull();

        assertThat(order1).hasLength(4);
        assertThat(order2).hasLength(4);

        String[] expected1 = new String[]{"beyti", "pizza", "hamburger", "tea"};
        String[] expected2 = new String[]{"sushi", "pasta", "avocado", "coffee"};

        for (int i = 0; i < 4; i++) {
            assertThat(order1[i]).isEqualTo(expected1[i]);
            assertThat(order2[i]).isEqualTo(expected2[i]);
        }

        String[] order3 = ArrayExercises.takeOrder("Teresa");

        assertThat(order3).isNotNull();
        assertThat(order3).hasLength(3);
    }

    @Test
    @Order(2)
    @DisplayName("Test findMinMax correctness")
    public void testFindMinMax() {
        int[] test1 = new int[]{1, 2, 3, 4, 5, 6};
        assertThat(ArrayExercises.findMinMax(test1)).isEqualTo(5);

        int[] test2 = new int[]{2, 4, 6, 8};
        assertThat(ArrayExercises.findMinMax(test2)).isEqualTo(6);
    }
}
