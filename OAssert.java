import java.lang.reflect.*;
public class OAssert {
    private static Object o;
    public OAssert(Object val) {
        o = val;
    }
    public OAssert isNotNull() {
        if (o == null) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public OAssert isNull() {
        if (o != null) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public OAssert isEqualTo(Object o2) {
        if (o != o2) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public OAssert isNotEqualTo(Object o2) {
        if (o == o2) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public OAssert isInstanceOf(Class c) {
        if (o.getClass() != c) {
            throw new IllegalArgumentException();
        }
        return this;
    }

}
