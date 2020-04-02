import java.util.*;
public class Testing {
    @Property
    public boolean squared (@IntRange(min = -10, max = 5) Integer x, @StringSet(strings={"s2", "s3"}) String y) {
        return false;
    }
    @Property
    public boolean squared2(@ListLength(min = 0, max = 1) List<@IntRange(min = 5, max = 7) Integer> x) {
        return false;
    }
}
