package sonar2.tistory.com.busan.easydisabledemploy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import pl.polidea.view.ZoomView;

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        //스와이프 구현
        final View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_main, null, false);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(false); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        ConstraintLayout container = findViewById(R.id.container);
        container.addView(zoomView);
        v.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeRight() {
                Toast.makeText(getApplicationContext(),"첫 페이지 입니다.",Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(),"메뉴를 선택해 주세요.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickCharge(View view){
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("*****주의 사항*****");       // 제목 설정
        ad.setMessage("장애인 고용 부담금 간단 계산기는 개인이 만든 어플리케이션으로 결과값은 법적 효력이 없으며, 데이터 사용에 따른 책임은 사용자에게 있음을 알려드립니다.\n동의 하시면 확인을 선택하세요.");   // 내용 설정
        // 확인 버튼 설정
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기
                Intent intent = new Intent(MainActivity.this, ChargeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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
        Intent intent = new Intent(this, KeadActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void onClickKeadintro(View view){
        Intent intent = new Intent(this, Busan1Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
