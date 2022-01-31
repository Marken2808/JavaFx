import models.Account;
import models.Feed;
import repositories.AccountRepo;
import repositories.FeedRepo;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int diff(double amount) {
        return  (int) (Math.ceil(amount/100)*100 - amount);
    }

    public static void roundup(List<Long> units){
        double saved = 0;
        for (long unit: units ) {
            System.out.println((unit));
            saved += diff(unit);
        }
        System.out.println("Total: " + saved/100);
    }

    public static void main(String[] args) throws Exception {
        Account account = new AccountRepo().fetch_account();
        List<Feed> feeds = new FeedRepo().fetch_feed(account);
        List<Long> units = new ArrayList<>();
        feeds.forEach(feed -> {
            units.add(feed.getMinorUnits());
        });
        roundup(units);
    }
}
