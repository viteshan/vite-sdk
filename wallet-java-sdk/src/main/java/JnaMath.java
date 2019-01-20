import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class JnaMath {

    public static void main(String[] args) {
        MathLib.Lib math = MathLib.LoadMathLib();
        System.out.println(math.Multiply(2, 7));
        Pointer p1 = math.Hello(MathLib.newString("viteshan"));
        System.out.println(p1.getString(0));
        Pointer p2 = math.Hello2(MathLib.newString("viteshan"));
        System.out.println(p2.getString(0));
        System.out.println(p2);

        math.FreeCs(p1);
        math.FreeCs(p2);

        System.out.println(p1.getString(0));
        System.out.println(p2.getString(0));

        math.PrintCs(p1);
        math.PrintCs(p2);

        // Call Sort
        // First, prepare data array
        long[] nums = new long[]{53, 11, 5, 2, 88};


        Memory arr = new Memory(nums.length * Native.getNativeSize(Long.TYPE));


        arr.write(0, nums, 0, nums.length);
        // fill in the GoSlice class for type mapping
        MathLib.GoSlice.ByValue slice = new MathLib.GoSlice.ByValue();
        slice.data = arr;
        slice.len = nums.length;
        slice.cap = nums.length;
        math.Sort(slice);
        System.out.print("awesome.Sort(53,11,5,2,88) = [");

        long[] sorted = slice.data.getLongArray(0, nums.length);
        for (int i = 0; i < sorted.length; i++) {
            System.out.print(sorted[i] + " ");
        }
        System.out.println("]");


//        Pointer error = math.Error();
//        String str = error.getString(0);
//        System.out.println(str);
//
//        System.out.println(Native.POINTER_SIZE);
//        for (int i = 0; i < 100; i++) {
//            System.out.println(i + "::" + error.getString(i));
//        }


        MathLib.GoError.ByReference error = math.Error();
        System.out.println(error.error.getString(0));
        math.PrintCs(error.error);
        math.FreeCs(error.error);


        System.out.println(error.error.getString(0));
        math.PrintCs(error.error);
        System.out.println(error.code);
    }
}
