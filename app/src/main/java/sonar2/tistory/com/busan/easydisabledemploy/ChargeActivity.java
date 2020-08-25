package sonar2.tistory.com.busan.easydisabledemploy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;

import pl.polidea.view.ZoomView;


public class ChargeActivity extends AppCompatActivity {

    public double MINUMUMWAGE = 0;   // 최저임금 2020=1795310
    public double BASISAMOUNT = 0;    // 기초액   2020=1078000


    public double getMINUMUMWAGE() {
        return MINUMUMWAGE;
    }

    public void setMINUMUMWAGE(double MINUMUMWAGE) {
        this.MINUMUMWAGE = MINUMUMWAGE;
    }

    public double getBASISAMOUNT() {
        return BASISAMOUNT;
    }

    public void setBASISAMOUNT(double BASISAMOUNT) {
        this.BASISAMOUNT = BASISAMOUNT;
    }


    private EditText edit1;  // 상시인원
    private EditText edit2;  // 경증
    private EditText edit3;  // 중증

    private EditText editb1;
    private EditText editb2;
    private EditText editb3;

    private EditText editc1;
    private EditText editc2;
    private EditText editc3;

    private TextView text5;
    private TextView text6;
    private TextView text8;
    private TextView text9;
    private TextView text11;
    private TextView text12;   //  연부담금
    private TextView text13;   //  차액

    private TextView textb5;
    private TextView textb6;
    private TextView textb8;
    private TextView textb9;
    private TextView textb11;
    private TextView textb12;   //  연부담금
    private TextView textb13;   //  차액

    private TextView textc5;
    private TextView textc6;
    private TextView textc8;
    private TextView textc9;
    private TextView textc11;
    private TextView textc12;   //  연부담금
    private TextView textc13;   //  차액

    private double rate_1;
    private double rate_2;
    private double rate_3;

    //private List myList;
    private String fileNames = "";
    //String path;  //sdcard 의 경로

    public void saveAllData(String fileName) {
        String EditTextedit1 = edit1.getText().toString();
        String EditTextedit2 = edit2.getText().toString();
        String EditTextedit3 = edit3.getText().toString();
        String str_rate_1 = Double.toString(rate_1);

        String EditTexteditb1 = editb1.getText().toString();
        String EditTexteditb2 = editb2.getText().toString();
        String EditTexteditb3 = editb3.getText().toString();
        String str_rate_2 = Double.toString(rate_2);

        String EditTexteditc1 = editc1.getText().toString();
        String EditTexteditc2 = editc2.getText().toString();
        String EditTexteditc3 = editc3.getText().toString();
        String str_rate_3 = Double.toString(rate_3);


        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "/" + fileName + ".부담금", false));
            //BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + fileName+".txt", true));
            bw.write(EditTextedit1 + "/");
            bw.write(EditTextedit2 + "/");
            bw.write(EditTextedit3 + "/");
            bw.write(str_rate_1 + "/");

            bw.write(EditTexteditb1 + "/");
            bw.write(EditTexteditb2 + "/");
            bw.write(EditTexteditb3 + "/");
            bw.write(str_rate_2 + "/");

            bw.write(EditTexteditc1 + "/");
            bw.write(EditTexteditc2 + "/");
            bw.write(EditTexteditc3 + "/");
            bw.write(str_rate_3 + "/");

            bw.close();

            Toast.makeText(this, fileName + " 저장완료!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickSave(View view) {

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("현 Data를 저장하시겠습니까?");       // 제목 설정
        ad.setMessage("저장할 파일이름을 넣으세요!");   // 내용 설정
        final EditText input = new EditText(ChargeActivity.this);
        ad.setView(input);

        // 확인 버튼 설정
        ad.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = input.getText().toString();
                saveAllData(fileName);
                dialog.dismiss();     //닫기
            }
        });

        // 취소 버튼 설정
        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기
                // Event
            }
        });
        // 창 띄우기
        ad.show();


    }


    public void onClickLoad(View view) {
        final Spinner spinner_Rate_1 = findViewById(R.id.spnR1C2);  // 의무고용율 1
        final Spinner spinner_Rate_2 = findViewById(R.id.spnR2C2);  // 의무고용율 2
        final Spinner spinner_Rate_3 = findViewById(R.id.spnR3C2);  // 의무고용율 3

        String path = getFilesDir().toString();

        //myList = new ArrayList();

        File f = new File(path + "");

        File[] list = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.lastIndexOf(".부담금") > 0;
            }
        });
        assert list != null;
        final CharSequence[] items = new String[list.length];
        for (int i = 0; i < list.length; i++) {
            items[i] = list[i].getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("불러올 파일 선택해주세요");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                fileNames = items[item].toString();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/" + fileNames));

                    StringBuilder readStr = new StringBuilder();
                    String str;
                    while (((str = br.readLine()) != null)) {
                        readStr.append(str).append("\n");
                        //Log.d("디버깅",str);
                    }

                    String[] loadData = readStr.toString().split("/");
                    int cntLoadData = loadData.length;
                    if (cntLoadData > 2) {
                        edit1.setText(loadData[0]);
                        edit2.setText(loadData[1]);
                        edit3.setText(loadData[2]);
                        spinner_Rate_1.setSelection(((ArrayAdapter<String>) spinner_Rate_1.getAdapter()).getPosition(loadData[3] + "%"), true);
                        editb1.setText(loadData[4]);
                        editb2.setText(loadData[5]);
                        editb3.setText(loadData[6]);
                        spinner_Rate_2.setSelection(((ArrayAdapter<String>) spinner_Rate_2.getAdapter()).getPosition(loadData[7] + "%"), true);
                        editc1.setText(loadData[8]);
                        editc2.setText(loadData[9]);
                        editc3.setText(loadData[10]);
                        spinner_Rate_3.setSelection(((ArrayAdapter<String>) spinner_Rate_3.getAdapter()).getPosition(loadData[11] + "%"), true);

                        Toast.makeText(getApplicationContext(), fileNames + " 불러왔습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "file이 존재 하지 않거나 잘못된 파일입니다.", Toast.LENGTH_LONG).show();
                    }
                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "file존재 하지 않습니다.", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        builder.show();

    }

    public void onClickDel(View view) {
        String path = getFilesDir().toString();

        //myList = new ArrayList();

        File f = new File(path + "");

        File[] list = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.equals("config.txt");
            }
        });
        assert list != null;
        final CharSequence[] items = new String[list.length];
        for (int i = 0; i < list.length; i++) {
            items[i] = list[i].getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제할 파일 선택해주세요!!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                fileNames = items[item].toString();
                try {
                    File f = new File(getFilesDir() + "/" + fileNames);
                    if (f.delete())
                        Toast.makeText(getApplicationContext(), fileNames + " 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), fileNames + " 삭제 실패!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }

        });
        builder.show();
    }

    public void onClickConfig(View view) {
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View layout = inflater.inflate(R.layout.config, (ViewGroup) findViewById(R.id.layout_root));


//////////////////   기존의 값을 불러오기 위한 구문 ////////////////
        AlertDialog.Builder aDialog = new AlertDialog.Builder(ChargeActivity.this);//
        aDialog.setTitle("설정값을 입력하세요");
        final CharSequence[] items = new String[1];
        items[0] = "현재 최저임금 값 : " + makeStringComma(formatD(getMINUMUMWAGE())) + " 원" + "\n현재 부담기초액 값 : " + makeStringComma(formatD(getBASISAMOUNT())) + " 원";
        aDialog.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            }
        });

        aDialog.setView(layout);

        if (MINUMUMWAGE == 0) ((EditText) layout.findViewById(R.id.minimumwage)).setText("1795310");
        if (BASISAMOUNT == 0) ((EditText) layout.findViewById(R.id.basisamount)).setText("1078000");
////////////////////////////////////////////////////////////////////////

        aDialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EditText minimumWage = ((EditText) ((AlertDialog) dialog).findViewById(R.id.minimumwage));  // 최저임금
                EditText basisAmount = ((EditText) ((AlertDialog) dialog).findViewById(R.id.basisamount));  // 기초액
                String strMinimumWage = minimumWage.getText().toString();
                String strBasisAmount = basisAmount.getText().toString();
                try {
                    //if(Integer.parseInt(strMinimumWage) < 1000000 || Integer.parseInt(strBasisAmount) < 800000 || strMinimumWage=="" ||strMinimumWage.equals("") || strBasisAmount =="" || strBasisAmount.equals("") ){
                    if ("".equals(strMinimumWage) || strBasisAmount.equals("") || Integer.parseInt(strMinimumWage) < 1000000 || Integer.parseInt(strBasisAmount) < 800000) {
                        Toast.makeText(getApplicationContext(), "금액 입력 값이 옳지 않습니다. 확인 하여 입력하여 주세요", Toast.LENGTH_LONG).show();
                    } else {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "/" + "config.txt"));
                        bw.write(strMinimumWage + "/");  // 최저 임금
                        bw.write(strBasisAmount);  // 기초액

                        bw.close();
                        Toast.makeText(getApplicationContext(), "설정파일 저장완료", Toast.LENGTH_SHORT).show();

                        getConfig();
                        calculate();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        aDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog ad = aDialog.create();
        ad.show();

    }

    public void getConfig() {   // 설정파일 가져오기
        StringBuilder readStr = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/" + "config.txt"));

            String str = null;
            while (((str = br.readLine()) != null)) {
                readStr.append(str).append("\n");
            }
            String[] loadData;
            loadData = readStr.toString().split("/");
            int cntLoadData = loadData.length;
            if (cntLoadData > 1) {
                String tmpMin = loadData[0];
                String tmpBasis = loadData[1];
                if (tmpMin.equals("")) tmpMin = "0";
                if (tmpBasis.equals("")) tmpBasis = "0";
                if (tmpMin.equals("\n") || tmpMin.equals("\\n"))
                    tmpBasis = "0";   // 설정파일에 값이 안들어가면 \n으로 들어와져서 예외처리
                if (tmpBasis.equals("\n") || tmpBasis.equals("\\n")) tmpBasis = "0";

                double tmpDblMin = Double.parseDouble(tmpMin);
                double tmpDblBasis = Double.parseDouble(tmpBasis);
                setMINUMUMWAGE(tmpDblMin);
                setBASISAMOUNT(tmpDblBasis);
            } else {
                setMINUMUMWAGE(0);  // 2017년 기준  최저임금 1352230
                setBASISAMOUNT(0);   // 2017년 기준  기초액 812000
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            onClickConfig(findViewById(R.id.btn_config));
            Toast.makeText(getApplicationContext(), "설정 파일이 존재 하지 않습니다.", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return readStr.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_msg);


        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_charge, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(false); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        //zoomView.setMiniMapCaption("Mini Map Test"); //미니 맵 내용
        //zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정

        LinearLayout container = findViewById(R.id.container);
        container.addView(zoomView);

        //스와이프 구현
        v.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeRight() {
                finish();
                overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
            }

            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(), "마지막 페이지 입니다.", Toast.LENGTH_SHORT).show();
            }
        });


        getConfig();  // 설정파일 불러오기

        edit1 = findViewById(R.id.etR1C1);  // 상시인원
        edit2 = findViewById(R.id.etR1C5);  // 경증
        edit3 = findViewById(R.id.etR1C6);  // 중증

        editb1 = findViewById(R.id.etR2C1);
        editb2 = findViewById(R.id.etR2C5);
        editb3 = findViewById(R.id.etR2C6);

        editc1 = findViewById(R.id.etR3C1);
        editc2 = findViewById(R.id.etR3C5);
        editc3 = findViewById(R.id.etR3C6);


        text5 = findViewById(R.id.tvR1C4);  // 의무고용인원
        text6 = findViewById(R.id.tvR1C3);  // 장애인 고용율
        //     text7 =  findViewById(R.id.textView7);  // 목표대비 고용율
        text8 = findViewById(R.id.etR1C7);  // 부담기초액
        text9 = findViewById(R.id.etR1C8);   // 미달인원(경증)
        text11 = findViewById(R.id.tvR1C9);  // 월부담금
        text12 = findViewById(R.id.tvR1C10);  // 연부담금
        text13 = findViewById(R.id.tvR1C11);  // 차액

        textb5 = findViewById(R.id.tvR2C4);  // 의무고용인원
        textb6 = findViewById(R.id.tvR2C3);  // 장애인 고용율
        //      textb7 = findViewById(R.id.textViewb7);  // 목표대비 고용율
        textb8 = findViewById(R.id.etR2C7);  // 부담기초액
        textb9 = findViewById(R.id.etR2C8);   // 미달인원(경증)
        textb11 = findViewById(R.id.tvR2C9);  // 월부담금
        textb12 = findViewById(R.id.tvR2C10);  // 연부담금
        textb13 = findViewById(R.id.tvR2C11);  // 차액

        textc5 = findViewById(R.id.tvR3C4);  // 의무고용인원
        textc6 = findViewById(R.id.tvR3C3);  // 장애인 고용율
        //       textc7 = findViewById(R.id.textViewc7);  // 목표대비 고용율
        textc8 = findViewById(R.id.etR3C7);  // 부담기초액
        textc9 = findViewById(R.id.etR3C8);   // 미달인원(경증)
        textc11 = findViewById(R.id.tvR3C9);  // 월부담금
        textc12 = findViewById(R.id.tvR3C10);  // 연부담금
        textc13 = findViewById(R.id.tvR3C11);  // 차액

        final Spinner spinner_Rate_1 = findViewById(R.id.spnR1C2);  // 의무고용율
        final Spinner spinner_Rate_2 = findViewById(R.id.spnR2C2);  // 의무고용율
        final Spinner spinner_Rate_3 = findViewById(R.id.spnR3C2);  // 의무고용율

        spinner_Rate_1.setSelection(0, true);
        spinner_Rate_2.setSelection(0, true);
        spinner_Rate_3.setSelection(0, true);

        // 스피너 속성과 선택값 얻어오기
        spinner_Rate_1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView arg0, View view, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String spinner_Rate_1_value = arg0.getItemAtPosition(arg2).toString();
                spinner_Rate_1_value = spinner_Rate_1_value.substring(0, 3);
                rate_1 = Double.parseDouble(spinner_Rate_1_value);
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });
        spinner_Rate_2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView arg0, View view, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String spinner_Rate_2_value = arg0.getItemAtPosition(arg2).toString();
                spinner_Rate_2_value = spinner_Rate_2_value.substring(0, 3);
                //Toast.makeText(getBaseContext(), spinner_Rate_2_value,Toast.LENGTH_SHORT).show();
                rate_2 = Double.parseDouble(spinner_Rate_2_value);
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });
        spinner_Rate_3.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView arg0, View view, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String spinner_Rate_3_value = arg0.getItemAtPosition(arg2).toString();
                spinner_Rate_3_value = spinner_Rate_3_value.substring(0, 3);
                //Toast.makeText(getBaseContext(), spinner_Rate_3_value,Toast.LENGTH_SHORT).show();
                rate_3 = Double.parseDouble(spinner_Rate_3_value);
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });

        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });
        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });
        edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });
        editb1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });
        editb2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });
        editb3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });
        editc1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });
        editc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });
        editc3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });

        edit1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    String tmp_edit1 = edit1.getText().toString();
                    if (tmp_edit1.length() > 0) {
                        if (editb1.getText().length() == 0) editb1.setText(tmp_edit1);
                        if (editc1.getText().length() == 0) editc1.setText(tmp_edit1);
//                        if (editd1.getText().length() == 0) editd1.setText(tmp_edit1);
//                        if (edite1.getText().length() == 0) edite1.setText(tmp_edit1);
                    }
                }
            }
        });

    }  //End of Oncreate

    public void calculate() {

        final Spinner spinner_Rate_1 = findViewById(R.id.spnR1C2);  // 의무고용율
        final Spinner spinner_Rate_2 = findViewById(R.id.spnR2C2);  // 의무고용율
        final Spinner spinner_Rate_3 = findViewById(R.id.spnR3C2);  // 의무고용율

        String tmp_edit1 = edit1.getText().toString();  // 상시인원
        String tmp_edit2 = edit2.getText().toString();  // 경증인원
        String tmp_edit3 = edit3.getText().toString();  // 중증인원

        if (tmp_edit1.equals("")) {
            tmp_edit1 = "0";
        }
        if (rate_1 == 0.0) {   // 초기에 spinner 값을 못가져 오기에 현재 년도 기준으로 값을 넣어줌
            rate_1 = Double.parseDouble(spinner_Rate_1.getSelectedItem().toString().replace("%", ""));
        }
        String tmp_result = cal_dutyCnt(tmp_edit1, rate_1);    // 의무 고용인원 계산
        text5.setText(tmp_result);

        if (tmp_edit2.equals("")) {
            tmp_edit2 = "0";
        }
        if (tmp_edit3.equals("")) {
            tmp_edit3 = "0";
        }
        String tmp_result2 = cal_disabledRate(tmp_edit1, tmp_edit2, tmp_edit3);  // 장애인 고용률 계산
        text6.setText(tmp_result2 + "%");

        String tmp_result3 = cal_GoalRate(tmp_edit1, tmp_edit2, tmp_edit3, rate_1);  // 목표대비 고용률 계산
        //     text7.setText(tmp_result3 + "%");

/*        int tmp_result4 = baseAmount(tmp_result3);  // 부담기초액
        text8.setText(makeStringComma(tmp_result4) );
*/
        int tmp_result4 = baseAmountNew(tmp_edit1, rate_1, tmp_edit2, tmp_edit3);  // 부담기초액
        text8.setText(makeStringComma(tmp_result4));


        String tmp_result5 = underCnt(tmp_edit1, tmp_edit2, tmp_edit3, "1", rate_1);  // 미달인원 경증
        text9.setText(tmp_result5 + "명");


        String tmp_result7 = monthAmount(tmp_result4, tmp_result5);   // 월 부담금 ( 부담기초액, 미달인원 경증)
        String tmp_result8 = "";
        text11.setText(makeStringComma(tmp_result7));
        tmp_result8 = yearAmount(tmp_result4, tmp_result5);
        text12.setText(makeStringComma(tmp_result8));
        text13.setText("-");  // 차액  맨 처음꺼는 비교 대상이 없으므로 -

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String tmp_editb1 = editb1.getText().toString();  // 상시인원
        String tmp_editb2 = editb2.getText().toString();  // 경증인원
        String tmp_editb3 = editb3.getText().toString();  // 중증인원

        int sumCnt2 = 0;  // 장애인 근로자 합계  경증인원 + (중증*2)

        //    String tmp_editb1 = editb1.getText().toString();
        if (tmp_editb1.equals("")) {
            tmp_editb1 = "0";
        }
        if (rate_2 == 0.0) {   // 초기에 spinner 값을 못가져 오기에 현재 년도 기준으로 값을 넣어줌
            //if(year==2017 || year==2018) rate_2=2.9;
            //else if(year==2019) rate_2=3.1;
            rate_2 = Double.parseDouble(spinner_Rate_2.getSelectedItem().toString().replace("%", ""));
        }
        String tmp_resultb = cal_dutyCnt(tmp_editb1, rate_2);
        textb5.setText(tmp_resultb);

        if (tmp_editb2.equals("")) {
            tmp_editb2 = "0";
        }
        if (tmp_editb3.equals("")) {
            tmp_editb3 = "0";
        }
        String tmp_resultb2 = cal_disabledRate(tmp_editb1, tmp_editb2, tmp_editb3);  // 장애인 고용률 계산
        textb6.setText(tmp_resultb2 + "%");

        String tmp_resultb3 = cal_GoalRate(tmp_editb1, tmp_editb2, tmp_editb3, rate_2);  // 목표대비 고용률 계산
        //       textb7.setText(tmp_resultb3 + "%");

        /*int tmp_resultb4 = baseAmount(tmp_resultb3);  // 부담기초액
        textb8.setText(makeStringComma(tmp_resultb4) );*/

        int tmp_resultb4 = baseAmountNew(tmp_editb1, rate_2, tmp_editb2, tmp_editb3);  // 부담기초액
        textb8.setText(makeStringComma(tmp_resultb4));

        String tmp_resultb5 = underCnt(tmp_editb1, tmp_editb2, tmp_editb3, "1", rate_2);  // 미달인원 경증
        textb9.setText(tmp_resultb5 + "명");


        String tmp_resultb7 = monthAmount(tmp_resultb4, tmp_resultb5);   // 월 부담금 ( 부담기초액, 미달인원 경증)
        String tmp_resultb8 = "";
        textb11.setText(makeStringComma(tmp_resultb7));   // 년 부담금 ( 부담기초액, 미달인원 경증)
        tmp_resultb8 = yearAmount(tmp_resultb4, tmp_resultb5);
        textb12.setText(makeStringComma(tmp_resultb8));

        String tmp_resultb9 = diff_Amount(tmp_result8, tmp_resultb8);
        if (tmp_editb1.equals("0")) textb13.setText("-");
        textb13.setText(makeStringComma(tmp_resultb9));  // 차액


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String tmp_editc1 = editc1.getText().toString();  // 상시인원
        String tmp_editc2 = editc2.getText().toString();  // 경증인원
        String tmp_editc3 = editc3.getText().toString();  // 중증인원

        int sumCnt3 = 0;  // 장애인 근로자 합계  경증인원 + (중증*2)

        if (tmp_editc1.equals("")) {
            tmp_editc1 = "0";
        }
        if (rate_3 == 0.0) {   // 초기에 spinner 값을 못가져 오기에 현재 년도 기준으로 값을 넣어줌
            rate_3 = Double.parseDouble(spinner_Rate_3.getSelectedItem().toString().replace("%", ""));
        }
        String tmp_resultc = cal_dutyCnt(tmp_editc1, rate_3);
        textc5.setText(tmp_resultc);

        if (tmp_editc2.equals("")) {
            tmp_editc2 = "0";
        }
        if (tmp_editc3.equals("")) {
            tmp_editc3 = "0";
        }
        String tmp_resultc2 = cal_disabledRate(tmp_editc1, tmp_editc2, tmp_editc3);  // 장애인 고용률 계산
        textc6.setText(tmp_resultc2 + "%");

        int tmp_resultc4 = baseAmountNew(tmp_editc1, rate_3, tmp_editc2, tmp_editc3);  // 부담기초액
        textc8.setText(makeStringComma(tmp_resultc4));


        String tmp_resultc5 = underCnt(tmp_editc1, tmp_editc2, tmp_editc3, "1", rate_3);  // 미달인원 경증
        textc9.setText(tmp_resultc5 + "명");


        String tmp_resultc7 = monthAmount(tmp_resultc4, tmp_resultc5);   // 월 부담금 ( 부담기초액, 미달인원 경증)
        String tmp_resultc8 = "";
        textc11.setText(makeStringComma(tmp_resultc7));   // 년 부담금 ( 부담기초액, 미달인원 경증)
        tmp_resultc8 = yearAmount(tmp_resultc4, tmp_resultc5);
        textc12.setText(makeStringComma(tmp_resultc8));

        String tmp_resultc9 = diff_Amount(tmp_result8, tmp_resultc8);
        if (tmp_editc1.equals("0")) textc13.setText("-");
        else textc13.setText(makeStringComma(tmp_resultc9));  // 차액

        GraphView graph = findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1, Long.parseLong(tmp_result8)),
                new DataPoint(2, Long.parseLong(tmp_resultb8)),
                new DataPoint(3, Long.parseLong(tmp_resultc8)),
        });
        series.setSpacing(35);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(0xFF0054A6);
        series.setValuesOnTopSize(28);

        graph.removeAllSeries();
        graph.addSeries(series);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0.3);
        graph.getViewport().setMaxX(3.7);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridColor(0xFF8FB9C9);
        graph.getGridLabelRenderer().setVerticalLabelsColor(0xFF8FB9C9);
        graph.getViewport().setScalable(false);
        graph.getViewport().setScrollable(false);

    } ///End of Calculate/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String cal_dutyCnt(String tmp, double rate) {   // 의무 고용 인원 계산
        int int_tmp = Integer.parseInt(tmp);
        double tmp_rate = rate;  // 추후에 스피너로 비율 받으면 rate 대체해야함
        double result = 0;
        result = (int_tmp * tmp_rate) / 100;
        rate = rate / 100;
        System.out.println("rate:" + Math.round(rate));

        return Integer.toString((int) result);
    }

    public String cal_disabledRate(String tmp1, String tmp2, String tmp3) {   // 장애인 고용률, tmp1=상시 tmp2=경증, tmp3=중증
        double double_tmp1 = Double.parseDouble(tmp1);
        double double_tmp2 = Double.parseDouble(tmp2);
        double double_tmp3 = Double.parseDouble(tmp3);

        double result = double_tmp2 + (double_tmp3 * 2);  // 합계

        double tmp_diabled_rate = 100 * (result / double_tmp1);

        if (Double.isNaN(tmp_diabled_rate)) tmp_diabled_rate = 0;   // NaN 나오는 문제 예외처리

        tmp_diabled_rate = Double.parseDouble(String.format("%.3f", tmp_diabled_rate));
        return Double.toString((double) tmp_diabled_rate);
    }


    public Double cal_Sum(String tmp1, String tmp2) {   // 장애인 근로자 합계  tmp1 경중, tmp2 중증
        double double_tmp1 = Double.parseDouble(tmp1);
        double double_tmp2 = Double.parseDouble(tmp2);

        return double_tmp1 + (double_tmp2 * 2);
    }

    public String cal_GoalRate(String tmp1, String tmp2, String tmp3, Double rate) {   // 목표대비 고용률

        double sum = cal_Sum(tmp2, tmp3);  // 장애인근로자 합계
//        String tmp_dutyCnt = cal_dutyCnt(tmp1,0);  // 의무고용인원
        String tmp_dutyCnt = cal_dutyCnt(tmp1, rate);  // 의무고용인원
        double double_dutyCnt = Double.parseDouble(tmp_dutyCnt);
        if (double_dutyCnt == 0 || double_dutyCnt == 0.0) return "0";   // 분모가 0이 되면 안되므로 예외처리 해둠
        double result = 100 * (sum / double_dutyCnt);

        result = Double.parseDouble(String.format("%.3f", result));

        return Double.toString((double) result);
    }

    DecimalFormat df = new DecimalFormat("#.##");   // Double 형 소수점 뒤 0  제거

    public String formatD(double number) {
        return df.format(number);
    }

    public String underCnt(String tmp1, String tmp2, String tmp3, String flag, Double rate) {   // 미달인원 경증 중증 상시인원 tmp1:상시인원 tmp2:경증, tmp3:중증, flag: 1.미달경증. 2.미달중증

        double sum = cal_Sum(tmp2, tmp3);  // 장애인근로자 합계
        //  String tmp_dutyCnt = cal_dutyCnt(tmp1,0);  // 의무고용인원
        String tmp_dutyCnt = cal_dutyCnt(tmp1, rate);  // 의무고용인원
        double double_dutyCnt = Double.parseDouble(tmp_dutyCnt);
        double result = 0;
        String tmp_result = "";
        if (flag.equals("1")) {
            result = double_dutyCnt - sum;
            tmp_result = formatD(result);

        } else if (flag.equals("2")) {
            result = (double_dutyCnt - sum) / 2;
            result = Double.parseDouble(String.format("%.1f", result));
            tmp_result = Double.toString((double) result);
        }

        return tmp_result;
    }


    public int baseAmountNew(String tmp, double rate, String tmp2, String tmp3) {  // 부담기초액  김정중대리 엑셀  tmp상시  rate 의무고용율 tmp2 경증 tmp3 중증
        double result = 0;

        double cal_sum = cal_Sum(tmp2, tmp3);  // 장애인근로자 합계
        String str_dutyCnt = cal_dutyCnt(tmp, rate); // 의무고용인원
        double dbl_dutyCnt = Double.parseDouble(str_dutyCnt);  // 의무고용인원
        double dbl_quarter = dbl_dutyCnt * 0.25;   // 1/4
        double dbl_half = dbl_dutyCnt * 0.5;       //  1/2
        double dbl_threeQuarter = dbl_dutyCnt * 0.75;   //  3/4

        if (cal_sum == 0) {
            result = MINUMUMWAGE;  // 최저 임금
        } else if (cal_sum < (int) dbl_quarter) {
            result = BASISAMOUNT * 1.4;
        } else if (cal_sum < (int) dbl_half) {
            result = BASISAMOUNT * 1.2;
        } else if (cal_sum < (int) dbl_threeQuarter) {
            result = BASISAMOUNT * 1.06;
        } else {
            result = BASISAMOUNT;
        }
        return (int) result;
    }

    private int baseAmountCalculate(int tmp_quarter, int tmp_half, int tmp_threeQuarter, int tmp_base) {
        return 0;
    }


    public String monthAmount(int dutyCnt, String underCnt) {  // 월 부담금(부담기초액, 미달인원 경증)
        if (dutyCnt == 0 || underCnt.equals("0.0"))
            return "0";
        double dblCnt = Double.parseDouble(underCnt);
        int int_underCnt = (int) dblCnt;
        long monthAmount = dutyCnt * int_underCnt;
        if (monthAmount < 0) return "0";
        return Long.toString(monthAmount);
    }

    public String yearAmount(int dutyCnt, String underCnt) {  // 년 부담금(부담기초액, 미달인원 경증)
        if (dutyCnt == 0 || underCnt.equals("0.0"))
            return "0";
        double dblCnt = Double.parseDouble(underCnt);
        long int_underCnt = (long) dblCnt;
        long yearAmount = ((long) dutyCnt * int_underCnt) * 12L;
        if (yearAmount < 0L) return "0";
        return Long.toString(yearAmount);
    }

    public String diff_Amount(String amount1, String amount2) {   // 차액 계산
        long tmp_amt1 = Long.parseLong(amount1);
        long tmp_amt2 = Long.parseLong(amount2);
        return Long.toString(tmp_amt1 - tmp_amt2);

    }

    protected String makeStringComma(int str0) {  // 세자리수 마다 콤마
        String str = Integer.toString(str0);
        if (str.length() == 0)
            return "";
        long value = Long.parseLong(str);
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(value);
    }

    protected String makeStringComma(String str0) {  // 세자리수 마다 콤마
        if (str0.length() == 0)
            return "";
        long value = Long.parseLong(str0);
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(value);
    }
}