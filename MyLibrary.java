/* @author Chitrangada Juneja , Dylan Coles
 * Net IDs: cj21 and colesdylan12
 * Main View class that communicates with the Model through the CONTROLLER
 * and displays appropriate prompts and error messages. Encapsulation is maintained
 * by communicating to the model object through the CONTROLLER object. We made the 
 * model object and CONTROLLER object final to clearly state on our end that once it is made , 
 * it cannot be modified or created again. this ensures no possible interference. Our View class
 * also ensures flexibility as the user interface can be modified without causing problems to other
 * classes.
 * we also make sure that no Book object is made in the view; if it is required to be made, 
 * we send the details to the controller, who makes and disposes of it appropriately.
 * @param input: File name
 * @pre no null inputs
 * 
 */


import java.util.ArrayList;
import java.util.Scanner;


public class MyLibrary {
	public static void main(String[] args) {
		// step 1: create the library using scanner; note that we can further
		// add to the library using both addBook and addBooks
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Welcome to the library! Enter any of the commands to process books in our database. ");
		System.out.println("Caution: Database is empty. Please add books/ file containing books to process further!");
		
		System.out.println("Please enter 'exit' if you wish to close the program");
		
		final  BookModel MODEL = new BookModel();
		final BookController CONTROLLER = new BookController(MODEL);
		String input = "begin";
		
		// create the library model and CONTROLLER
		
		// prompt the user for various commands to interact with the library
		while (!input.equals("exit")) {
			System.out.println();
			System.out.println("Please enter the command you wish to execute:");
			System.out.println("search -> Search for a book by 'title', 'author', or 'rating'.");
			System.out.println("addBook -> Add a book to the collection.");
			System.out.println("setToRead -> Set a book to 'read'.");
			System.out.println("rate -> Rate a book with a rating between 1 and 5 (inclusive)");
			System.out.println("getBooks -> Retrieve and display your books, sorted by 'title', "
					+ "'author', and whether or not they have been 'read'.");
			System.out.println("suggestRead -> Get a suggestion for a book to read.");
			System.out.println("addBooks -> Add multiple books to the library at once.");
			System.out.println();

			System.out.println("Please enter 'exit' if you wish to close the program");
			System.out.println();

			input = keyboard.nextLine();

			// Do actions based on inputs. this switch cases provides us a simple 
			// yet effective way to keep our program running - whilst looking much neater
			// than if-else blocks. 
			switch (input) {
			case "search":
				System.out.println(
						"Please enter how you wish to search for a book: -> " + "'title'; 'author'; " + "'rating'");
				String searchInput = keyboard.nextLine();
				ArrayList<Book>results= new ArrayList<Book>();
				switch (searchInput) {
				case "title":
					System.out.println("Please enter the title of the book you wish to find: ");
					String searchTitle = keyboard.nextLine();
					results=CONTROLLER.searchLibrary(0, searchTitle.toUpperCase());
					break;
					
				case "author":
					System.out.println("Please enter the author of the books you wish to find: ");
					String searchAuthor = keyboard.nextLine();
					results=CONTROLLER.searchLibrary(1, searchAuthor.toUpperCase());
					break;
					
				case "rating":
					System.out.println(
							"Please enter the rating of the books you wish to find (between 1 and 5 inclusive): ");
					String searchRating = keyboard.nextLine();
					results=CONTROLLER.searchLibrary(2, searchRating);
					break;
					
				default:
					System.out.println("Please enter a valid command");
				}
				if (results.isEmpty()) {
					System.out.println("Sorry, could not find any books matching search specifications!");
					
				}
				else {
					System.out.println("the following books match your specifications: ");
					for ( int k=0; k<results.size();k++) {
						System.out.println(results.get(k));
					}
					System.out.println("Search complete!");
					
				}
				
				break;
				
			case "addBook":
				// modifying addBook to add a single book.
				System.out.println("Please enter the book's title: ");
				String newTitle = keyboard.nextLine();
				System.out.println("Please enter the book's author: ");
				String newAuthor = keyboard.nextLine();
				CONTROLLER.addBookToLibrary(newTitle, newAuthor);
				System.out.println("Successfully added book to database!");
				break;
				
			case "setToRead":
				System.out.println("Please enter the book's title: ");
				String thisTitle = keyboard.nextLine();
				System.out.println("Please enter the book's author: ");
				String thisAuthor = keyboard.nextLine();
				int n=CONTROLLER.readBook(thisTitle, thisAuthor);
				if (n==1) {
					System.out.println("Sorry that book doesn't exist in our database!");
					
				}
				else {
					System.out.println("Book set to read!");
				}
				break;
				
			case "rate":
				System.out.println("Please enter the title of the book you want to rate:");
				String thisTitle2 = keyboard.nextLine();
				System.out.println("Please enter the author of the book you want to rate: ");
				String thisAuthor2 = keyboard.nextLine();
				System.out.println("Rate the book between 1 and 5 (inclusive)");
				String rating = keyboard.nextLine();
				int myRating = Integer.parseInt(rating);
				int check= CONTROLLER.rateBook(thisTitle2,thisAuthor2 , myRating);
				if (check==0) {
					System.out.println("Successfully rated the book!");
				}
				else {
					System.out.println("Could not find a book matching specifications! ");
				}
				
				break;
				
			case "getBooks":
				System.out.println("Please enter whether you would like to get your books by 'author', 'title', 'read', or 'unread'");
				String getInput = keyboard.nextLine();
				ArrayList<Book> sorted_books=new ArrayList<Book>();
				switch (getInput) {
				case "title":
					System.out.println("Sorting books by title: ");
					sorted_books=CONTROLLER.getBooksFromLibrary(0);
					break;
					
				case "author":
					System.out.println("Sorting books by author: ");
					sorted_books=CONTROLLER.getBooksFromLibrary(1);
					break;
					
				case "read":
					System.out.println("Getting all read books");
					sorted_books=CONTROLLER.getBooksFromLibrary(2);
					if (sorted_books.isEmpty()) {
						System.out.println("Sorry, no read books so far!");
					}
					
					break;
					
				case "unread":
					System.out.println("Getting all unread books");
					sorted_books=CONTROLLER.getBooksFromLibrary(3);
					if (sorted_books.isEmpty()) {
						System.out.println("Sorry, no unread books so far!");
					}
					break;
					
				
					default:
					System.out.println("Please enter a valid command!");
				}
				//prints out all the books that are sorted
				if (!sorted_books.isEmpty()) {
					for(int i=0; i<sorted_books.size();i++) {
						System.out.println(sorted_books.get(i));
						
					}
					System.out.println("Successfully displayed sorted books!");
					
				}
				
				
				break;
				
			case "suggestRead":
				String[] bookInfo = CONTROLLER.suggestBook();
				if (bookInfo==null) {
					System.out.println("Sorry, no unread books to suggest!");
				}
				else {
					System.out.println("We have a book suggestion for you ! It is: " + bookInfo[0] + " by " + bookInfo[1]);
					
				}
				
				break;
				
			case "addBooks":
				// modifying addBooks to give a file name.
				System.out.println("Please enter the name of a file with books to add. ");
				String fileName = keyboard.nextLine();
				CONTROLLER.addListOfBooks(fileName);
				System.out.println("Books added!");
				break;
				
			case "exit":
				System.out.println("Closing library");
				keyboard.close();
				return;
				
			default:
				System.out.println("Invalid command. Please enter a command to access the library.");
			}
		}

		System.out.println("Closing library.");
		keyboard.close();
		System.exit(0);
	}
}