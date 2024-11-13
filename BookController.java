
/* Authors: Chitrangada Juneja and Dylan Coles
 * Net IDs: cj21 and colesdylan12
 * the Controller class that facilitates communication between
 * the model and view, and displays appropriate prompts 
 * and error messages to the view. 
 * @param model object. it has been declared final in the view 
 * as well as here, to make it clear that it needs to be immutable
 * once created.
 * 
 * 
 */



import java.util.ArrayList;

public class BookController {

	private final BookModel MODEL;

	public BookController(BookModel model) {
		MODEL = model;
	}
	

	public ArrayList<Book> searchLibrary(int searchBy, String search) {
		 ArrayList <Book> results=MODEL.search(searchBy, search);
		 ArrayList<Book> results_copy= new ArrayList<Book>();
		 
		 for ( int i=0; i<results.size(); i++) {
			 results_copy.add(new Book (results.get(i).getTitle(), results.get(i).getAuthor()));
			 
			 
		 }
		return results_copy;
		
	}

	public void addBookToLibrary(String title, String author) {
		// we create this book object internally so that the view 
		// never has to deal with book objects.
		Book book = new Book(title.toUpperCase(), author.toUpperCase());
		MODEL.addBook(book);
		
	}

	public int readBook(String title, String author) {
		// it is possible the book doesnt exist, so we create a flag
		// to check accordingly.
		
		int n= MODEL.setToRead(title);
		return n;
		
			
	}
		
	

	public int rateBook(String title, String author, int rating) {
		Book book = new Book(title.toUpperCase(), author.toUpperCase());
		int n= MODEL.rate(title, rating);
		// if 1 is returned , it means we couldnt find the book in the library
		//to rate
		return n;
	}

	public ArrayList<Book> getBooksFromLibrary(int searchBy) {
		/*
		 * since we have 2 overloaded versions, we call 
		 * the ones according to their method signatures as 
		 * defined by the model.
		 */
		ArrayList<Book> sorted_books=new ArrayList<Book>();
		if (searchBy == 0) {
			sorted_books=MODEL.getBooks(searchBy);
		} 
		else if (searchBy == 1) 
		{
			sorted_books=MODEL.getBooks(searchBy);
		} 
		else if (searchBy == 2){
			
			sorted_books= MODEL.getBooks(searchBy, "read");
			// the flag check notifies us if the list is empty 
			
			
		}
		else {
			sorted_books= MODEL.getBooks(searchBy,"unread");
			
		}
		
		return sorted_books;
	}

	public String[] suggestBook() {
		Book suggestion = MODEL.suggestRead();
		// we get the book object, but we deal with it internally instead of 
		// sending it to the view.
		if (suggestion==null) {
			return null;
		}
		String[] bookInfo = { suggestion.getTitle(), suggestion.getAuthor() };
		return bookInfo;
	}

	public void addListOfBooks(String fileName) {
		MODEL.addBooks(fileName);
	}
	
}
