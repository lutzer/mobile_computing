package edu.mc.lab2;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookDatabase {

	/*
	 *  class manages the connection and setup to the sql database
	 */
	private class SqlDatabaseHelper extends SQLiteOpenHelper {
	
		public final String FIELDS[] = {"author","title","isbn","course","price"};
		
		public final String TABLE_NAME = "Books";
		
		private final static String DATABASE_NAME = "bookManager.db";
		private final static int DATABASE_VERSION = 1;
	
		// Database creation
		private  final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME 
				+ "('id' integer primary key autoincrement, "
				+ FIELDS[0] + " TEXT,"
				+ FIELDS[1] + " TEXT,"
				+ FIELDS[2] + " TEXT,"
				+ FIELDS[3] + " TEXT,"
				+ FIELDS[4] + " DECIMAL(7,2)"
				+ ");";
	
		public SqlDatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
	
		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL(DATABASE_CREATE);
			
			Log.e("INFO","created database");
			
			ArrayList<Book> books = new ArrayList<Book>();
			
			// add a few books
			Book book1 = new Book("Michael Ende", "Momo", 22, "012", "Book Circle");
			books.add(book1);
			Book book2 = new Book("Johann Wolfgang von Goethe", "Die Leiden des Jungen Werther", 5, "0123", "German Literature");
			books.add(book2);
			Book book3 = new Book("William Gibson", "Neuromancer", 10, "01234", "Cyborgs and Post-Feminism");
			books.add(book3);
			Book book4 = new Book("Rafik Schami", "A Hand Full of Stars", 4, "012345", "The East");
			books.add(book4);
			Book book5 = new Book("Daniel Kehlmann", "Die Vermessung der Welt", 25, "0123456", "German Literature");
			books.add(book5);
			
			// insert the data
			for (Book book : books) {
				database.insert(sqlHelper.TABLE_NAME, null, BookDatabase.createContentValues(book));
			}
			
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(BookDatabase.class.getName(),
					"Upgrading database from version " + oldVersion + " to "
							+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	} 
	
	SqlDatabaseHelper sqlHelper;
	SQLiteDatabase database;
	
	public BookDatabase(Context context) {
		sqlHelper = new SqlDatabaseHelper(context);
		database = sqlHelper.getWritableDatabase();
	}

	public void close() {
		sqlHelper.close();
	}
	
	/*
	 * returns books from database
	 */
	public ArrayList<Book> getBooks() {

		ArrayList<Book> books = new ArrayList<Book>();
		
		Cursor cursor = database.rawQuery("SELECT * FROM " + sqlHelper.TABLE_NAME, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Book book = cursorToBook(cursor);
			books.add(book);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return books;
	}
	
	/*
	 * creates book object from cursor data
	 */
	private Book cursorToBook(Cursor cursor) {
		Book book = new Book();
		book.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
		book.setTitle(cursor.getString(cursor.getColumnIndex("title")));
		book.setCourse(cursor.getString(cursor.getColumnIndex("course")));
		book.setIsbn(cursor.getString(cursor.getColumnIndex("isbn")));
		book.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
		return book;
	}
	
	private static ContentValues createContentValues(Book book) {
		ContentValues newCon = new ContentValues();
		newCon.put("author", book.getAuthor());
		newCon.put("title", book.getTitle());
		newCon.put("course", book.getCourse());
		newCon.put("price", book.getPrice());
		newCon.put("isbn", book.getIsbn());
		
		return newCon;
	}

	public void saveBooks(ArrayList<Book> books) {
		// first drop all data
		database.delete(sqlHelper.TABLE_NAME,null,null);
		
		// insert new data
		for (Book book : books) {
			database.insert(sqlHelper.TABLE_NAME, null, createContentValues(book));
		}
		
		Log.e("INFO","saved database");
		
	}

}
