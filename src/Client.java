import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

class Client {

private JPanel panel;
private JPanel web;
private JComboBox sectionSelector;
private JComboBox chapterSelector;
private JScrollPane pane;
private JPanel cards;
private JButton goButton;
private JButton back;
private JLabel question;
private JPanel questionImage;
private JScrollPane pane2;
private JPanel gallery;
private JScrollPane galleryPane;
private JButton bookButton;
private JLabel chapterLabel;
private JLabel sectionLabel;
private JProgressBar downloadingBar;
private JLabel downloadingLabel;
private JPanel downloadingPanel;

private ArrayList<BufferedImage> images;
private int selectedImage;
private BookHandler handler;
private Book book;
private Chapter chapter;
private Section section;

private Client() {
	pane.getVerticalScrollBar().setUnitIncrement(16);
	pane2.getVerticalScrollBar().setUnitIncrement(16);
	galleryPane.getVerticalScrollBar().setUnitIncrement(16);

	bookButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			BookSelector dialog = new BookSelector(Client.this);
			dialog.pack();
			dialog.setVisible(true);
		}
	});
	goButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			update();
		}
	});
	back.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "overview");
		}
	});
	chapterSelector.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			for(Chapter chapter : handler.chapters) {
				if(chapter.name == chapterSelector.getSelectedItem()) {
					Client.this.chapter = chapter;
					Client.this.section = chapter.sections.get(0);
					sectionSelector.removeAllItems();
					for(Section section : chapter.sections) {
						sectionSelector.addItem(section.name);
					}
					break;
				}
			}
		}
	});
	sectionSelector.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			for(Section section : chapter.sections) {
				if(section.name == sectionSelector.getSelectedItem()) {
					Client.this.section = section;
					break;
				}
			}
		}
	});

	panel.addComponentListener(new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			panel.updateUI();
			updateGallery();
		}
	});
}

public static void main(String[] args) {
	JFrame frame = new JFrame("A Better CalcChat Client");
	frame.setContentPane(new Client().panel);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setMinimumSize(new Dimension(720, 640));
	frame.pack();
	frame.setVisible(true);
}

// Updates the images
private void update() {
	new Thread(new Runnable() {
		@Override
		public void run() {
			downloadingBar.setMaximum(section.lastProblem);
			downloadingLabel.setText("Downloading problem 0 out of " + section.lastProblem);
			downloadingBar.setValue(0);
			downloadingPanel.setVisible(true);
			images = new ArrayList<>();
			((CardLayout) cards.getLayout()).show(cards, "overview");
			web.removeAll();
			web.updateUI();
			gallery.removeAll();
			gallery.updateUI();

			for(int i = 1; i <= section.lastProblem; i += 2) {
				downloadingLabel.setText("Downloading problem " + i + " out of " + section.lastProblem);
				downloadingBar.setValue(i);
				final BufferedImage image = getImage(i);
				images.add(image);
				JPanel overviewPanel = getImageCard(image, i);
				overviewPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						((CardLayout) cards.getLayout()).show(cards, "gallery");
						cards.updateUI();
						selectedImage = images.indexOf(image);
						updateGallery();
					}
				});
				web.add(overviewPanel);
				web.updateUI();

				int width = gallery.getWidth() - 30;
				int height = image.getHeight() * width / image.getWidth();
				JPanel galleryPanel = getImageCard(getScaledInstance(image, width, height), i);
				galleryPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						selectedImage = images.indexOf(image);
						updateGallery();
					}
				});
				gallery.add(galleryPanel);
				gallery.updateUI();
			}
			downloadingPanel.setVisible(false);
		}
	}).start();
}

void readBook(Book book) {
	try {
		this.book = book;
		handler = new BookHandler(book);
		XMLReader myReader = XMLReaderFactory.createXMLReader();
		myReader.setContentHandler(handler);
		myReader.parse(new InputSource(book.xmlUrl.openStream()));

		System.out.println("Found " + handler.chapters.size() + " chapters:");
		for(Chapter chapter : handler.chapters)
			System.out.println(chapter);

		chapter = handler.chapters.get(0);
		section = chapter.sections.get(0);
		chapterSelector.removeAllItems();
		sectionSelector.removeAllItems();
		for(Chapter chapter : handler.chapters)
			chapterSelector.addItem(chapter.name);
		for(Section section : chapter.sections)
			sectionSelector.addItem(section.name);

		chapterLabel.setVisible(true);
		chapterSelector.setVisible(true);
		sectionLabel.setVisible(true);
		sectionSelector.setVisible(true);
		goButton.setVisible(true);
	} catch(SAXException | IOException e) {
		e.printStackTrace();
	}
}

// Get image from server
private BufferedImage getImage(int i) {
	String url = book.imageUrl;
	url += section.pre;
	url += String.format("%3d", i).replaceAll(" ", "0");

	System.out.println("Fetching " + url);
	try {
		return ImageIO.read(new URL(url + ".gif"));
	} catch(IOException ignored) {
		try {
			return ImageIO.read(new URL(url + ".png"));
		} catch(IOException e) {
			e.printStackTrace();
			((CardLayout) cards.getLayout()).show(cards, "startup");
			downloadingPanel.setVisible(false);
		}
	}
	return null;
}

// Create an etched border and title around image
private JPanel getImageCard(BufferedImage image, int num) {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.setBorder(new EtchedBorder());

	JLabel label = new JLabel(" Question " + num + ":");
	Font font = label.getFont();
	font = new Font(font.getFontName(), Font.BOLD, font.getSize());
	label.setFont(font);
	panel.add(label);
	panel.add(new JLabel(new ImageIcon(image)));
	return panel;
}

// Update image shown in the gallery view
void updateGallery() {
	if(images == null)
		return;
	questionImage.removeAll();
	int width = questionImage.getWidth() - 30;
	int height = images.get(selectedImage).getHeight() * width / images.get(selectedImage).getWidth();
	questionImage.add(new JLabel(new ImageIcon(getScaledInstance(images.get(selectedImage), width, height))));
	questionImage.updateUI();
	question.setText("Question " + (selectedImage * 2 + 1));
}

// Source: https://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html
BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight) {
	int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
	int w, h;
	w = targetWidth;
	h = targetHeight;

	BufferedImage tmp = new BufferedImage(w, h, type);
	Graphics2D g2 = tmp.createGraphics();
	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g2.drawImage(img, 0, 0, w, h, null);
	g2.dispose();
	return tmp;
}

// Set up custom LayoutManagers
private void createUIComponents() {
	web = new JPanel(new GridLayout(180, 100));
	gallery = new JPanel();
	gallery.setLayout(new BoxLayout(gallery, BoxLayout.Y_AXIS));
}
}