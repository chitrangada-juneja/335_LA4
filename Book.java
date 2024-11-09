/* Authors: Chitrangada Juneja and Dylan Coles
 * Net IDs: cj21 and colesdylan12
 * Book class where the encapsulation is maintained by 
 * lacking any setters; once a Book object has been initialized
 * through the constructor, it cannot be modified in any way. 
 * every parameter is private,so cannot be modified outside of the class.
 * @param title : title of the book
 * @param author: author of the book
 * @pre no null inputs
 * 
 * 
 */






public class Book {
	private String myTitle;
	private String myAuthor;
	
	public Book(String title, String author) {
		//constructor
		myTitle = title;
		myAuthor = author;
	}
	
	public String getTitle() {
		//getter for title
		return myTitle;
	}
	
	public String getAuthor() {
		// returns the author
		return myAuthor;
	}
	public String toString() {
		// returns  a string representation of the Book object
		return myTitle+" : "+myAuthor;
	}
}