package sonar2.tistory.com.busan.easydisabledemploy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by 맞춤팀 on 2017-10-17.
 */

public class KeadIntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keadintro);
    }

    public void onCLickNext(View view) {
        Intent intent = new Intent(this, Keadintro2Activity.class);
        startActivity(intent);
        finish();
    }
}
