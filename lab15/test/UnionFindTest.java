import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class UnionFindTest {

    @Test
    public void test() {
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(0, 2);
        uf.union(5, 7);
        uf.union(7, 3);
        assertThat(uf.find(0)).isEqualTo(3);
        assertThat(uf.parent(0)).isEqualTo(3);
    }

}


