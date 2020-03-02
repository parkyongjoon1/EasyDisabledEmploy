package kr.or.kead.busan.easydisabledemploy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickCharge(View view){
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("*****주의 사항*****");       // 제목 설정
        ad.setMessage("장애인 고용 부담금 간단 계산기는 개인이 만든 어플리케이션으로 결과값은 법적 효력이 없으며, 데이터 사용에 따른 책임은 사용자에게 있음을 알려드립니다.\n 동의 하시면 확인을 선택하세요.");   // 내용 설정
        // 확인 버튼 설정
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기
                Intent intent = new Intent(MainActivity.this, ChargeActivity.class);
                startActivity(intent);
            }
        });
//       // 중립 버튼 설정
//        ad.setNeutralButton("What?", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();     //닫기
//                // Event
//            }
//        });
        // 취소 버튼 설정
//        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();     //닫기
//                // Event
//            }
//        });
        // 창 띄우기
        ad.show();
    }

    public void onClickIntro(View view){
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);

    }

    public void onClickKeadintro(View view){
        Intent intent = new Intent(this, KeadIntroActivity.class);
        startActivity(intent);

    }

}
