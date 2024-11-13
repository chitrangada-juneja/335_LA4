import javax.swing.SwingUtilities;

public class LibraryGUI  implements Runnable{

	@Override
	public void run() {
		GUI_view gui= new GUI_view();
		gui.setVisible(true);
		
	}
	public static void main(String []args) {
		SwingUtilities.invokeLater(new LibraryGUI());
	}
	

}
