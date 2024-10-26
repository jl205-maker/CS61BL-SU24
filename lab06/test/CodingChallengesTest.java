import org.junit.Test;

import static com.google.common.truth.Truth.assertWithMessage;

public class CodingChallengesTest {

    @Test
    public void testMissingNumber() {
        int[] missing3 = {0, 1, 2, 4, 5, 6, 7};
        int case1 = CodingChallenges.missingNumber(missing3);
        assertWithMessage("The array is missing 3").that(case1).isEqualTo(3);
        int[] missing2 = {0, 1, 3, 4, 5, 6, 7};
        int case2 = CodingChallenges.missingNumber(missing2);
        assertWithMessage("The array is missing 2").that(case2).isEqualTo(2);
        int[] missing0 = {1, 2, 3, 4, 5, 6, 7};
        int case3 = CodingChallenges.missingNumber(missing0);
        assertWithMessage("The array is missing 0").that(case3).isEqualTo(0);
    }


    @Test
    public void testIsPermutation() {
        boolean case1 = CodingChallenges.isPermutation("String s&1", "String s.2");
        assertWithMessage("Case 1 is not permutation").that(case1).isEqualTo(false);
        boolean case1var = CodingChallenges.isPermutation("a;bc", "abc.");
        assertWithMessage("Case 1var is not permutation").that(case1var).isEqualTo(false);
        boolean case2 = CodingChallenges.isPermutation("", "");
        assertWithMessage("Case 2 is permutation").that(case2).isEqualTo(true);
        boolean case3 = CodingChallenges.isPermutation("abcda", "cbdaa");
        assertWithMessage("Case 3 is permutation").that(case3).isEqualTo(true);
        boolean case4 = CodingChallenges.isPermutation("sbafw", "etfas");
        assertWithMessage("Case 4 is not permutation").that(case4).isEqualTo(false);
        boolean case5 = CodingChallenges.isPermutation("sbafw", "sbaf");
        assertWithMessage("Case 5 is not permutation").that(case5).isEqualTo(false);
    }
}
