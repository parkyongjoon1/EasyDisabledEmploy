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

public class Grant extends AppCompatActivity {

    private EditText etA1;  // 상시인원
    private EditText etB1;
    private EditText etC1;

    private TextView tvA4;
    private TextView tvB4;
    private TextView tvC4;

    private EditText etA5;  // 경증
    private EditText etB5;
    private EditText etC5;

    private EditText etA6;  // 중증
    private EditText etB6;
    private EditText etC6;

    private TextView etA7;
    private TextView etB7;
    private TextView etC7;

    private TextView etA8;
    private TextView etB8;
    private TextView etC8;

    private TextView tvA9;
    private TextView tvB9;
    private TextView tvC9;

    private TextView tvA10;   //  연부담금
    private TextView tvB10;   //  연부담금
    private TextView tvC10;   //  연부담금

    private TextView tvA11;   //  차액
    private TextView tvB11;   //  차액
    private TextView tvC11;   //  차액


    private double dblA2;
    private double dblB2;
    private double dblC2;

    //private List myList;
    private String fileNames = "";
    //String path;  //sdcard 의 경로

    public void saveAllData(String fileName) {
        String strA1 = etA1.getText().toString();
        String strA5 = etA5.getText().toString();
        String strA6 = etA6.getText().toString();
        String strA7 = etA7.getText().toString();
        String strA8 = etA8.getText().toString();
        String strA2 = Double.toString(dblA2);

        String strB1 = etB1.getText().toString();
        String strB5 = etB5.getText().toString();
        String strB6 = etB6.getText().toString();
        String strB7 = etB7.getText().toString();
        String strB8 = etB8.getText().toString();
        String strB2 = Double.toString(dblB2);

        String strC1 = etC1.getText().toString();
        String strC5 = etC5.getText().toString();
        String strC6 = etC6.getText().toString();
        String strC7 = etC7.getText().toString();
        String strC8 = etC8.getText().toString();
        String strC2 = Double.toString(dblC2);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "/" + fileName + ".장려금", false));
            //BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + fileName+".txt", true));
            bw.write(strA1 + "/");
            bw.write(strA2 + "/");
            bw.write(strA5 + "/");
            bw.write(strA6 + "/");
            bw.write(strA7 + "/");
            bw.write(strA8 + "/");

            bw.write(strB1 + "/");
            bw.write(strB2 + "/");
            bw.write(strB5 + "/");
            bw.write(strB6 + "/");
            bw.write(strB7 + "/");
            bw.write(strB8 + "/");

            bw.write(strC1 + "/");
            bw.write(strC2 + "/");
            bw.write(strC5 + "/");
            bw.write(strC6 + "/");
            bw.write(strC7 + "/");
            bw.write(strC8 + "/");

            bw.close();
            Toast.makeText(this, fileName + " 저장완료!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickSave_g(View view) {

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("현 Data를 저장하시겠습니까?");       // 제목 설정
        ad.setMessage("저장할 파일이름을 넣으세요!");   // 내용 설정
        final EditText input = new EditText(Grant.this);
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


    public void onClickLoad_g(View view) {
        final Spinner spnA2 = findViewById(R.id.g_spnR1C2);  // 의무고용율 1
        final Spinner spnB2 = findViewById(R.id.g_spnR2C2);  // 의무고용율 2
        final Spinner spnC2 = findViewById(R.id.g_spnR3C2);  // 의무고용율 3


        String path = getFilesDir().toString();

        //myList = new ArrayList();

        File f = new File(path + "");

        File[] list = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.lastIndexOf(".장려금") > 0;
            }
        });
        assert list != null;
        final CharSequence[] items = new String[list.length];
        for (int i = 0; i < list.length; i++) {
            //   myList.add(list[i].getName());
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
                        etA1.setText(loadData[0]);
                        spnA2.setSelection(((ArrayAdapter<String>) spnA2.getAdapter()).getPosition(loadData[1] + "%"), true);
                        etA5.setText(loadData[2]);
                        etA6.setText(loadData[3]);
                        etA7.setText(loadData[4]);
                        etA8.setText(loadData[5]);

                        etB1.setText(loadData[6]);
                        spnB2.setSelection(((ArrayAdapter<String>) spnB2.getAdapter()).getPosition(loadData[7] + "%"), true);
                        etB5.setText(loadData[8]);
                        etB6.setText(loadData[9]);
                        etB7.setText(loadData[10]);
                        etB8.setText(loadData[11]);

                        etC1.setText(loadData[12]);
                        spnC2.setSelection(((ArrayAdapter<String>) spnC2.getAdapter()).getPosition(loadData[13] + "%"), true);
                        etC5.setText(loadData[14]);
                        etC6.setText(loadData[15]);
                        etC7.setText(loadData[16]);
                        etC8.setText(loadData[17]);


                        Toast.makeText(getApplicationContext(), fileNames + " 불러왔습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "file이 존재 하지 않거나 잘못된 파일입니다.", Toast.LENGTH_LONG).show();
                    }
                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "file이 존재 하지 않습니다.", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        builder.show();

    }

    public void onClickDel_g(View view) {
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
                    if(f.delete()) {
                        Toast.makeText(getApplicationContext(), fileNames + " 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), fileNames + " 삭제 실패!!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }

        });
        builder.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grant_msg);


        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_grant, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(false); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.

        LinearLayout container = findViewById(R.id.g_container);
        container.addView(zoomView);

        //스와이프 구현
        v.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeRight() {
                finish();
                overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
            }

            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(), "마지막 페이지 입니다.^^", Toast.LENGTH_SHORT).show();
            }
        });


        etA1 = findViewById(R.id.g_etR1C1);  // 상시인원
        etB1 = findViewById(R.id.g_etR2C1);
        etC1 = findViewById(R.id.g_etR3C1);

        final Spinner spnA2 = findViewById(R.id.g_spnR1C2);  // 의무고용율
        final Spinner spnB2 = findViewById(R.id.g_spnR2C2);  // 의무고용율
        final Spinner spnC2 = findViewById(R.id.g_spnR3C2);  // 의무고용율

        //tvA3 = findViewById(R.id.g_tvR1C3);  // 장애인 고용율
        //tvB3 = findViewById(R.id.g_tvR2C3);  // 장애인 고용율
        //tvC3 = findViewById(R.id.g_tvR3C3);  // 장애인 고용율

        tvA4 = findViewById(R.id.g_tvR1C4);  // 의무고용인원
        tvB4 = findViewById(R.id.g_tvR2C4);  // 의무고용인원
        tvC4 = findViewById(R.id.g_tvR3C4);  // 의무고용인원

        etA5 = findViewById(R.id.g_etR1C5);  // 기준초과 경증남성
        etB5 = findViewById(R.id.g_etR2C5);
        etC5 = findViewById(R.id.g_etR3C5);

        etA6 = findViewById(R.id.g_etR1C6);  // 기준초과 경증여성
        etB6 = findViewById(R.id.g_etR2C6);
        etC6 = findViewById(R.id.g_etR3C6);

        etA7 = findViewById(R.id.g_etR1C7);  // 기준초과 중증남성
        etB7 = findViewById(R.id.g_etR2C7);  //
        etC7 = findViewById(R.id.g_etR3C7);  //

        etA8 = findViewById(R.id.g_etR1C8);   // 기준초과 중증여성
        etB8 = findViewById(R.id.g_etR2C8);   //
        etC8 = findViewById(R.id.g_etR3C8);   // 미달인원(경증)

        tvA9 = findViewById(R.id.g_tvR1C9);  // 월장려금
        tvB9 = findViewById(R.id.g_tvR2C9);  //
        tvC9 = findViewById(R.id.g_tvR3C9);  //

        tvA10 = findViewById(R.id.g_tvR1C10);  // 연장려금
        tvB10 = findViewById(R.id.g_tvR2C10);  //
        tvC10 = findViewById(R.id.g_tvR3C10);  //

        tvA11 = findViewById(R.id.g_tvR1C11);  // 차액
        tvB11 = findViewById(R.id.g_tvR2C11);  // 차액
        tvC11 = findViewById(R.id.g_tvR3C11);  // 차액

        spnA2.setSelection(0, true);
        spnB2.setSelection(0, true);
        spnC2.setSelection(0, true);


        // 스피너 속성과 선택값 얻어오기
        spnA2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView arg0, View view, int arg2, long arg3) {
                String spinner_Rate_1_value = arg0.getItemAtPosition(arg2).toString();
                spinner_Rate_1_value = spinner_Rate_1_value.substring(0, 3);

                //Toast.makeText(getBaseContext(), spinner_Rate_1_value,Toast.LENGTH_SHORT).show();
                dblA2 = Double.parseDouble(spinner_Rate_1_value);
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });
        spnB2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView arg0, View view, int arg2, long arg3) {
                String spinner_Rate_2_value = arg0.getItemAtPosition(arg2).toString();
                spinner_Rate_2_value = spinner_Rate_2_value.substring(0, 3);
                dblB2 = Double.parseDouble(spinner_Rate_2_value);
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });
        spnC2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView arg0, View view, int arg2, long arg3) {
                String spinner_Rate_3_value = arg0.getItemAtPosition(arg2).toString();
                spinner_Rate_3_value = spinner_Rate_3_value.substring(0, 3);
                dblC2 = Double.parseDouble(spinner_Rate_3_value);
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });

        etA1.addTextChangedListener(new TextWatcher() {
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
        etA5.addTextChangedListener(new TextWatcher() {
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
        etA6.addTextChangedListener(new TextWatcher() {
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
        etA7.addTextChangedListener(new TextWatcher() {
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
        etA8.addTextChangedListener(new TextWatcher() {
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
        etB1.addTextChangedListener(new TextWatcher() {
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
        etB5.addTextChangedListener(new TextWatcher() {
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
        etB6.addTextChangedListener(new TextWatcher() {
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
        etB7.addTextChangedListener(new TextWatcher() {
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
        etB8.addTextChangedListener(new TextWatcher() {
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
        etC1.addTextChangedListener(new TextWatcher() {
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
        etC5.addTextChangedListener(new TextWatcher() {
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
        etC6.addTextChangedListener(new TextWatcher() {
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
        etC7.addTextChangedListener(new TextWatcher() {
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
        etC8.addTextChangedListener(new TextWatcher() {
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


        //상시인원 첫줄 넣으면 나머지 자동 복사되도록 하는 루틴,
        etA1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    String tmp_edit1 = etA1.getText().toString();
                    if (tmp_edit1.length() > 0) {
                        if (etB1.getText().length() == 0) etB1.setText(tmp_edit1);
                        if (etC1.getText().length() == 0) etC1.setText(tmp_edit1);
                    }
                }
            }
        });

    }  //End of Oncreate

    public void calculate() {

        final Spinner spinner_Rate_1 = findViewById(R.id.g_spnR1C2);  // 의무고용율
        final Spinner spinner_Rate_2 = findViewById(R.id.g_spnR2C2);  // 의무고용율
        final Spinner spinner_Rate_3 = findViewById(R.id.g_spnR3C2);  // 의무고용율


        String total_worker;// 상시인원
        String light_man_worker;// 경증남성
        String light_woman_worker;  // 경증여성
        String heavy_man_worker; //중증남성
        String heavy_woman_worker;  //중증여성
        String str_resultA, str_resultB, str_resultC;
        String tmp_result;

        //+++++++++++++++++++++++++1번째 행++++++++++++++++++++++++++++++++++++++++++++++++++
        total_worker = etA1.getText().toString();
        light_man_worker = etA5.getText().toString();
        light_woman_worker = etA6.getText().toString();
        heavy_man_worker = etA7.getText().toString();
        heavy_woman_worker = etA8.getText().toString();

        if (total_worker.equals("")) {
            total_worker = "0";
        }
        if (dblA2 == 0.0) {   // 초기에 spinner 값을 못가져 오기에 현재 년도 기준으로 값을 넣어줌
            dblA2 = Double.parseDouble(spinner_Rate_1.getSelectedItem().toString().replace("%", ""));
        }
        str_resultA = cal_dutyCnt(total_worker, dblA2);    // 지급 기준 인원 계산
        tvA4.setText(str_resultA);

        if (light_man_worker.equals("")) {
            light_man_worker = "0";
        }
        if (light_woman_worker.equals("")) {
            light_woman_worker = "0";
        }
        if (heavy_man_worker.equals("")) {
            heavy_man_worker = "0";
        }
        if (heavy_woman_worker.equals("")) {
            heavy_woman_worker = "0";
        }

        str_resultA = monthAmount(light_man_worker, light_woman_worker, heavy_man_worker, heavy_woman_worker);   // 월 장려금
        tvA9.setText(makeStringComma(str_resultA));
        str_resultA = yearAmount(str_resultA);
        tvA10.setText(makeStringComma(str_resultA));

        tvA11.setText("-");  // 차액  맨 처음꺼는 비교 대상이 없으므로 -
        ////////////////////////////////////////////////////////////////////////////////////


        //+++++++++++++++++++++++++2번째 행++++++++++++++++++++++++++++++++++++++++++++++++++
        total_worker = etB1.getText().toString();
        light_man_worker = etB5.getText().toString();
        light_woman_worker = etB6.getText().toString();
        heavy_man_worker = etB7.getText().toString();
        heavy_woman_worker = etB8.getText().toString();

        if (total_worker.equals("")) {
            total_worker = "0";
        }
        if (dblB2 == 0.0) {   // 초기에 spinner 값을 못가져 오기에 현재 년도 기준으로 값을 넣어줌
            dblB2 = Double.parseDouble(spinner_Rate_2.getSelectedItem().toString().replace("%", ""));
        }
        str_resultB = cal_dutyCnt(total_worker, dblB2);    // 지급 기준 인원 계산
        tvB4.setText(str_resultB);

        if (light_man_worker.equals("")) {
            light_man_worker = "0";
        }
        if (light_woman_worker.equals("")) {
            light_woman_worker = "0";
        }
        if (heavy_man_worker.equals("")) {
            heavy_man_worker = "0";
        }
        if (heavy_woman_worker.equals("")) {
            heavy_woman_worker = "0";
        }

        str_resultB = monthAmount(light_man_worker, light_woman_worker, heavy_man_worker, heavy_woman_worker);   // 월 장려금
        tvB9.setText(makeStringComma(str_resultB));
        str_resultB = yearAmount(str_resultB);
        tvB10.setText(makeStringComma(str_resultB));

        tmp_result = diff_Amount(str_resultB, str_resultA);
        if (str_resultB.equals("0")) tvB11.setText("-");
        else tvB11.setText(makeStringComma(tmp_result));  // 차액
        ////////////////////////////////////////////////////////////////////////////////////


        //+++++++++++++++++++++++++3번째 행++++++++++++++++++++++++++++++++++++++++++++++++++
        total_worker = etC1.getText().toString();

        light_man_worker = etC5.getText().toString();

        light_woman_worker = etC6.getText().toString();

        heavy_man_worker = etC7.getText().toString();

        heavy_woman_worker = etC8.getText().toString();

        if (total_worker.equals("")) {
            total_worker = "0";
        }
        if (dblC2 == 0.0) {   // 초기에 spinner 값을 못가져 오기에 현재 년도 기준으로 값을 넣어줌
            dblC2 = Double.parseDouble(spinner_Rate_3.getSelectedItem().toString().replace("%", ""));
        }

        str_resultC = cal_dutyCnt(total_worker, dblC2);    // 지급 기준 인원 계산
        tvC4.setText(str_resultC);

        if (light_man_worker.equals("")) {
            light_man_worker = "0";
        }
        if (light_woman_worker.equals("")) {
            light_woman_worker = "0";
        }
        if (heavy_man_worker.equals("")) {
            heavy_man_worker = "0";
        }
        if (heavy_woman_worker.equals("")) {
            heavy_woman_worker = "0";
        }

        str_resultC = monthAmount(light_man_worker, light_woman_worker, heavy_man_worker, heavy_woman_worker);   // 월 장려금
        tvC9.setText(makeStringComma(str_resultC));
        str_resultC = yearAmount(str_resultC);
        tvC10.setText(makeStringComma(str_resultC));

        tmp_result = diff_Amount(str_resultC, str_resultA);
        if (str_resultC.equals("0")) tvC11.setText("-");
        else tvC11.setText(makeStringComma(tmp_result));  // 차액
        ////////////////////////////////////////////////////////////////////////////////////


        ///// 여기부터 그래프를 표시하기 위한 부분 ////////////////
        GraphView graph = findViewById(R.id.g_graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1, Long.parseLong(str_resultA)),
                new DataPoint(2, Long.parseLong(str_resultB)),
                new DataPoint(3, Long.parseLong(str_resultC)),
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


    public String cal_dutyCnt(String tmp, double rate) {   // 지급기준 인원 계산
        int int_tmp = Integer.parseInt(tmp);
        int result = (int) Math.ceil((int_tmp * rate) / 100);

        return Integer.toString(result);
    }


    public String monthAmount(String lm, String lw, String hm, String hw) {  // 월 장려금 계산
        long l_lm = Long.parseLong(lm);
        long l_lw = Long.parseLong(lw);
        long l_hm = Long.parseLong(hm);
        long l_hw = Long.parseLong(hw);

        long l_monthAmount = l_lm * 300000 + l_lw * 450000 + l_hm * 600000 + l_hw * 800000;

        return Long.toString(l_monthAmount);
    }

    public String yearAmount(String mA) {  // 년 부담금(부담기초액, 미달인원 경증)
        long yearAmount = Long.parseLong(mA) * 12L;
        return Long.toString(yearAmount);
    }

    public String diff_Amount(String amount1, String amount2) {   // 차액 계산
        long tmp_amt1 = Long.parseLong(amount1);
        long tmp_amt2 = Long.parseLong(amount2);
        return Long.toString(tmp_amt1 - tmp_amt2);

    }

    protected String makeStringComma(String str0) {  // 세자리수 마다 콤마
        if (str0.length() == 0)
            return "";
        long value = Long.parseLong(str0);
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(value);
    }
}
