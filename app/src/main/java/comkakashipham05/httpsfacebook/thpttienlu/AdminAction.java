package comkakashipham05.httpsfacebook.thpttienlu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Calendar;

import comkakashipham05.httpsfacebook.thpttienlu.Model.TKB;
import comkakashipham05.httpsfacebook.thpttienlu.Model.TKBGiaoVien;
import comkakashipham05.httpsfacebook.thpttienlu.Model.ThongBao;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class AdminAction extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TextView textHello, titleDialog, txtdate, tieudeDialog, noidungDialog;
    Bundle extras;
    Button addThongbao, btnAdd, addTKB, themTKB, btnChon, doituongTKB, btnok, btntrolai;
    Dialog dialog, dialogthemTKB, dialogXacnhan;
    EditText tieude, noidung, linkPath;
    Calendar calendar;
    String link, doituong;
    int mYear, mMonth, mDay;
    Boolean doituongHocsinh;
    String[] mang = new String[1000];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_action);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        connectview();
        loadtimeDialog();
        khoitaodialogXacnhandialog();
        khoitaodialogthemTKB();
        extras = getIntent().getExtras();
        textHello.setText("Xin chào " + extras.getString("USER"));
        clickView();


    }

    private void khoitaodialogXacnhandialog() {
        dialogXacnhan = new Dialog(AdminAction.this);
        dialogXacnhan.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogXacnhan.setContentView(R.layout.dialog_xacnhan);
        tieudeDialog = (TextView) dialogXacnhan.findViewById(R.id.tieudeDialog);
        noidungDialog = (TextView) dialogXacnhan.findViewById(R.id.noidungDialog);
        btnok = (Button) dialogXacnhan.findViewById(R.id.btnok);
        btntrolai = (Button) dialogXacnhan.findViewById(R.id.btntrolai);
        tieudeDialog.setText("Xác nhận hành động :");
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.65);
        dialogXacnhan.getWindow().setLayout(width, height);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("TKB Start").setValue(txtdate.getText().toString());
                dialogXacnhan.dismiss();
                dialogthemTKB.dismiss();
                if (doituongHocsinh == true) {
                    loadexcelHocSinh();
                } else {
                    loadexelGiaoVien();
                }
                Toast.makeText(AdminAction.this, "Cập nhật thời khóa biểu thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btntrolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogXacnhan.dismiss();
            }
        });

    }

    private void loadtimeDialog() {
        mYear = calendar.getInstance().get(calendar.YEAR);
        mMonth = calendar.getInstance().get(calendar.MONTH);
        mDay = calendar.getInstance().get(calendar.DAY_OF_MONTH);
    }

    private void khoitaodialogthemTKB() {
        doituongHocsinh = true;
        dialogthemTKB = new Dialog(AdminAction.this);
        dialogthemTKB.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthemTKB.setContentView(R.layout.dialog_addtkb);
        titleDialog = (TextView) dialogthemTKB.findViewById(R.id.titleDialog);
        doituongTKB = (Button) dialogthemTKB.findViewById(R.id.doituongTKB);
        doituongTKB.setText("Dành cho học sinh");
        themTKB = (Button) dialogthemTKB.findViewById(R.id.themTKB);
        btnChon = (Button) dialogthemTKB.findViewById(R.id.btnChon);
        txtdate = (TextView) dialogthemTKB.findViewById(R.id.txtdate);
        txtdate.setText(String.valueOf(mDay) + "/" + String.valueOf(mMonth + 1) + "/" + String.valueOf(mYear));
        linkPath = (EditText) dialogthemTKB.findViewById(R.id.linkPath);
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();

            }
        });

        doituongTKB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doituongHocsinh == true) {
                    doituongTKB.setText("Dành cho giáo viên");
                    doituongHocsinh = false;
                } else {
                    doituongTKB.setText("Dành cho học sinh");
                    doituongHocsinh = true;
                }

            }
        });

        themTKB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogXacnhan.show();
                if (doituongHocsinh == true) {
                    doituong = "Học Sinh";
                } else {
                    doituong = "Giáo Viên";
                }
                noidungDialog.setText("Bạn có chắc chắn thêm thời khóa biểu dành cho: " + doituong + "\n" + "Với đường dẫn file: " + linkPath.getText().toString() + "\n\n" + "Hãy kiểm tra thật kĩ trước khi update thời khóa biểu, thời khóa biểu phải phù hợp mẫu chung của trường THPT Tiên Lữ và của từng đối tượng, nếu file không phù hợp sẽ dẫn đến lỗi ứng dụng.\nXin thật lưu ý ! ");

            }
        });

        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialogdate = new DatePickerDialog(AdminAction.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                txtdate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                dialogdate.show();
            }
        });
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.75);
        dialogthemTKB.getWindow().setLayout(width, height);
    }


    private void clickView() {
        addThongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemThongBao();
                dialog.show();

            }
        });

        addTKB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleDialog.setText("Update thời khóa biểu");
                dialogthemTKB.show();
            }
        });


    }


    private void openFolder() {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn File thời khóa biểu"), 1001);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String typeFile = getMimeType(data.getData().toString());//doan nay no ko split duoc la sao ta
        if (requestCode == 1001 && data != null && typeFile.equals("application/vnd.ms-excel")) {
            Toast.makeText(this, "Đã chọn file thành công", Toast.LENGTH_SHORT).show();
            Uri uri = data.getData();
            link = uri.getPath();
            linkPath.setText(link);

            Log.e("link", link);
        } else {
            Toast.makeText(this, "File không được hỗ trợ", Toast.LENGTH_SHORT).show();
        }

    }




    private void connectview() {
        textHello = (TextView) findViewById(R.id.textHello);
        addThongbao = (Button) findViewById(R.id.addThongbao);
        addTKB = (Button) findViewById(R.id.addTKB);

    }

    private void dialogThemThongBao() {
        dialog = new Dialog(AdminAction.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_thongbao);
        tieude = (EditText) dialog.findViewById(R.id.tieude);
        noidung = (EditText) dialog.findViewById(R.id.noidung);
        btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.65);
        dialog.getWindow().setLayout(width, height);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            int number1=0;
            @Override
            public void onClick(View v) {
                ThongBao thongbao = new ThongBao(extras.getString("USER"), tieude.getText().toString(), noidung.getText().toString(), getTime());
                mDatabase.child("Thông Báo").push().setValue(thongbao);
//                Toast.makeText(AdminAction.this, "Thêm thông báo thành công", Toast.LENGTH_SHORT).show();
                mDatabase.child("Kiem tra notifications").setValue(getTime());
                dialog.dismiss();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
            try{
                Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            }
            catch(NoSuchMethodException e){
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.dangnhap){
            Intent intent = new Intent(AdminAction.this,AdminMode.class);
            startActivity(intent);
        }

        if (id == R.id.dangki){
            Intent intent = new Intent(AdminAction.this,SignUP.class);
            startActivity(intent);
        }
        if(id ==R.id.setting)
        {
            Intent intent = new Intent(AdminAction.this,setting.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }


    private String getTime() {
        String Nam = String.valueOf(calendar.getInstance().get(calendar.YEAR));
        String Thang = String.valueOf(calendar.getInstance().get(calendar.MONTH) + 1);
        String Ngay = String.valueOf(calendar.getInstance().get(calendar.DAY_OF_MONTH));
        String Gio = String.valueOf(calendar.getInstance().get(calendar.HOUR_OF_DAY));
        String Phut = String.valueOf(calendar.getInstance().get(calendar.MINUTE));
        String Time = Gio + ":" + Phut + " , " + Ngay + "/" + Thang + "/" + Nam;
        return Time;
    }

    public String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    private void loadexcelHocSinh() {
        try {
            File dir = new File(link);
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("Cp1252");
            Workbook workbook = Workbook.getWorkbook(dir, workbookSettings);
            Sheet sheet = workbook.getSheet(0);
            int row = sheet.getRows();
            for (int i = 2; i <= row - 2; i++) {
                String txtlop = "";
                txtlop = sheet.getCell(i, 2).getContents();
                String txtthu2 = "";
                String txtthu3 = "";
                String txtthu4 = "";
                String txtthu5 = "";
                String txtthu6 = "";
                String txtthu7 = "";
                String chunhiem = "";
                for (int j = 3; j <= 7; j++) {
                    Cell cell = sheet.getCell(i, j);
                    if (j < 7) {
                        txtthu2 = txtthu2 + cell.getContents() + ", ";
                    } else {
                        txtthu2 = txtthu2 + cell.getContents();
                    }
                }

                for (int j = 9; j <= 13; j++) {
                    Cell cell = sheet.getCell(i, j);
                    if (j < 13) {
                        txtthu3 = txtthu3 + cell.getContents() + ", ";
                    } else {
                        txtthu3 = txtthu3 + cell.getContents();
                    }
                }

                for (int j = 15; j <= 19; j++) {
                    Cell cell = sheet.getCell(i, j);
                    if (j < 19) {
                        txtthu4 = txtthu4 + cell.getContents() + ", ";
                    } else {
                        txtthu4 = txtthu4 + cell.getContents();
                    }
                }

                for (int j = 21; j <= 25; j++) {
                    Cell cell = sheet.getCell(i, j);
                    if (j < 25) {
                        txtthu5 = txtthu5 + cell.getContents() + ", ";
                    } else {
                        txtthu5 = txtthu5 + cell.getContents();
                    }
                }

                for (int j = 27; j <= 31; j++) {
                    Cell cell = sheet.getCell(i, j);
                    if (j < 31) {
                        txtthu6 = txtthu6 + cell.getContents() + ", ";
                    } else {
                        txtthu6 = txtthu6 + cell.getContents();
                    }
                }

                for (int j = 33; j <= 37; j++) {
                    Cell cell = sheet.getCell(i, j);
                    if (j < 37) {
                        txtthu7 = txtthu7 + cell.getContents() + ", ";
                    } else {
                        txtthu7 = txtthu7 + cell.getContents();
                    }
                }

                chunhiem = sheet.getCell(i, 38).getContents();
                TKB tkb = new TKB(chunhiem, txtlop, txtthu2, txtthu3, txtthu4, txtthu5, txtthu6, txtthu7);

                mDatabase.child("TKBHocSinh").child(txtlop).setValue(tkb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mDatabase.child("Notifi").child("NotifiHocsinh").setValue("ON");
    }


    private void loadexelGiaoVien() {
        try {
            File file = new File(link);
            FileInputStream is = new FileInputStream(file);
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("Cp1252");
            Workbook workbook = Workbook.getWorkbook(is, workbookSettings);
            Sheet sheet = workbook.getSheet(0);
            int row = sheet.getRows();
            int hangMain = -9;
            int dem = 0;

            while (hangMain <= row) {
                ++dem;
                hangMain = hangMain + 9;
                String GiaoVien = "";
                GiaoVien = sheet.getCell(0, hangMain).getContents().toString();
                for (int cot = 1; cot <= 6; cot = cot + 1) {
                    String tam = "";
                    for (int hang = hangMain + 2; hang <= hangMain + 6; hang = hang + 1) {
                        if ((sheet.getCell(cot, hang).getContents().toString()).compareTo("") > 0) {
                            tam = tam + "( Tiết " + String.valueOf(hang - hangMain - 1) + ")" + sheet.getCell(cot, hang).getContents() + ", ";
                        }

                    }
                    if ("".equals(tam)) {
                        mang[cot] = "Không có tiết nào ";
                    } else {
                        mang[cot] = tam;
                    }
                }
                String noteGiaovien = sheet.getCell(0, hangMain + 7).getContents();
                TKBGiaoVien tkb = new TKBGiaoVien(GiaoVien, mang[1], mang[2], mang[3], mang[4], mang[5], mang[6], noteGiaovien);
                mDatabase.child("TKBGiaoVien").child(String.valueOf(dem)).setValue(tkb);
            }
        } catch (Exception e) {

        }
        mDatabase.child("Notifi").child("NotifiGiaovien").setValue("ON");
    }


}




