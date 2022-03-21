import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class wise
{
    public static int checkIndexValue(int indexKey) {
        int indexValue = 0;
        switch (indexKey) {
            case 1:
                indexValue = 7;
                break;
            case 2:
                indexValue = 3;
                break;
            case 3:
            case 9:
                indexValue = 1;
                break;
            case 4:
            case 11:
                indexValue = 5;
                break;
            case 5:
                indexValue = 2;
                break;
            case 6:
                indexValue = 4;
                break;
            case 7:
                indexValue = 8;
                break;
            case 8:
            case 10:
                indexValue = 6;
                break;
        }
        return indexValue;
    }

    public static int checkLetterValue(String letterKey){
        int letterValue = 10;
        Map<String,Integer> map = new HashMap<>();
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            map.put(String.valueOf(letter), letterValue++);
        }
//        System.out.println(map);
//        System.out.println(map.get(letterKey));
        return map.get(letterKey);
    }

    public static String checkEvenOrOdd(int sum) {
        int num = 0;
        if(sum%2==0){
            num = (sum%89);
        } else {
            num = (89-(sum-89)%89);
        }

        return String.valueOf(num).length() < 2 ? ("0"+(num)) : String.valueOf(num);
    }

    public static String checkDetailsAreValid(String accountNumber, String bankCode) {

        if(!accountNumber.contains("-") || (accountNumber.split("-")[0]).length() >2) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder((accountNumber.split("-")[1]));
            sb.append(bankCode);
            String[] strArr = sb.toString().replaceAll("\\s+","").split("");
            System.out.println(Arrays.toString(strArr));

            int sum= 0;
            for (int i = 0; i < strArr.length; i++) {
                int num =0;
                if(i==7 || i==8){
                    num=checkLetterValue(strArr[i]);
                } else {
                    num=Integer.valueOf(strArr[i]);
                }
//            System.out.println("num: " + num + " && index: " +(i+1));
                sum+=num*checkIndexValue(i+1);
            }
            System.out.println(checkEvenOrOdd(sum));
            return checkEvenOrOdd(sum).isEmpty() ? null : checkEvenOrOdd(sum);
        }


    }

    public static void main (String[] args)
    {
        checkDetailsAreValid("1234567","AB12");

    }
}