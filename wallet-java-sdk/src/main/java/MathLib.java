import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MathLib {
    private static String LIB_NAME = "math";

    static {
        InputStream is = MathLib.class.getClassLoader().getResourceAsStream(System.mapLibraryName(LIB_NAME));
        try {
            Loader.loadFromStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Lib LoadMathLib() {
        MathLib.Lib math = Native.load(LIB_NAME, MathLib.Lib.class);
        return math;
    }

    public static class GoSlice extends Structure {
        public static class ByValue extends GoSlice implements Structure.ByValue {
        }

        public Pointer data;
        public long len;
        public long cap;

        protected List getFieldOrder() {
            return Arrays.asList(new String[]{"data", "len", "cap"});
        }
    }

    // GoString class maps to:
    // C type struct { const char *p; GoInt n; }
    public static class GoString extends Structure {
        public static class ByValue extends GoString implements Structure.ByValue {
        }

        public String p;
        public long n;

        protected List getFieldOrder() {
            return Arrays.asList(new String[]{"p", "n"});
        }
    }



    public static class GoError extends Structure {
        public static class ByReference extends GoError implements Structure.ByValue{
        }

        public Pointer error;
        public long code;

        protected List getFieldOrder() {
            return Arrays.asList(new String[]{"error", "code"});
        }
    }


    public static GoString.ByValue newString(String value) {
        GoString.ByValue result = new GoString.ByValue();
        result.p = value;
        result.n = value.getBytes().length;
        return result;
    }


    public interface Lib extends Library {
        long Multiply(long x, long y);

        Pointer Hello(GoString.ByValue name);

        Pointer Hello2(GoString.ByValue name);

        void Sort(GoSlice.ByValue vals);

        void FreeCs(Pointer p);

        void PrintCs(Pointer p);

        GoError.ByReference Error();
    }
}
