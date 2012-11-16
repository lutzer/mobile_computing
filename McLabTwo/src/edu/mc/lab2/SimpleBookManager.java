package edu.mc.lab2;

import java.util.ArrayList;

import android.content.Context;


public class SimpleBookManager implements BookManagerInterface {
	
	private static SimpleBookManager instance = null;

	private ArrayList<Book> books;
	
	private BookDatabase database;
	
	private SimpleBookManager() {
		
		books = new ArrayList<Book>();
		
	}
	
	/*
	 * get the instance of the singleton
	 */
	public static SimpleBookManager getInstance() {
        if (instance == null) {
            instance = new SimpleBookManager();
        }
        return instance;
    }

	@Override
	public int count() {
		return books.size();
	}

	@Override
	public Book getBook(int index) {
		if (books.size() < index)
			return null;
		
		return books.get(index);
	}

	@Override
	public Book createBook() {
		Book newBook = new Book();
		books.add(newBook);
		
		database.addBook(newBook);
		
		return newBook;
	}
	
	/*
	 * updates the database entry of book
	 * (should be called after every change of data, 
	 * else the book data will be reset on next restart)
	 */
	public void updateBook(Book book) {
		database.updateBook(book);
	}

	@Override
	public ArrayList<Book> getAllBooks() {
		return books;
	}

	@Override
	public void removeBook(Book book) {
		database.deleteBook(book);
		books.remove(book);
	}

	@Override
	public void moveBook(int from, int to) throws Exception {
		if (from >= books.size() && to >= books.size())
			throw new Exception("Cannot move books. one or both the books not in the database");
		
		Book fromBook = books.get(from);
		books.set(from, books.get(to));
		books.set(to,fromBook);
	}

	@Override
	public float getMinPrice() {
		
		if (books.isEmpty())
			return 0;
		
		float minPrice = Float.MAX_VALUE;
		for (Book book : books) {
			if (book.getPrice() < minPrice)
				minPrice = book.getPrice();
		}
		
		return minPrice;
	}

	@Override
	public float getMaxPrice() {
		
		if (books.isEmpty())
			return 0;
		
		float maxPrice = 0;
		for (Book book : books) {
			if (book.getPrice() > maxPrice)
				maxPrice = book.getPrice();
		}
		
		return maxPrice;
	}

	@Override
	public float getMeanPrice() {
		if (books.isEmpty())
			return 0;
		
		return this.getTotalCost()/(float)books.size();
	}

	@Override
	public int getTotalCost() {
		int sum = 0;
		for (Book book : books) {
			sum += book.getPrice();
		}
		
		return sum;
	}

	@Override
	/*
	 * drops the whole db and replaces them with the current arraylist
	 * (this function works but is not used for performance reasons)
	 */
	public void saveChanges() throws Exception {
		if (database == null)
			throw new Exception("Can not save data, no database connection");
		
		database.saveBooks(books);
	}
	
	/*
	 * connect to database, call this on programm start
	 */
	public void connectDatabase(Context context) {
		if (database == null)
			database = new BookDatabase(context);
	}
	
	/*
	 * close the connection to database, call this on exit
	 */
	public void closeDatabase() {
		if (database == null)
			database.close();
	}
	
	/*
	 * loads books from database
	 */
	public void loadData() throws Exception {
		if (database == null)
			throw new Exception("Can not load data, no database connection");
		
		books = database.getBooks();
	}
	
	/*
	 * returns a string containing all the book values
	 */
	public String getBookString(int index) {
		if (index >= books.size())
			return null;
		
		Book book = getBook(index);
		
		return 	"Author: " +book.getAuthor() + ", Title: " + book.getTitle() 
				+ ", Price " + book.getPrice() + ", ISBN: " + book.getIsbn()
				+ ", Course: " + book.getCourse();
		
	}

}
