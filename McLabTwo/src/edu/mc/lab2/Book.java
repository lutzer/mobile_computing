package edu.mc.lab2;

import android.util.Log;

public class Book {

	private String author;
	private String title;
	private float price;
	private String isbn;
	private String course;
	
	public int id = -1; // -1 not in database
	
	public Book(String author, String title, int price, String isbn, String course) {
		this.author = author;
		this.title = title;
		this.price = price;
		this.isbn = isbn;
		this.course = course;
	}
	
	public Book() {
		this.author = "";
		this.title = "";
		this.price = 0;
		this.isbn = "";
		this.course = "";
	}
	
	// getter and setter methods
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	@Override
	public boolean equals(Object obj) {
	       // check for reference equality
		Log.e("INFO","called equals");
	       if(this == obj) return true;

	       // type check
	       if( !(obj instanceof Book) ) return false;

	       Book book = (Book)obj;
	       
	       
	       
	       return (this.author == book.getAuthor()
	    		   && this.title == book.getTitle()
	    		   && this.course == book.getCourse()
	    		   && this.price == book.getPrice()
	    		   && this.isbn == book.getIsbn());
	   }
	
}
