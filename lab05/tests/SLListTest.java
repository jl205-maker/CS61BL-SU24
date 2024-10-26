import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class SLListTest {

    @Test
    public void testSLListAdd() {
        SLList test1 = SLList.of(1, 3, 5); /* test1: {1, 3, 5} */
        SLList test2 = new SLList(); /* test2: {} */

        test1.add(1, 2); /* test1: {1, 2, 3, 5}*/
        test1.add(3, 4); /* test1: {1, 2, 3, 4, 5}*/
        assertWithMessage("test1 does not have a size of 5").that(test1.size()).isEqualTo(5);
        assertWithMessage("test1 does not have 3 at index 2").that(test1.get(2)).isEqualTo(3);
        assertWithMessage("test1 does not have 4 at index 3").that(test1.get(3)).isEqualTo(4);

        test2.add(1, 1); /* test2: {1} */
        assertWithMessage("test2 does not contain 1").that(test2.get(0)).isEqualTo(1);
        assertWithMessage("test2 does not have a size of 1").that(test2.size()).isEqualTo(1);

        test2.add(10, 10); /* test2: {1, 10} */
        assertWithMessage("test2 does not have 10 at the index 1").that(test2.get(1)).isEqualTo(10);
        test1.add(0, 0); /* test1: {0, 1, 2, 3, 4, 5}*/
        assertWithMessage("test1 is incorrect after addition at the front").that(test1.equals(SLList.of(0, 1, 2, 3, 4, 5))).isTrue();
    }

    @Test
    public void testSLListReverse() {
        // General case: for list size >= 2, return a reversed list
        SLList testRev1 = SLList.of(1, 2, 3, 4);
        testRev1.reverse();
        assertWithMessage("testRev1 should have 4 at index 0").that(testRev1.get(0)).isEqualTo(4);
        // Base case: reverse list of size 1
        SLList testRev2 = SLList.of(16);
        testRev2.reverse();
        assertWithMessage("testRev2 should have 16 at index 0").that(testRev2.get(0)).isEqualTo(16);
        // Base case: reverse list of size 0
        SLList testRev3 = new SLList();
        testRev3.reverse();
        assertWithMessage("testRev3 should have a size of 0").that(testRev3.size()).isEqualTo(0);
    }
}
