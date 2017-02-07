
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Lab11 extends JFrame {
	public Lab11(){
		setTitle("CS251-Editor");
		setBounds(200, 200, 600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private static final long serialVersionUID = 1L;

	// Main program for the application
	public static void main(String[] args) {
		final JFrame frame = new Lab11();
		// set border layout
		// TODO

		// panel for the two buttons
		final JPanel bPanel = new JPanel();

		// button for load and save file
		final JButton save = new JButton("save");
		final JButton load = new JButton("load");

		// add buttons to panel
		bPanel.add(save);bPanel.add(load);

		// disable save button since it makes no sense to save before there is file
		save.setEnabled(false);

		// create a text area
		final JTextArea text = new JTextArea();
		// put it into a scroll pane so that the text can be larger than viewable area
		final JScrollPane textScroller = new JScrollPane(text);

		// add scrollable text to the center of the frame
		// add button panel to the top
		frame.add(textScroller, BorderLayout.CENTER);
		frame.add(bPanel, BorderLayout.NORTH);

		// create a file editor instance
		final FileEditor editor = new FileEditor(text);

		// create a listener that loads file if load button is clicked
		//   if file is loaded successfully, it enables the save button
		// the listener saves file if save button is clicked
		ActionListener listener = new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == save){
					editor.saveFile();
				}
				else if (event.getSource() == load){
					editor.loadFile();
					save.setEnabled(true);
				}
			}
		};
		// register listener to the buttons
		save.addActionListener(listener);
		load.addActionListener(listener);
		
		frame.setVisible(true);
	}
}

class FileEditor {
	private JTextArea text;

	// file loaded and to be saved
	private File file = null;

	// save the text area
	FileEditor(JTextArea text) {
		this.text = text;
	}

	// returns true if and only if a file is successfully loaded
	boolean loadFile() {
		File selectedFile = null;
		// file chooser allows user to select a file from the default directory
		JFileChooser dialog = new JFileChooser();

		// use a new frame to display open dialog
		int result = dialog.showOpenDialog(new JFrame());

		// if user approved chosen file
		if (result == JFileChooser.APPROVE_OPTION) {
			// get the selected file
			selectedFile = dialog.getSelectedFile();

			// create a scanner for the file
			Scanner in = null;
			try {
				in = new Scanner(selectedFile);
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			// use the scanner to read the file line by line
			String content = "";
			while (in.hasNextLine()) {
				content = content + in.nextLine() + "\n";
			}
			// close scanner
			if(in != null) { in.close(); }

			// set the text area with the read file
			this.text.setText(content);
		}

		// save selected file and return true if and only if the selected file is not null 
		boolean ret = false;
		if(selectedFile != null) {
			this.file = selectedFile;
			ret = true;
		}
		return ret;
	}
	
	// if the file is not null, use PrintWriter to write the content of the text area into the file
	void saveFile() {
		if(this.file != null) {
			PrintWriter out = null;
			try {
				out = new PrintWriter(this.file);
				out.println(this.text.getText());
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			finally {
				if(out != null) { out.close(); }
			}
		}
	}
}

