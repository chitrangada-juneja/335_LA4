import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_view extends JFrame {
	
	private  BookModel MODEL;
	private  BookController CONTROLLER;
	private JTextArea output;
	
	public GUI_view(){
		MODEL= new BookModel();
		CONTROLLER= new BookController(MODEL);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle ("My Library");
		setSize(800, 800);
		setLayout(new BorderLayout());
		
		
		output= new JTextArea(20,60);
		output.setEditable(false);
		//cant modify the buttons
		
		JScrollPane scroll=new JScrollPane(output);
		// to be able to scroll
		add (scroll,BorderLayout.CENTER);
		
		//adding buttons
		// we have a total of 8 actions possible, including exit
		// we need 2x4 layout for the buttons
		JPanel buttons= new JPanel();
		
		buttons.setLayout(new GridLayout(3,3));
		
		JButton searchButton= new JButton("Search");
		JButton addBookButton= new JButton("addBook");
		JButton setReadButton= new JButton ("setToRead");
		JButton rateButton= new JButton ("rate");
		JButton getBooksButton= new JButton ("getBooks");
		JButton suggestReadButton= new JButton ("suggestRead");
		JButton addBooksButton= new JButton("addBooks");
		JButton exitButton= new JButton("exit");
		JButton clearButton=new JButton("clear");
		
		
		buttons.add(searchButton);
		buttons.add(addBookButton);
		buttons.add(setReadButton);
		buttons.add(rateButton);
		buttons.add(getBooksButton);
		buttons.add(suggestReadButton);
		buttons.add(addBooksButton);
		buttons.add(exitButton);
		buttons.add(clearButton);
		add(buttons,BorderLayout.SOUTH);
		//added and set all buttons
		// now we need to define how they work and what they do 
		//when clicked
		// we add handlers for each action, except exit, where we just exit
		//we use lambda functions for easier functionality
		
		exitButton.addActionListener(e->System.exit(0));
		clearButton.addActionListener(e->output.setText(""));
		
		searchButton.addActionListener(e->searchHandler());
		addBookButton.addActionListener(e->addBookHandler());
		setReadButton.addActionListener(e->setReadHandler());
		rateButton.addActionListener(e->rateHandler());
		getBooksButton.addActionListener(e->getBooksHandler());
		suggestReadButton.addActionListener(e->suggestReadHandler());
		addBooksButton.addActionListener(e->addBooksHandler());
		
		
	}
	private void searchHandler() {
		// now we need to provide same functionality as we had in text UI
		// for search, this means we need options to search by, and display
		//according to specifications. we use a dialog box+drop down for this
		int searchCode=-1;
		String options[]= {"title", "author", "rating"};
		String searchBy= (String)JOptionPane.showInputDialog(this,"options","search",
				JOptionPane.PLAIN_MESSAGE, null,options, null );
		
		if (searchBy!=null) {
			String query=JOptionPane.showInputDialog(this,"Enter the "+searchBy+" :" );
			// now we check which one it was and accordingly ask for more info
			if (searchBy.equals("title")) {
				searchCode=0;
				
			}
			else if (searchBy.equals("author")) {
				searchCode=1;
			}
			else if (searchBy.equals("rating")) {
				searchCode=2;
			}
			if (query!=null && searchCode!=-1) {
				ArrayList<Book> results= CONTROLLER.searchLibrary(searchCode, query.toUpperCase());
				if (results.isEmpty()) {
					output.append("Sorry! no books found matching those specifications\n");
					
				}
				else {
					output.append("the following books match your specifications: \n");
					for (Book b: results) {
						output.append(b.toString()+"\n");
					}
				}
				
			}
			
		}
	}
	private void addBookHandler() {
		
		String title =JOptionPane.showInputDialog(this,"Please enter the book's title: ");
		if (title!=null) {
			String author=JOptionPane.showInputDialog(this,"Please enter the book's author: ");
			if (author!=null) {
				CONTROLLER.addBookToLibrary(title, author);
				output.append("Successfully added book to database!\n");
			}
		}
		
	}
	
	private void setReadHandler() {
		String title =JOptionPane.showInputDialog(this,"Please enter the book's title: ");
		if (title!=null) {
			String author=JOptionPane.showInputDialog(this,"Please enter the book's author: ");
			if (author!=null) {
				int n=CONTROLLER.readBook(title,author);
				
				if (n==1) {
					output.append("Sorry that book doesn't exist in our database!\n");
				}
				else {
					output.append("Book set to read!\n");
				}
					
			}
		}
	}
	
	private void rateHandler() {
		String title =JOptionPane.showInputDialog(this,"Please enter the title of the book you want to rate: ");
		if (title!=null) {
			String author=JOptionPane.showInputDialog(this,"Please enter the author of the book you want to rate: ");
			
			if (author!=null) {
				// since we can only deal with strings with JOptionPanel, we do parseInt
				String rating=JOptionPane.showInputDialog(this,"Rate the book between 1 and 5 (inclusive)");
				if (rating!=null) {
					int myRating = Integer.parseInt(rating);
					int check= CONTROLLER.rateBook(title ,author, myRating);
					if (check==0) {
						output.append("Successfully rated the book!\n");
						
					}
					else {
						output.append("Could not find a book matching specifications!\n");
					}
				}
			}
				
		}
	}
	
	private void getBooksHandler() {
		
		String options[]= {"title", "author","read","unread"};
		String sortBy= (String)JOptionPane.showInputDialog(this,"sort by","getBooks",
				JOptionPane.PLAIN_MESSAGE, null,options, null );
		ArrayList<Book> sortedBooks= new ArrayList<Book>();
		
		if (sortBy!=null) {
			if (sortBy.equals("title")) {
				sortedBooks=CONTROLLER.getBooksFromLibrary(0);
				
			}
			else if (sortBy.equals("author")) {
				sortedBooks=CONTROLLER.getBooksFromLibrary(1);
			}
			else if (sortBy.equals("read")) {
				sortedBooks=CONTROLLER.getBooksFromLibrary(2);
				
			}
			else if (sortBy.equals("unread")) {
				sortedBooks=CONTROLLER.getBooksFromLibrary(3);
			}
			
			if (sortedBooks.isEmpty()) {
				output.append(" No books found for  "+sortBy+" option \n");
				
			}
			else {
				output.append("the following books match your specifications: \n");
				for (Book b : sortedBooks) {
					output.append(b.toString()+"\n");
					
				}
			}
		}
		
	
	}
	
	private void suggestReadHandler() {
		String[] bookInfo=CONTROLLER.suggestBook();
		if (bookInfo==null) {
			output.append("Sorry, no unread books to suggest!\n");
		}
		else {
			
			output.append("We have a book suggestion for you ! It is: " + bookInfo[0] +
					" by " + bookInfo[1]+"\n");

		}
		
	}
	
	private void addBooksHandler() {
		
		String fileName= JOptionPane.showInputDialog(this,"Please enter the name of a file with books to add");
		if (fileName!=null) {
			CONTROLLER.addListOfBooks(fileName);
			output.append("Books added!\n");
		}
		
		
	}
	
}
