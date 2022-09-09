# D1 write-up

All the ocde that is written below can be found in the java files in this repository

## Programming the coupon collector problem

First we read the task description and try to figure out what we need to do:

"Write a method static `int couponCollectorTest(int N)` that performs one such experiment. There are **N** „cards“ to be collected, which we can number as **0** to **N-1** You want to generate random integers in that range until all **N** numbers have appeared. The method outputs how many numbers were generated in the process (in total)."

Okay, so we want to randomly pick a card from **0** to **N-1** until all cards in that range have been seen.

First we need to find a way to keep track of what cards we have seen. Since the cards are numbered from **0** to **N - 1**, then thinking about an array of size **N** where each index represents their respective card could be one way of storing it. So index **0** is card **0**, index **1** is card **1**, etc.

In java we can do that by initializing an array of size **N** as follows
```java
int[] seen = new int[N];
```
`int[]` in the beginning means that this is an integer array, `seen` is the name of the array and `new int[N]` is saying that this array should be of size **N**.

Now to represent if the card has been seen, we can use the values; **0** if it's not been seen, **1** if it has. So we want all the values to be **0** initially. Now after a quick google search on how to set all the values to **0** we find out that when generating an array like shown above, java guarantees that all the values will be set to **0** initally, so we don't have to do anything about that.

Now next we want to generate a random number and check if it has been seen before, one way of doing it is the following.

```java
random_number = StdRandom.uniformInt(N);
if (seen[random_number] == 0){
    // Not been seen so we set it so it has been seen
    seen[random_number] = 1;
} else {
    // Do nothing since we have already seen it
}
```

The function `StdRandom.uniformInt(N)` is provided in the algs4 library and gives us a random number between **0** and **N-1** inclusive.

Now we just need to repeat this until all cards have been found, or in other words, all values in the array are set to **1**.

Here we have a new problem, how do we know if all the values in the array are set to **1**. Well one way would be to go through each value and check if it is set to **1**, but that is kinda slow since because each time we generate a new number we would have to go through the array and check if all values are set to **1**.

Now we can observe that since all the values are **0** initially and only when they show up the first time we will go into the `if` clause, since in there they are set to **1** and nothing changes it back. So we could instead of going through the array each time, we only will need a counter variable saying how often we have gone into the `if` clause and when that counter reaches **N**, well then we must have entered the `if` clause **N** times and thus have found all our cards.

One way to program it is like the following.

```java
public static int couponCollectorTest(int N) {
    int seen_count = 0, random_count = 0;
    int[] vis = new int[N];
    while (seen_count != N){
        int num = StdRandom.uniformInt(N);
        if (vis[num] == 0) {
            seen_count++;
            vis[num] = 1;
        }
        random_count++;
    }
    return random_count;
}
```

Here `seen_count` is counting how often we have gone into the `if` clause, `random_count` is counting how often we have called `StdRandom.uniformInt(N)` and then finally after all have been seen we return the number of random calls.

So now we have programmed the coupon collector problem

## Little about java classes

Each class in java can have methods (the class functions) and varaibles, they can be `public` or `private`. When a method or variable is `private` only the class itself should access it. Now `public` methods or variables on the other hand are to be used by anyone. (This is the same as using **_** infront of the class method or variable in python)

Defined as in the following example:

```java
public class Example{
    private int private_variable = 1;
    public int public_variable = 2;

    public int public_function(){
        private_variable -= 1;
        return private_variable;
    }

    private int private_function(){
        private_variable += 1;
        return private_variable;
    }
}
```

## Finishing the expirment
### CouponCollectorStats
Now we want to create the class to run and test our problem.

When we look at the api we should create, what it is telling us is which functions should be public, note that we can still make private functions since they are not supposed to be exposed.
```java
public class CouponCollectorStats  {
  public CouponCollectorStats(int N, int T)
  public double mean()
  public double stddev()
}
```

First let's implement the `CouponCollectorStats(int N, int T)` function. There we are supposed to run our `couponCollectorTest` function `T` times with the value of `N` in each of those loops and count the time it takes for the function our `couponCollectorTest` function to run.

To count the time we'll use the `Stopwatch` algs4 library, to initialize the class we do the following

```java
Stopwatch watch = new Stopwatch();
```

The first `Stopwatch` means that this variable is of type `Stopwatch`. Next there is the `watch`, this is the variable itself. Lastly the `new Stopwatch();`, here we are initializing a new instance of the class `Stopwatch`. So all in all we are creating a variable called `watch` of type `Stopwatch` and then setting it as a new instance of the class `Stopwatch`.

Now we'll write our `CouponCollectorStats` function.

```java
public void CouponCollectorStats(int N, int T) {
    timings = new double[T];
    for (int i = 0; i < T; i++){
        Stopwatch watch = new Stopwatch();
        couponCollectorTest(N);
        double time = watch.elapsedTime();
        timings[i] = time;
    }
}
```

Firstly you can see a variable `timings` which is a global array of type `double`. The type `double` is how we store floating point numbers with a precision of 15-16 digits. At the start of the function we are reseting this variable to be an empty `double` array of size `T`.

Then we create a for loop that will cal our function `T` times and take the time of each call. To get the time we call the `elapsedTime` method on the `Stopwatch` class, this method returns the time since the class was initialized.

Lastly we save our result into our `timings` array.

### mean

Now onto the `mean` method, we are given that the algs4 library has an inbuilt class `StdStats` which has the method, so we'll just use that.

Via a quick lookup, we can find out that the `StdStats.mean` function takes in an argument, a list of type `double` and returns the mean.

So the code is quite simple
```java
public double mean() {
    return StdStats.mean(timings);
}
```

### stddev

Similar like with `mean` the algs4 libaray has a standard deviation function also in the `StdStats` class.

Via another quick lookup, we can see that it takes an array in as well and returns the standard deviation of it

```java
public double stddev() {
    return StdStats.stddev(timings);
}
```

## How to call it all

Now since we have everything setup and ready, we'll need to call our code. For that we'll create a main function, which is the function that'll be executed when you run your program.

We are supposed to run our code with `N = 100000` and `T` as `10`, `100` and `1000`

So our main function could look something like this

```java
public static void main(String[] args) {
    int N = 100000;
    int[] times = {10, 100, 1000};
    CouponCollectorStats coupon = new CouponCollectorStats();
    StdOut.println("N\tT\tmean\t\tstdev");
    for (int T : times){
        coupon.CouponCollectorStats(N, T);
        StdOut.printf("%d\t%d\t%.6f\t%.6f\n", N, T, coupon.mean(), coupon.stddev());
    }
}
```

First we set `N = 100000`, next we create an `int` array `times` where we'll keep our values for `T` (To set a default value of an array use `{}` around the values).

Now a small funky thing, java uses the `static` keyword to be able to access a method with creating an instance of the object first and because of that a `static` method can not directly call a non-static method. So first we need to create an instance of our class.

```java
CouponCollectorStats coupon = new CouponCollectorStats();
```

The weird println is creating a nice header for our output, where the `\t` is a tab character, similiar to using a normal space character, except tab characters start at fixed intervals.

Finally we go through our times array, which is storing our `T` values.
```java
for (int T : times){
    coupon.CouponCollectorStats(N, T);
    StdOut.printf("%d\t%d\t%.6f\t%.6f\n", N, T, coupon.mean(), coupon.stddev());
}
```

The for loop may seem a bit weird on how it is used, but this is how you can go through each value of an array (the same as doing `for T in times` in python). Then we just call our function and print the responses.

`printf` is another way to print things in java and it gives us a freedom on how to format the output, I'd recomend reading this article if you'd want to learn more about how it works https://en.wikipedia.org/wiki/Printf_format_string.

Thus completing our task. The final code for this task can be found [here](CouponCollectorStats.java)
