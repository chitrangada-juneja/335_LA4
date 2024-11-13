/* Authors: Chitrangada Juneja and Dylan Coles
 * Net IDs: cj21 and colesdylan12
 * The Model class where we create our initial library using the 
 * given file name. 
 * Encapsulation is maintained by not communicating to the view directly
 * except in cases where we need to access private objects to print. we 
 * determined it was better to do those operations within model. However, 
 * in most cases, we send control back to the controller once an operation
 * has been performed to communicate with the user appropriately. this 
 * ensures no overlap with the view, thereby making it impossible for someone
 * to modify any internal data structures. 
 * @throws FileNotFoundException if the file doesn't exist. we exit in that case.
 * @pre no Null inputs
 * 
 * 
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class BookModel {

	// dictionary of unread books
	// we know status is going to be unread; no need for the key: value since every
	// value is UNREAD
	private ArrayList<Book> thisLibraryUnread;
	// dictionary of all books (read/unread) and their rating
	private HashMap<Book, Integer> thisLibraryRating ;
	// all values will be READ, so no need for a dictionary, we can use a list
	private ArrayList<Book> thisLibraryRead ;
	public BookModel() {
		 thisLibraryUnread = new ArrayList<Book>();
		 thisLibraryRating = new HashMap<Book, Integer>();
		 thisLibraryRead = new ArrayList<Book>();
		 
	
		
	}
	
	public void addBook(Book book) {
		thisLibraryUnread.add(book);
		thisLibraryRating.put(book, 0);
	}


	public void addBooks(String fileName) {
		/* 
		 * takes in the file name and tries to read it if it exists
		 * we use a try-catch block so that the error is dealt with here
		 * instead of passing it on.
		 */
		String curString;
		// read file line by line
		Scanner input;
		try {
			input = new Scanner(new File(fileName));
			while (input.hasNextLine()) {
				curString = input.nextLine();
				String[] bookArray = curString.split(";");
				// title:author
				// both are upper case so its easier to search when needed
				String title = bookArray[0].toUpperCase();
				String author = bookArray[1].toUpperCase();
				// since the structure of the example txt file has a "title ; author" 
				// we need this check so that we dont accidentally include it as a book
				// we skip if true.
				
				if (title.toUpperCase().equals("TITLE")&& author.toUpperCase().equals("AUTHOR")){
					continue;
				}
				
				Book book = new Book(title, author);
				// add to unread books
				thisLibraryUnread.add(book);
				// add to book Hash map
				thisLibraryRating.put(book, 0);
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such file exists ! Please try again !");
			e.printStackTrace();
			System.exit(0);
		}
	}

	public ArrayList<Book>  search(int searchBy, String search) {
		// searchBy==0 specifies search by title
		ArrayList <Book> results= new ArrayList<Book>();
		int found=0;
		if (searchBy == 0) {
			for (Entry<Book, Integer> mapElement : thisLibraryRating.entrySet()) {
				
				Book key = mapElement.getKey();
				if (key.getTitle().equals(search)) {
					results.add(key);
					found=1;
					
				}
			}
		}
		// searchBy==1 specifies search by author
		else if (searchBy == 1) {
			for (Entry<Book, Integer> mapElement : thisLibraryRating.entrySet()) {
				Book key = mapElement.getKey();
				if (key.getAuthor().equals(search)) {
					results.add(key);
					found=1;
				}
			}
		}
		// searchBy==2 specifies search by rating
		else {
			for (Entry<Book, Integer> mapElement : thisLibraryRating.entrySet()) {
				Book key = mapElement.getKey();
				int rating = mapElement.getValue();
				if (rating == Integer.valueOf(search)) {
					results.add(key);
					found=1;
				}
			}
		}
		// it is possible that results is empty; in that case, controller checks
		// and if true, it prints the appropriate message. 
		return results;
		
		

	}

	
	public int setToRead(String title) {
		// first we check if the book is even in the library
		// if not, we return control to the controller to display 
		// the right message.
		
		ArrayList <Book> result=this.search(0,title.toUpperCase());
		
		if (result.isEmpty()) {
			return 1;
		}
		boolean bookInReadList = false;
		for (int i = 0; i < thisLibraryRead.size(); i++) {
			if (thisLibraryRead.get(i) == result.get(0)) {
				bookInReadList = true;
			}
		}
		// check if the book has been set to "read"
		if (!bookInReadList) {
			// if not, add it to "read" list and remove from "unread" list
			thisLibraryRead.add(result.get(0));
			thisLibraryUnread.remove(result.get(0));
		}
		return 0;
	}

	public int rate(String title, int rating) {
		// set rating; can be changed multiple times
		// it is possible they are trying to rate a book 
		// that doesn't exist in the library. we return control
		// to the controller to display the right message in that case.
		
		ArrayList<Book> searched= search (0, title.toUpperCase());
		if (searched.isEmpty()) {
			return 1;
		}
		else {
			thisLibraryRating.replace(searched.get(0), rating);
			
		}
		return 0;
	}

	/*
	 * Overloading for getBooks seems like the best idea. We
	 * have 2 different cases: one where we search through the 
	 * hashmap thisLibraryRating, which contains ALL books, 
	 * regardless of their read/ unread status. this is used for 
	 * sorting through title and author.our second case is one
	 * where we search through an Arraylist- specifically the ones
	 * for read and unread. therefore, we have 2 versions of the 
	 * getBooks function. The parameters needed to be different for the
	 * overloading, so we just take an arbitrary string to change the
	 * method signature a bit.
	 * When it comes to the read/unread sorting, it is possible for 
	 * none of the books to be read, or unread. Therefore, if the list is 
	 * empty, we transfer control to the controller to display the appropriate
	 * message. 
	 * After getting the sorted titles/authors/unread/read status, we still 
	 * display the book objects with all their information- with title and author.
	 * therefore, we get a list of keys, and print the appropriate ones in the 
	 * right order.  
	 */ 
	
	public ArrayList<Book> getBooks ( int searchBy) {
		ArrayList<String> sorted = new ArrayList<String>();
		for (Entry<Book, Integer> mapElement : thisLibraryRating.entrySet()) {
			Book key = mapElement.getKey();
			if ( searchBy==0) {
				sorted.add(key.getTitle());
			}
			if (searchBy==1) {
				sorted.add(key.getAuthor());
			}
			
		}
		// sort the title list
		Collections.sort(sorted);
		// print out each title in sorted order
		List<Book> keys= new ArrayList(thisLibraryRating.keySet());
		ArrayList<Book> sorted_books=new ArrayList<Book>();
		
		for (int i=0; i<sorted.size();i++) {
			for ( int j=0; j<keys.size(); j++) {
				
				if (searchBy==0) {
					if ( (sorted.get(i)).equals(keys.get(j).getTitle()) ) {
						sorted_books.add (new Book (keys.get(j).getTitle(), keys.get(j).getAuthor()));
						
						//System.out.println(keys.get(j).toString());
						keys.remove(j);
						break;
					}
				}
				if ( searchBy==1) {
					if ( (sorted.get(i)).equals(keys.get(j).getAuthor()) ) {
						sorted_books.add (new Book (keys.get(j).getTitle(), keys.get(j).getAuthor()));
						//System.out.println(keys.get(j).toString());
						keys.remove(j);
						break;
					}
				}
			}
		}
		return sorted_books;
	}
	
	public ArrayList<Book> getBooks(int searchBy, String searchMode) {
		/* 2nd version of getBooks
		 */
		ArrayList<Book> sorted_books=new ArrayList<Book>();
		ArrayList <Book> ListMode;
		if (searchBy==2) {
			ListMode= thisLibraryRead;
			
		}
		else {
			ListMode= thisLibraryUnread;
		}
		ArrayList<String> sorted = new ArrayList<String>();
		for (int i=0; i<ListMode.size(); i++) {
			sorted.add(ListMode.get(i).getTitle());
		} 
		Collections.sort(sorted);
		
		List<Book> keys= new ArrayList(thisLibraryRating.keySet());
		// print out all the read titles
		for (int i = 0; i < sorted.size(); i++) {
			for ( int j=0; j<keys.size(); j++) {
				if ( (sorted.get(i)).equals(keys.get(j).getTitle()) ) {
					//System.out.println(keys.get(j).toString());
					sorted_books.add (new Book (keys.get(j).getTitle(), keys.get(j).getAuthor()));
					keys.remove(j);
					break;
				}
			}
		}
		return sorted_books;
	}
	

	public Book suggestRead() {
		/*
		 * simply shuffling the unread list and returning the one at index 0
		 */
		if (!thisLibraryUnread.isEmpty()) {
			Collections.shuffle(thisLibraryUnread);
			return thisLibraryUnread.get(0);
			
		}
		return null;
	}
}