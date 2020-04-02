public class TAssert {
    private static String s;

    public TAssert(String val) {
        s = val;
    }

    public TAssert isNotNull() {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public TAssert isNull() {
        if (s != null) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public TAssert isEqualTo(Object o2) {
        if (s != o2) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public TAssert isNotEqualTo(Object o2) {
        if (s == o2) {
            throw new IllegalArgumentException();
        }
        return this;
    }

    public TAssert startsWith(String s2) {
        if (!s.startsWith(s2)) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public TAssert isEmpty() {
        if (s != "") {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public TAssert contains(String s2) {
        if (!s.contains(s2)) {
            throw new IllegalArgumentException();
        }
        return this;
    }
}
