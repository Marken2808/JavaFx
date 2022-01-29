public class Main {

    public static void main(String[] args) {

        int[] amounts = new int[] {435,520,87};
        double saved = 0;
        for (int amount: amounts ) {
            System.out.println(diff(amount));
            saved += diff(amount);
        }
        System.out.println(saved/100);
    }

    public static int diff(double amount) {
        return  (int) (Math.ceil(amount/100)*100 - amount);
    }
}
