package com.ssthouse.supermarketmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ssthouse.supermarketmanagement.util.PreferUtil;

/**
 * Created by ssthouse on 21/11/2016.
 */

public class SettingAty extends AppCompatActivity {

    EditText etStaffName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initToolbar();
        etStaffName = (EditText) findViewById(R.id.id_et_staff_name);
        etStaffName.setText(PreferUtil.getInstance(this).getStaffName());

        findViewById(R.id.id_btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etStaffName.getText().toString().trim())) {
                    return;
                }
                Toast.makeText(SettingAty.this, "Change Name to : " + etStaffName.getText().toString(),
                        Toast.LENGTH_SHORT).show();
                PreferUtil.getInstance(SettingAty.this).setStaffName(etStaffName.getText().toString());
                finish();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.id_tb));
        getSupportActionBar().setTitle("Setting");
    }

    public static void start(TaskListActivity taskListActivity) {
        Intent intent = new Intent(taskListActivity, SettingAty.class);
        taskListActivity.startActivity(intent);
    }
}
