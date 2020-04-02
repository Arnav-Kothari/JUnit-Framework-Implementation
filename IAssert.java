public class IAssert {
    private static int i;

    public IAssert(int val) {
        i = val;
    }
    public IAssert isEqualTo(int i2) {
        if (i != i2) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public IAssert isLessThan(int i2) {
        if (i >= i2) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public IAssert isGreaterThan(int i2) {
        if (i <= i2) {
            throw new IllegalArgumentException();
        }
        return this;
    }

}

