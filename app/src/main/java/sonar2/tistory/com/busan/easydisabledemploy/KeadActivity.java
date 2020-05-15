package sonar2.tistory.com.busan.easydisabledemploy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import pl.polidea.view.ZoomView;

/**
 * Created by 맞춤팀 on 2017-10-17.
 */

public class KeadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kead);

        //화면확대
        final View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_kead, null, false);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(false); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        ConstraintLayout container = findViewById(R.id.container);
        container.addView(zoomView);

        //스와이프 구현
        v.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeRight() {
                finish();
                overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
            }
            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(),"마지막 페이지 입니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickKeadService(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.naver.com/kead1/221138688332"));
        startActivity(intent);
    }

    public void onClickDisabledKnow(View view) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:_QGixW-4C8E"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=_QGixW-4C8E"));
        try {
            startActivity(appIntent);
        } catch(ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}