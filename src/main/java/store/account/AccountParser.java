package store.account;

import java.util.List;

import store.account.AccountIn;
import store.account.AccountOut;

public class AccountParser {

    public static AccountOut to(Account in){
        return in == null ? null :
            AccountOut.builder()
                .id(in.id())
                .name(in.name())
                .email(in.email())
                .build();
    }

    public static List<AccountOut> to(List<Account> in) {
        return in.stream().map(AccountParser::to).toList();
    }

    public static Account to(AccountIn in){
        return in == null ? null:
            Account.builder()
                .name(in.name())
                .email(in.email())
                .password(in.password())
                .build();
    }   
}
