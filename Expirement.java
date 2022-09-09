import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Expirement {
    public static void main(String[] args) {
        int N = 100000;
        for (int i = 0; i < 3; i++){
            Stopwatch watch = new Stopwatch();
            couponCollectorTest(N);
            double time = watch.elapsedTime();
            StdOut.println(time);
        }

        N = 1000000;
        for (int i = 0; i < 3; i++){
            Stopwatch watch = new Stopwatch();
            couponCollectorTest(N);
            double time = watch.elapsedTime();
            StdOut.println(time);
        }
    }

    /**
     * Count how often random is called until all numbers
     * in the range N have appeared.
     * 
     * @param N upper bound of the range
     * @return the count of how often random was called
     */
    public static int couponCollectorTest(int N) {
        int sm = 0, count = 0;
        int[] vis = new int[N];
        while (sm != N){
            int num = StdRandom.uniformInt(N);
            if (vis[num] == 0) {
                sm++;
                vis[num] = 1;
            }
            count++;
        }
        return count;
    }
}