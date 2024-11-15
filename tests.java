import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class tests {
	
	@BeforeEach
	public void setUp() {
		BookModel MODEL= new BookModel();
		BookController CONTROLLER= new BookController(MODEL);
		
		
	}
	
	

	@Test
	void controllerInitialization() {
		
		
		
	}

}
