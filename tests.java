/* Authors: Chitrangada Juneja and Aidan Tucker
 * Net IDs: cj21 and artboy
 * This program serves as our JUnit test cases for the
 * BookModel and BookController classes.
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class tests {
	private BookModel MODEL;
	private BookController CONTROLLER;
	
	@BeforeEach
	public void setUp() {
		MODEL = new BookModel();
		CONTROLLER = new BookController(MODEL);
		
		CONTROLLER.addListOfBooks("books.txt");
		CONTROLLER.addBookToLibrary("Visual Thinking", "Temple Grandin");
		
	}
	
	

	@Test
	void testSearchLibraryAndRate() {
		assertEquals(CONTROLLER.searchLibrary(0, "THE HOBBIT").size(), 1);
		assertEquals(CONTROLLER.searchLibrary(1, "J.R.R. TOLKIEN").size(), 5);
		assertEquals(CONTROLLER.getBooksFromLibrary(3).size(), CONTROLLER.searchLibrary(2, "0").size());
	}
	
	@Test
	void testRateBook() {
		assertEquals(CONTROLLER.rateBook("VISUAL THINKING", "TEMPLE GRANDIN", 5), 0);
		assertEquals(CONTROLLER.rateBook("DOESNT", "EXIST", 2), 1);
	}
	
	@Test
	void testGetBooks() {
		assertEquals(CONTROLLER.getBooksFromLibrary(0).get(0).getTitle(), "11-22-63");
		assertEquals(CONTROLLER.getBooksFromLibrary(1).get(0).getAuthor(), "ALLISON HOOVER BARTLETT");
		assertEquals(CONTROLLER.getBooksFromLibrary(3).get(0).getAuthor(), "STEPHEN KING");
	}
	
	@Test
	void testReadEmpty() {
		assertEquals(CONTROLLER.readBook("DOESNT", "EXIST"), 1);
	}
	
	@Test
	void testGetReadBooks() {
		assertEquals(CONTROLLER.readBook("VISUAL THINKING", "TEMPLE GRANDIN"), 0);
		CONTROLLER.readBook("VISUAL THINKING", "TEMPLE GRANDIN");
		assertEquals(CONTROLLER.getBooksFromLibrary(2).size(), 1);
	}
	
	@Test
	void testSuggestion() {
		BookModel MODEL2 = new BookModel();
		BookController CONTROLLER2 = new BookController(MODEL2);
		CONTROLLER2.addBookToLibrary("Book", "Aidan Tucker");
		CONTROLLER2.addBookToLibrary("Tome", "Chitrangada Juneja");
		String title = CONTROLLER2.suggestBook()[0];
		assertTrue(title.equals("BOOK") || title.equals("TOME"));
		CONTROLLER2.readBook("BOOK", "AIDAN TUCKER");
		CONTROLLER2.readBook("TOME", "CHITRANGADA JUNEJA");
		assertNull(CONTROLLER2.suggestBook());
	}

}
