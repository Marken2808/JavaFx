package zzz;

import java.math.BigDecimal;
import java.util.Arrays;

class test
{

    public static void main (String[] args)
    {
        BigDecimal bd1 = new BigDecimal(0.01);
        BigDecimal bd2 = BigDecimal.valueOf(0.01);
        System.out.println("BigDecimal bd1 = " + bd1);
        System.out.println("Double bd2 = " + bd2);

    }
}