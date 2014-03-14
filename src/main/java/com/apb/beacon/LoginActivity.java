package com.apb.beacon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apb.beacon.alert.AlertStatus;
import com.apb.beacon.common.AppUtil;


public class LoginActivity extends PanicButtonActivity {

    private EditText passwordEditText;
    private Button bGo;
    private int tryCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_layout);

        passwordEditText = (EditText) findViewById(R.id.create_pin_edittext);

        bGo = (Button) findViewById(R.id.b_action);
        bGo.setText("Go");
        bGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                if (ApplicationSettings.passwordMatches(getApplicationContext(), password)) {

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    if (getPanicAlert().getAlertStatus().equals(AlertStatus.ACTIVE)) {
                        i.putExtra("page_id", "home-alerting");
                    } else{
                        i.putExtra("page_id", "home-ready");
                    }
                    startActivity(i);


                    return;
                }
                AppUtil.setError(LoginActivity.this, passwordEditText, ((tryCount < 2) ? R.string.incorrect_pin : R.string.incorrect_pin_3_times));
                tryCount++;
            }
        });
    }
}