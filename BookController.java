
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
	

	public void searchLibrary(int searchBy, String search) {
		 ArrayList <Book> results=MODEL.search(searchBy, search);
		if (results.isEmpty()) {
			System.out.println("Sorry, couldn't find any books matching your specifications! ");
			return;
		}
		System.out.println("The following books match your specifications: ");
		int l=results.size();
		for ( int i=0; i<l;i++) {
			System.out.println(results.get(i).toString());
	
		}
		 
		
	}

	public void addBookToLibrary(String title, String author) {
		// we create this book object internally so that the view 
		// never has to deal with book objects.
		Book book = new Book(title.toUpperCase(), author.toUpperCase());
		MODEL.addBook(book);
		
	}

	public void readBook(String title, String author) {
		// it is possible the book doesnt exist, so we create a flag
		// to check accordingly.
		Book  book = new Book(title.toUpperCase(), author.toUpperCase());
		int n= MODEL.setToRead(book);
		
		if (n==1) {
			System.out.println("Sorry, this book doesn't exist in our library!");
			
		}
		
	}

	public void rateBook(String title, String author, int rating) {
		Book book = new Book(title.toUpperCase(), author.toUpperCase());
		int n= MODEL.rate(book, rating);
		// if 1 is returned , it means we couldnt find the book in the library
		//to rate
		if (n==1) {
			System.out.println("Sorry, this book doesn't exist in our library!");
		}
	}

	public void getBooksFromLibrary(int searchBy) {
		/*
		 * since we have 2 overloaded versions, we call 
		 * the ones according to their method signatures as 
		 * defined by the model.
		 */
		if (searchBy == 0) {
			MODEL.getBooks(searchBy);
		} 
		else if (searchBy == 1) 
		{
			MODEL.getBooks(searchBy);
		} 
		else if (searchBy == 2){
			
			int check= MODEL.getBooks(searchBy, "read");
			// the flag check notifies us if the list is empty 
			if (check==1) {
				System.out.println("Sorry ! Looks like there are no read books !");
			}
			
		}
		else {
			int check= MODEL.getBooks(searchBy,"unread");
			if (check==1) {
				System.out.println("Sorry ! Looks like there are no unread books !");
			}
		}
	}

	public String[] suggestBook() {
		Book suggestion = MODEL.suggestRead();
		// we get the book object, but we deal with it internally instead of 
		// sending it to the view.
		String[] bookInfo = { suggestion.getTitle(), suggestion.getAuthor() };
		return bookInfo;
	}

	public void addListOfBooks(String fileName) {
		MODEL.addBooks(fileName);
	}
	
}
