package comkakashipham05.httpsfacebook.thpttienlu.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 01/02/2017.
 */

public class ThongBao implements Serializable {
    public String nick,tieude,noidung,thoigian;

    public ThongBao() {
    }

    public ThongBao(String nick, String tieude, String noidung, String thoigian) {
        this.nick = nick;
        this.tieude = tieude;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }
}
