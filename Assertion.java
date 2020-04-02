public class Assertion {

    static OAssert assertThat(Object o) {
        OAssert obj = new OAssert(o);
        return obj;
    }
    static TAssert assertThat(String s) {
	    TAssert obj = new TAssert(s);
	    return obj;
    }
    static BAssert assertThat(boolean b) {
        BAssert obj = new BAssert(b);
        return obj;
    }
    static IAssert assertThat(int i) {
        IAssert obj = new IAssert(i);
        return obj;
    }

}