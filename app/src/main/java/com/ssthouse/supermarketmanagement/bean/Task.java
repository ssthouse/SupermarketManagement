package com.ssthouse.supermarketmanagement.bean;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.ssthouse.supermarketmanagement.detail.CallBack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssthouse on 20/11/2016.
 */
public class Task implements Serializable {

    public static final String CLASS_NAME = "Task";

    private static final String KEY_HANDLER_STAFF = "handlerStaff";
    private static final String KEY_DELIVER_TIME = "deliverTIme";
    private static final String KEY_FINISH_TIME = "finishTime";
    private static final String KEY_TASK_CONTENT = "taskContent";
    private static final String KEY_OTHER_NOTE = "otherNote";
    private static final String KEY_STEP_LIST = "stepList";
    private static final String KEY_TASK_STATE = "taskState";

    //TODO added field
    private static final String KEY_OBJECT_ID = "id";
    private String objId;

    //the staff who handle the task
    private String handlerStaff = "";

    //begin time
    private String deliverTime = "";
    //end time
    private String finishTime = "";

    //content
    private String taskContent = "";
    //other note
    private String otherNote = "";

    /**
     * record the step change of Task
     */
    private List<String> stepList = new ArrayList<>();
    private String taskState = TaskState.UnConfirmed.toString();

    private Task() {
    }

    public Task(String taskContent) {
        this.taskContent = taskContent;
        this.taskState = TaskState.UnConfirmed.toString();
    }

    public String getTaskDetail() {
        String stepStr = "";
        for (String step : stepList) {
            stepStr += step + "\n";
        }
        return "handler: " + handlerStaff + "\n\n"
                + "delivered date:" + deliverTime + "\n\n"
                + "steps: " + stepStr + "\n\n"
                + "content: " + taskContent + "\n\n"
                + "otherNote: " + otherNote + "\n\n"
                + "finish time: " + finishTime;
    }

    public String getStepsStr() {
        String stepStr = "";
        for (String step : stepList) {
            stepStr += step + "\n";
        }
        return stepStr;
    }

    /**
     * save this task to cloud to share with staff
     */
    public void saveToCloud() {
        AVObject taskObject = new AVObject(CLASS_NAME);
        taskObject.put(KEY_HANDLER_STAFF, handlerStaff);
        taskObject.put(KEY_DELIVER_TIME, deliverTime);
        taskObject.put(KEY_FINISH_TIME, finishTime);
        taskObject.put(KEY_TASK_CONTENT, taskContent);
        taskObject.put(KEY_OTHER_NOTE, otherNote);
        taskObject.put(KEY_STEP_LIST, stepList);
        taskObject.put(KEY_TASK_STATE, taskState);
        taskObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                Log.e("Task", "save success");
            }
        });
    }

    public void saveExistObjectToCloud(final CallBack callBack) {
        if (objId == null || objId.length() == 0) {
            Log.e("Task", "obj id is empty");
            return;
        }
        AVQuery<AVObject> existObj = new AVQuery<>(CLASS_NAME);
        existObj.getInBackground(objId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                avObject.put(KEY_HANDLER_STAFF, handlerStaff);
                avObject.put(KEY_DELIVER_TIME, deliverTime);
                avObject.put(KEY_FINISH_TIME, finishTime);
                avObject.put(KEY_TASK_CONTENT, taskContent);
                avObject.put(KEY_OTHER_NOTE, otherNote);
                avObject.put(KEY_STEP_LIST, stepList);
                avObject.put((KEY_TASK_STATE), taskState);
                avObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        Log.e("Task", "save success");
                        callBack.callback();
                    }
                });
            }
        });
    }

    /**
     * transfer cloud obj to Task
     *
     * @param avObject
     * @return
     */
    public static Task getTaskFromCloudObj(AVObject avObject) {
        Task task = new Task();
        task.setHandlerStaff(avObject.getString(KEY_HANDLER_STAFF));
        task.setDeliverTime(avObject.getString(KEY_DELIVER_TIME));
        task.setFinishTime(avObject.getString(KEY_FINISH_TIME));
        task.setTaskContent(avObject.getString(KEY_TASK_CONTENT));
        task.setOtherNote(avObject.getString(KEY_OTHER_NOTE));
        task.setStepList(avObject.getList(KEY_STEP_LIST));
        task.setObjId(avObject.getObjectId());
        return task;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public void addTaskStep(String newStep) {
        stepList.add(newStep);
    }

    public List<String> getStepList() {
        return stepList;
    }

    public void setStepList(List<String> stepList) {
        this.stepList = stepList;
    }

    @Override
    public String toString() {
        return "handler name:" + handlerStaff + "    deliver time: " + deliverTime;
    }


    public String getHandlerStaff() {
        return handlerStaff;
    }

    public void setHandlerStaff(String handlerStaff) {
        this.handlerStaff = handlerStaff;
    }

    public String getOtherNote() {
        return otherNote;
    }

    public void setOtherNote(String otherNote) {
        this.otherNote = otherNote;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
}
