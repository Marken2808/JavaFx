package Luno;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class luno
{

    public static boolean check (String str) {

        ArrayList<String> setBracket = new ArrayList(Arrays.asList("(",")"));
        for (int i=0; i<str.length(); i++){
            if(setBracket.contains(String.valueOf(str.charAt(i)))
                    && String.valueOf(str.charAt(0)).equals("(")){

                System.out.println(str.charAt(i));

                return true;    // not cover all case
            }
        }


        return false;
    }

    public static void main (String[] args)
    {
        System.out.println(check("((hello))(world)"));

    }
}