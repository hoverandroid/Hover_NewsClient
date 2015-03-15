package com.hover.newsclientapp2.DB;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "mtable.db";
	private final static int DATABASE_VERSION = 1;
	private final static String ID = "id";
	private final static String TABLE_NAME = "categorys";
	public final static String CATEGORY_ID = "categoryid";
	public final static String STATUS = "status";

	public DBHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 创建table
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + ID
				+ " INTEGER primary key autoincrement, " + CATEGORY_ID
				+ " text, " + STATUS + " text);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public Map<String, String> selectAll() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		Map<String, String> map = new HashMap<String, String>();
		while (cursor.moveToNext()) {
			String categoryid = cursor.getString(cursor
					.getColumnIndex(CATEGORY_ID));
			String status = cursor.getString(cursor.getColumnIndex(STATUS));
			map.put(categoryid, status);
		}
		return map;
	}

	public boolean isHasRecord() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor.moveToNext();
	}

	// 增加操作
	public long insert(String categoryid, String status) {
		SQLiteDatabase db = this.getWritableDatabase();
		/* ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(CATEGORY_ID, categoryid);
		cv.put(STATUS, status);
		long row = db.insert(TABLE_NAME, null, cv);
		return row;
	}

	// 删除操作
	public void delete(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = ID + " = ?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);
	}

	// 修改操作
	public void update(String categoryid, String status) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = CATEGORY_ID + " = ?";
		String[] whereValue = { categoryid };

		ContentValues cv = new ContentValues();
		// cv.put(CATEGORY_ID, categoryid);
		cv.put(STATUS, status);
		db.update(TABLE_NAME, cv, where, whereValue);
	}

	public boolean tabbleIsExist(String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
					+ TABLE_NAME.trim() + "' ";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
}