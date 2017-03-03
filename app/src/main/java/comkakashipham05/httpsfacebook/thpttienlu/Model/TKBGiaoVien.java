package comkakashipham05.httpsfacebook.thpttienlu.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 06/02/2017.
 */

public class TKBGiaoVien implements Serializable {
    private String Giaovien,thu2,thu3,thu4,thu5,thu6,thu7,noteGiaovien;

    public TKBGiaoVien() {
    }

    public TKBGiaoVien(String giaovien, String thu2, String thu3, String thu4, String thu5, String thu6, String thu7, String noteGiaovien) {
        Giaovien = giaovien;
        this.thu2 = thu2;
        this.thu3 = thu3;
        this.thu4 = thu4;
        this.thu5 = thu5;
        this.thu6 = thu6;
        this.thu7 = thu7;
        this.noteGiaovien = noteGiaovien;
    }

    public String getGiaovien() {
        return Giaovien;
    }

    public void setGiaovien(String giaovien) {
        Giaovien = giaovien;
    }

    public String getThu2() {
        return thu2;
    }

    public void setThu2(String thu2) {
        this.thu2 = thu2;
    }

    public String getThu3() {
        return thu3;
    }

    public void setThu3(String thu3) {
        this.thu3 = thu3;
    }

    public String getThu4() {
        return thu4;
    }

    public void setThu4(String thu4) {
        this.thu4 = thu4;
    }

    public String getThu5() {
        return thu5;
    }

    public void setThu5(String thu5) {
        this.thu5 = thu5;
    }

    public String getThu6() {
        return thu6;
    }

    public void setThu6(String thu6) {
        this.thu6 = thu6;
    }

    public String getThu7() {
        return thu7;
    }

    public void setThu7(String thu7) {
        this.thu7 = thu7;
    }

    public String getNoteGiaovien() {
        return noteGiaovien;
    }

    public void setNoteGiaovien(String noteGiaovien) {
        this.noteGiaovien = noteGiaovien;
    }
}
