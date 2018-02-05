package com.nicklasoxhammar.makeyourownquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.nicklasoxhammar.makeyourownquiz.listeners.OnDatabaseChangedListener;

/**
 * Created by Nick on 2018-02-05.
 */

public class DBHelper extends SQLiteOpenHelper {
    private Context mContext;

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;

    public static final String DATABASE_NAME = "saved_quizzes.db";
    private static final int DATABASE_VERSION = 1;


    public static abstract class DBHelperItem implements BaseColumns {
        public static final String TABLE_NAME = "saved_quizzes";

        public static final String COLUMN_NAME_QUIZ_NAME = "quiz_name";
        public static final String COLUMN_NAME_QUIZ_FILE_PATH = "file_path";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBHelperItem.TABLE_NAME + " (" +
                    DBHelperItem._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    DBHelperItem.COLUMN_NAME_QUIZ_NAME + TEXT_TYPE + COMMA_SEP +
                    DBHelperItem.COLUMN_NAME_QUIZ_FILE_PATH + TEXT_TYPE + COMMA_SEP + ")";

    @SuppressWarnings("unused")
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBHelperItem.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static void setOnDatabaseChangedListener(OnDatabaseChangedListener listener) {
        mOnDatabaseChangedListener = listener;
    }

    public QuizItem getItemAt(int position){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                DBHelperItem._ID,
                DBHelperItem.COLUMN_NAME_QUIZ_NAME,
                DBHelperItem.COLUMN_NAME_QUIZ_FILE_PATH
        };

        Cursor c = db.query(DBHelperItem.TABLE_NAME, projection, null, null, null, null, null);
        if (c.moveToPosition(position)){
            QuizItem item = new QuizItem();
            item.setId(c.getInt(c.getColumnIndex(DBHelperItem._ID)));
            item.setName(c.getString(c.getColumnIndex(DBHelperItem.COLUMN_NAME_QUIZ_NAME)));
            item.setFilePath(c.getString(c.getColumnIndex(DBHelperItem.COLUMN_NAME_QUIZ_FILE_PATH)));
            c.close();
            return item;
        }

        return null;
    }

    public void removeItemWithId(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = { String.valueOf(id) };
        db.delete(DBHelperItem.TABLE_NAME, "_ID=?", whereArgs);
    }

    public int getCount() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = { DBHelperItem._ID };
        Cursor c = db.query(DBHelperItem.TABLE_NAME, projection, null, null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;
    }

    public Context getContext() {
        return mContext;
    }

    public long addQuiz(String quizName, String filePath) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelperItem.COLUMN_NAME_QUIZ_NAME, quizName);
        cv.put(DBHelperItem.COLUMN_NAME_QUIZ_FILE_PATH, filePath);
        long rowId = db.insert(DBHelperItem.TABLE_NAME, null, cv);

        if (mOnDatabaseChangedListener != null) {
            mOnDatabaseChangedListener.onNewDatabaseEntryAdded();
        }

        return rowId;
    }

    public void renameItem(QuizItem item, String quizName, String filePath) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelperItem.COLUMN_NAME_QUIZ_NAME, quizName);
        cv.put(DBHelperItem.COLUMN_NAME_QUIZ_FILE_PATH, filePath);
        db.update(DBHelperItem.TABLE_NAME, cv,
                DBHelperItem._ID + "=" + item.getId(), null);

        if (mOnDatabaseChangedListener != null) {
            mOnDatabaseChangedListener.onDatabaseEntryRenamed();
        }

    }

    public long restoreQuiz(QuizItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelperItem.COLUMN_NAME_QUIZ_NAME, item.getName());
        cv.put(DBHelperItem.COLUMN_NAME_QUIZ_FILE_PATH, item.getFilePath());
        cv.put(DBHelperItem._ID, item.getId());
        long rowId = db.insert(DBHelperItem.TABLE_NAME, null, cv);
        if (mOnDatabaseChangedListener != null) {
            //mOnDatabaseChangedListener.onNewDatabaseEntryAdded();
        }
        return rowId;
    }

}
