package kr.or.kead.busan.easydisabledemploy;

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
        Intent intent = new Intent(this, ChargeActivity.class);
        startActivity(intent);

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
