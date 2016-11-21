package com.ssthouse.supermarketmanagement.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ssthouse.supermarketmanagement.R;
import com.ssthouse.supermarketmanagement.bean.Task;
import com.ssthouse.supermarketmanagement.bean.TaskState;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ssthouse.supermarketmanagement.bean.TaskState.Finished;
import static com.ssthouse.supermarketmanagement.bean.TaskState.OnGoing;
import static com.ssthouse.supermarketmanagement.bean.TaskState.UnConfirmed;

/**
 * Created by ssthouse on 21/11/2016.
 */

public class TaskEditAty extends AppCompatActivity {

    public static final String KEY_INTENT_TASK = "task";

    public static void startForResult(Activity context, Task task, int requestCode) {
        Intent intent = new Intent(context, TaskEditAty.class);
        intent.putExtra(KEY_INTENT_TASK, task);
        context.startActivityForResult(intent, requestCode);
    }

    private Task mTask;

    private ProgressDialog waitDialog;

    @BindView(R.id.id_tb)
    Toolbar toolbar;

    @BindView(R.id.id_tv_staff_name)
    TextView tvStaffName;

    @BindView(R.id.id_tv_deliver_time)
    TextView tvDeliverTime;

    @BindView(R.id.id_tv_finish_time)
    TextView tvFinishTime;

    @BindView(R.id.id_tv_task_content)
    TextView tvTaskContent;

    @BindView(R.id.id_tv_other_note)
    TextView tvOtherNote;


    @BindView(R.id.id_tv_steps)
    TextView tvSteps;
    @BindView(R.id.id_et_new_step)
    EditText etNewStep;
    @BindView(R.id.id_btn_add_step)
    Button btnAddStep;

    @BindView(R.id.id_sp_task_status)
    Spinner spTaskStatus;

    @BindView(R.id.id_btn_ensure_change)
    Button btnEnsureChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_datail);
        ButterKnife.bind(this);
        mTask = (Task) getIntent().getSerializableExtra(KEY_INTENT_TASK);
        loadTaskData();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadTaskData() {
        tvStaffName.setText(mTask.getHandlerStaff());
        tvDeliverTime.setText(mTask.getDeliverTime());
        tvFinishTime.setText(mTask.getFinishTime());
        tvTaskContent.setText(mTask.getTaskContent());
        tvOtherNote.setText(mTask.getOtherNote());

        tvSteps.setText(mTask.getStepsStr());
        btnAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNewStep.getText().toString().trim())) {
                    return;
                }
                mTask.addTaskStep(etNewStep.getText().toString());
                tvSteps.setText(mTask.getStepsStr());
            }
        });

        switch (TaskState.valueOf(mTask.getTaskState())) {
            case UnConfirmed:
                spTaskStatus.setSelection(0);
                break;
            case OnGoing:
                spTaskStatus.setSelection(1);
                break;
            case Finished:
                spTaskStatus.setSelection(2);
                break;
        }
        spTaskStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mTask.setTaskState(UnConfirmed.toString());
                        break;
                    case 1:
                        mTask.setTaskState(OnGoing.toString());
                        break;
                    case 2:
                        mTask.setTaskState(Finished.toString());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnEnsureChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitDialog("Wait");
                mTask.saveExistObjectToCloud(new CallBack() {
                    @Override
                    public void callback() {
                        waitDialog.dismiss();
                        finish();
                    }
                });
            }
        });
    }

    private void showWaitDialog(String msg) {
        waitDialog = new ProgressDialog(this);
        //设置风格为圆形
        waitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        waitDialog.setTitle(null);
        waitDialog.setIcon(null);
        //设置提示信息
        waitDialog.setMessage(msg);
        //设置是否可以通过返回键取消
        waitDialog.setCancelable(false);
        waitDialog.setIndeterminate(false);
        waitDialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
