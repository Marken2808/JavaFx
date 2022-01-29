package DigitalFuture;

import java.math.BigDecimal;
import java.util.Scanner;

class DigitalFuture
{

    static int fib(int n)
    {
        if (n <= 1)
            return n;
        return fib(n-1) + fib(n-2);
    }

    public static void main (String args[])
    {
        int n = 8;
        System.out.println(fib(n));
    }
//    public static int  productsAtNegativeTemp(int[] temparature)
//    {
//        int  answer = 0;
//        // Write your code here
//        for(int temp: temparature){
//            if(temp<0) {answer++;}
//        }
//
//        return answer;
//    }
//
//    public static void main(String[] args)
//    {
//        Scanner in = new Scanner(System.in);
//        //input for temparature
//        int temparature_size = in.nextInt();
//        int temparature[] = new int[temparature_size];
//        for(int idx = 0; idx < temparature_size; idx++)
//        {
//            temparature[idx] = in.nextInt();
//        }
//
//        int result = productsAtNegativeTemp(temparature);
//        System.out.print(result);
//
//    }
}





