import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdStats;

public class CouponCollectorStats {
    double[] timings;
    public static void main(String[] args) {
        int N = 100000;
        int[] times = {10, 100, 1000};
        CouponCollectorStats coupon = new CouponCollectorStats();
        StdOut.println("N\tT\tmean\t\tstdev");
        for (int T : times){
            coupon.CouponCollectorStats(N, T);
            StdOut.println(String.format("%d\t%d\t%.6f\t%.6f", N, T, coupon.mean(), coupon.stddev()));
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
        int collected = 0, totalGenerated = 0;
        int[] visited = new int[N];
        while (collected < N){
            int num = StdRandom.uniformInt(N);
            if (visited[num] == 0) {
                collected++;
                visited[num] = 1;
            }
            totalGenerated++;
        }
        return totalGenerated;
    }

    /**
     * Count the time it takes to run couponCollectorTest and save it in an array.
     * @param N value of N for couponCollectorTest
     * @param T iterations to run
     */
    public void CouponCollectorStats(int N, int T) {
        timings = new double[T];
        for (int i = 0; i < T; i++){
            Stopwatch watch = new Stopwatch();
            couponCollectorTest(N);
            double time = watch.elapsedTime();
            timings[i] = time;
        }
    }

    /**
     * Calculate the mean of the stats array.
     * @return the mean value of the stats array
     */
    public double mean() {
        return StdStats.mean(timings);
    }

    /**
     * Calculate the standard deviation of the stats array.
     * @return the standard deviation of the stats array
     */
    public double stddev() {
        return StdStats.stddev(timings);
    }
}
