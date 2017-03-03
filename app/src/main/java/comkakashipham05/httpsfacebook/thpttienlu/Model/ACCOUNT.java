package comkakashipham05.httpsfacebook.thpttienlu.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 02/02/2017.
 */

public class ACCOUNT implements Serializable {
    public String user, pass;

    public ACCOUNT() {
    }

    public ACCOUNT(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }
}
