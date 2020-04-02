public class BAssert {
    private static boolean b;
    public BAssert(boolean val) {
        b = val;
    }
    public BAssert isEqualTo(boolean b2) {
        if (b != b2) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public BAssert isTrue() {
        if (!b){
            throw new IllegalArgumentException();
        }
        return this;
    }
    public BAssert isFalse() {
        if (b){
            throw new IllegalArgumentException();
        }
        return this;
    }
}
