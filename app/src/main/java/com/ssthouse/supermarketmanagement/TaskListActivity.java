package com.ssthouse.supermarketmanagement;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.ssthouse.supermarketmanagement.bean.Task;
import com.ssthouse.supermarketmanagement.detail.TaskEditAty;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private List<Task> taskList = new ArrayList<>();

    private static final int MSG_RECEIVE_TASK = 1000;

    private ListView lvTaskList;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_RECEIVE_TASK) {
                receiveTasksFromCloud();
                handler.sendEmptyMessageDelayed(MSG_RECEIVE_TASK, 3000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initTaskListView();
        autoReceiveTasks();
    }

    private void initTaskListView() {
        lvTaskList = (ListView) findViewById(R.id.id_lv_main);
        lvTaskList.setAdapter(taskLvAdapter);
        lvTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskEditAty.start(TaskListActivity.this, taskList.get(position));
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.id_tb));
        getSupportActionBar().setTitle("My Task");
    }

    private BaseAdapter taskLvAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return taskList.size();
        }

        @Override
        public Object getItem(int position) {
            return taskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = LayoutInflater.from(TaskListActivity.this).inflate(R.layout.item_task_lv, parent, false);
            TextView tvStaffName = (TextView) itemView.findViewById(R.id.id_tv_staff_name);
            tvStaffName.setText(taskList.get(position).getHandlerStaff());
            TextView tvContent = (TextView) itemView.findViewById(R.id.id_tv_content);
            tvContent.setText(taskList.get(position).getTaskContent());
            return itemView;
        }
    };

    //auto check the number of the task on cloud
    //if more than locale -> download the new list
    private void autoReceiveTasks() {
        handler.sendEmptyMessage(MSG_RECEIVE_TASK);
    }

    private void receiveTasksFromCloud() {
        AVQuery<AVObject> query = new AVQuery<>(Task.CLASS_NAME);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size() > taskList.size()) {
                    taskList.clear();
                    for (AVObject avObject : list) {
                        taskList.add(Task.getTaskFromCloudObj(avObject));
                    }
                    //update view
                    taskLvAdapter.notifyDataSetChanged();
                }
            }
        });
    }


}
