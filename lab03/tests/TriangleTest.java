import org.junit.Rule;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
public abstract class TriangleTest {

    /** For autograding purposes; do not change this line. */
    abstract Triangle getNewTriangle();

    /* ***** TESTS ***** */

    // FIXME: Add additional tests for Triangle.java here that pass on a
    //  correct Triangle implementation and fail on buggy Triangle implementations.

    @Test
    public void test1() {
        // TODO: stub for first test
        Triangle t = getNewTriangle();
        // remember that you'll have to call on Triangle methods like
        // t.functionName(arguments), where t is a Triangle object
        //test
        boolean passCase1 = t.sidesFormTriangle(3, 4, 5);
        boolean passCase2 = t.sidesFormTriangle(4, 4, 4);
        boolean failCase1 = t.sidesFormTriangle(0, 0, 0);
        boolean failCase2 = t.sidesFormTriangle(1, 2, 6);
        boolean failCase3 = t.sidesFormTriangle(3, 5, 8);
        boolean failCase4 = t.sidesFormTriangle(2, 2, 4);
        //assertions
        assertThat(passCase1).isEqualTo(true);
        assertThat(passCase2).isEqualTo(true);
        assertThat(failCase1).isEqualTo(false);
        assertThat(failCase2).isEqualTo(false);
        assertThat(failCase3).isEqualTo(false);
        assertThat(failCase4).isEqualTo(false);

    }
    @Test
    public void test2() {
        Triangle t = getNewTriangle();
        //test
        boolean validPoints1 = t.pointsFormTriangle(0,0,0,4,3,0);
        boolean validPoints2 = t.pointsFormTriangle(1, 3, 2, 0, 3, 4);
        boolean invalidPoints1 = t.pointsFormTriangle(0,0,4,7,4,7);
        boolean invalidPoints2 = t.pointsFormTriangle(0, 1, 0, 2,0, 3);
        //assertions
        assertThat(validPoints1).isEqualTo(true);
        assertThat(validPoints2).isEqualTo(true);
        assertThat(invalidPoints1).isEqualTo(false);
        assertThat(invalidPoints2).isEqualTo(false);
    }
    @Test
    public void test3() {
        Triangle t = getNewTriangle();
        //test
        String case1 = t.triangleType(3, 4, 5);
        String case2 = t.triangleType(4, 4, 4);
        String case3 = t.triangleType(2, 3, 3);
        //assertions
        assertThat(case1).isEqualTo("Scalene");
        assertThat(case2).isEqualTo("Equilateral");
        assertThat(case3).isEqualTo("Isosceles");
    }
    @Test
    public void test4() {
        Triangle t = getNewTriangle();
        //test
        int hypo1 = t.squaredHypotenuse(3, 4);
        int hypo2 = t.squaredHypotenuse(4, 4);
        int hypoError1 = t.squaredHypotenuse(0, 0);
        int hypoError2 = t.squaredHypotenuse(-5, 3);
        //assertions
        assertThat(hypo1).isEqualTo(25);
        assertThat(hypo2).isEqualTo(32);
        assertWithMessage("HypoError1 cannot form a triangle!")
                .that(hypoError1)
                .isEqualTo(0);
        assertWithMessage("HypoError2 cannot form a triangle!")
                .that(hypoError2)
                .isEqualTo(34);
    }


}
