public class SuperPrimeCounter {

    public static void main(String[] args) {
        int n = 111;
        int result = countSuperPrimes(n);
        System.out.println("Number of super easy numbers " + n + ": " + result);
    }

    public static int countSuperPrimes(int n) {
        if (n < 1 || n > 1000) {
            throw new IllegalArgumentException("Number must be between 1 and 1000.");
        }

        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (isSuperPrime(i)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isSuperPrime(int num) {
        if (!isPrime(num)) {
            return false;
        }
        int reversed = reverseNumber(num);
        return isPrime(reversed);
    }

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; (long) i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static int reverseNumber(int num) {
        int reversed = 0;
        int original = num;
        while (original > 0) {
            reversed = reversed * 10 + original % 10;
            original /= 10;
        }
        return reversed;
    }
}