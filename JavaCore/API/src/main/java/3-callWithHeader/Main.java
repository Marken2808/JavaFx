import models.Account;
import models.Feed;
import repositories.AccountRepo;
import repositories.FeedRepo;

public class Main {

    public static void main(String[] args) throws Exception {
        Account account = new AccountRepo().fetch_account();
        System.out.println(account.getAccountUid());
        Feed feed = new FeedRepo().fetch_feed(account);
    }
}
