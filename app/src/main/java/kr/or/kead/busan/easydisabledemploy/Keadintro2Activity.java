package kr.or.kead.busan.easydisabledemploy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Keadintro2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keadintro2);
    }

    public void onCLickNext(View view) {
        Intent intent = new Intent(this, Keadintro3Activity.class);
        startActivity(intent);
        finish();
    }
}
