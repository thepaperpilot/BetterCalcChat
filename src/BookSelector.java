import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BookSelector extends JDialog {
private final Client client;
private JPanel contentPane;
private JButton buttonOK;
private JButton buttonCancel;
private JComboBox classSelector;
private JComboBox textbookSelector;
private JPanel cover;

public BookSelector(Client client) {
	this.client = client;
	setContentPane(contentPane);
	setModal(true);
	getRootPane().setDefaultButton(buttonOK);
	setMinimumSize(new Dimension(600, 240));

	buttonOK.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			onOK();
		}
	});
	buttonCancel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			onCancel();
		}
	});

	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			onCancel();
		}
	});
	contentPane.registerKeyboardAction(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			onCancel();
		}
	}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

	classSelector.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			textbookSelector.removeAllItems();
			switch((String) classSelector.getSelectedItem()) {
				case "Calculus & Linear Algebra":
					for(Book book : Book.calc) {
						textbookSelector.addItem(book.name);
					}
					break;
				case "Precalculus & College Algebra":
					for(Book book : Book.precalc) {
						textbookSelector.addItem(book.name);
					}
					break;
				case "Applied Series":
					for(Book book : Book.applied) {
						textbookSelector.addItem(book.name);
					}
					break;
			}
			updateCover();
		}
	});

	textbookSelector.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			updateCover();
		}
	});

	for(Book book : Book.calc) {
		textbookSelector.addItem(book.name);
	}
	updateCover();
}

private void onOK() {
// add your code here
	Book book = Book.getBookByName((String) textbookSelector.getSelectedItem());
	if(book != null)
		client.readBook(book);
	dispose();
}

private void onCancel() {
	dispose();
}

private void updateCover() {
	cover.removeAll();
	Book book = Book.getBookByName((String) textbookSelector.getSelectedItem());
	if(book != null)
		cover.add(new JLabel(new ImageIcon(book.coverUrl)));
	cover.updateUI();
}
}
