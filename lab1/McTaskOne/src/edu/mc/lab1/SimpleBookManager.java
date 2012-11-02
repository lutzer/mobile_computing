package edu.mc.lab1;

import java.util.ArrayList;

public class SimpleBookManager implements BookManagerInterface {

	private ArrayList<Book> books;
	
	public SimpleBookManager() {
		
		books = new ArrayList<Book>();
		
		// add a few books
		Book book1 = new Book("Momo", "Michal Ende", 22, "012", "Book Circle");
		books.add(book1);
		Book book2 = new Book("Johann Wolfgang von Goethe", "Die Leiden des Jungen Werther", 5, "0123", "German Literature");
		books.add(book2);
		Book book3 = new Book("William Gibson", "Neuromancer", 10, "01234", "Cyborgs and Post-Feminism");
		books.add(book3);
		Book book4 = new Book("Rafik Schami", "A Hand Full of Stars", 4, "012345", "The East");
		books.add(book4);
		Book book5 = new Book("Daniel Kehlmann", "Die Vermessung der Welt", 25, "0123456", "German Literature");
		books.add(book5);
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
		
		return newBook;
	}

	@Override
	public ArrayList<Book> getAllBooks() {
		return books;
	}

	@Override
	public void removeBook(Book book) {
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
	public int getMinPrice() {
		
		if (books.isEmpty())
			return 0;
		
		int minPrice = Integer.MAX_VALUE;
		for (Book book : books) {
			if (book.getPrice() < minPrice)
				minPrice = book.getPrice();
		}
		
		return minPrice;
	}

	@Override
	public int getMaxPrice() {
		
		if (books.isEmpty())
			return 0;
		
		int maxPrice = Integer.MIN_VALUE;
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
	public void saveChanges() {
		// TODO Auto-generated method stub

	}
	
	public String getBookString(int index) {
		if (index >= books.size())
			return null;
		
		Book book = getBook(index);
		
		return 	"Author: " +book.getAuthor() + ", Title: " + book.getTitle() 
				+ ", Price " + book.getPrice() + ", ISBN: " + book.getIsbn()
				+ ", Course: " + book.getCourse();
		
	}

}
