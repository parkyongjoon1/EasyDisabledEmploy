package kr.or.kead.busan.easydisabledemploy;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by 맞춤팀 on 2017-10-17.
 */

public class IntroActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
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