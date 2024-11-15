/* Author: Aidan Tucker
 * Note: Simulates a keyboard input by setting System.in
 * and .out via byte arrays.
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyLibraryTest {

  // default System.in and .out streams
  private final InputStream sysIn = System.in;
  private final PrintStream sysOut = System.out;

  // input and output byte arrays
  private ByteArrayInputStream testIn;
  private ByteArrayOutputStream testOut;

  // sets the output byte array to the System.out
  @Before
  public void setUpOut() {
    testOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(testOut));
  }

  // set the System.in
  private void giveIn(String in) {
    testIn = new ByteArrayInputStream(in.getBytes());
    System.setIn(testIn);
  }

  // get the System.out in string format
  private String getOut() {
    return testOut.toString();
  }

  // reset System.in and .out
  @After
  public void restoreSys() {
    System.setIn(sysIn);
    System.setOut(sysOut);
  }

  @Test
  public void testExitFirst() {
    final String testInput = "addBook\nThe Hobbit\nJRR "
    		+ "Tolkien\naddBook\nThe Sorcerer's Stone\nJK"
    		+ " Rowling\naddBooks\nbooks.txt\nsuggestRead"
    		+ "\ngetBooks\ntitle\ngetBooks\nauthor\nrate\n"
    		+ "Where the Sidewalk Ends\nShel Silverstein\n"
    		+ "5\nrate\nThe Hobbit\nJRR Tolkien\n5\nsetToRead"
    		+ "\nWhere the Sidewalk Ends\nShel Silverstein"
    		+ "\nsetToRead\nThe Hobbit\nJRR Tolkien\nexit";
    giveIn(testInput);

    MyLibrary.main(new String[0]);
    
    
  }
}
