package com.zalo.trainingmenu.downloader.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zalo.trainingmenu.App;
import com.zalo.trainingmenu.downloader.model.DownloadItem;
import com.zalo.trainingmenu.downloader.model.PartialInfo;
import com.zalo.trainingmenu.downloader.model.TaskInfo;

import java.util.ArrayList;
import java.util.List;

public class DownloadDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DownloadDBHelper";

    // Database Name
    public static final String DATABASE_NAME = "downloader_db";
    private static final int DATABASE_VERSION = 1;
    private static DownloadDBHelper sDownloadDBHelper;

    public static DownloadDBHelper getInstance() {
        if(sDownloadDBHelper ==null) sDownloadDBHelper = new DownloadDBHelper(App.getInstance().getApplicationContext());
        return sDownloadDBHelper;
    }

    public static void destroy() {
        sDownloadDBHelper.close();
        sDownloadDBHelper = null;
    }

    private DownloadDBHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TaskInfo.CREATE_TABLE);
        sqLiteDatabase.execSQL(PartialInfo.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TaskInfo.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PartialInfo.TABLE_NAME);
    }

    public List<TaskInfo> getSavedTaskFromDatabase() {
        List<TaskInfo> taskInfoList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TaskInfo.TABLE_NAME,null,null,null,null,null,TaskInfo.EXTRA_ID);
        if(cursor!=null) {
            if(cursor.moveToFirst())
            do {
                try {
                    TaskInfo info = TaskInfo.restoreInstance(db,cursor);
                    taskInfoList.add(info);
                } catch (Exception ignored) {}
            } while (cursor.moveToNext());
        }
        db.close();
        return taskInfoList;
    }

    public synchronized void saveTask(TaskInfo info) {
        SQLiteDatabase db = getWritableDatabase();
        info.save(db);
        db.close();

    }

    public synchronized long generateNewTaskId(DownloadItem item) {
        SQLiteDatabase db = getWritableDatabase();
        TaskInfo sample = new TaskInfo(1,-1,item.getFileTitle(),item.getDirectoryPath(),item.getUrlString());
        long id = db.insert(TaskInfo.TABLE_NAME,null,sample.getValues());
        db.close();
        return id;
    }
    public synchronized long generateNewPartialTaskId(long startByte, long endByte) {
        SQLiteDatabase db = getWritableDatabase();
        PartialInfo info = new PartialInfo(startByte, endByte, 1);
        long id = db.insert(PartialInfo.TABLE_NAME,null,info.getValues());
        db.close();
        return id;
    }

    public synchronized void deleteTask(TaskInfo info) {
        String partial_ids = info.getPartialInfoIds();
        SQLiteDatabase db = getWritableDatabase();

        // delete partial list
        db.delete(PartialInfo.TABLE_NAME,PartialInfo.EXTRA_ID+" IN ("+partial_ids+" )",null);
        db.delete(TaskInfo.TABLE_NAME,TaskInfo.EXTRA_ID+" = "+ info.getId(),null);
        db.close();
    }

    public void deleteAllTasks() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TaskInfo.TABLE_NAME, null, null);
        db.delete(PartialInfo.TABLE_NAME,null,null);
        db.close();
    }
}