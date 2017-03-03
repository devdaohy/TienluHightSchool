package comkakashipham05.httpsfacebook.thpttienlu.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 30/01/2017.
 */
public class TKB implements Serializable {
    public String Chunhiem,Lop,thu2,thu3,thu4,thu5,thu6,thu7;

    public TKB() {
    }

    public TKB(String chunhiem, String lop, String thu2, String thu3, String thu4, String thu5, String thu6, String thu7) {
        Chunhiem = chunhiem;
        Lop = lop;
        this.thu2 = thu2;
        this.thu3 = thu3;
        this.thu4 = thu4;
        this.thu5 = thu5;
        this.thu6 = thu6;
        this.thu7 = thu7;
    }
}