import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class Client {

private JPanel panel;
private JPanel web;
private JComboBox book;
private JSpinner section;
private JSpinner chapter;
private JScrollPane pane;
private JPanel cards;
private JButton goButton;
private JButton back;
private JLabel question;
private JPanel questionImage;
private JScrollPane pane2;
private JPanel gallery;
private JScrollPane galleryPane;

private ArrayList<BufferedImage> images;
private int selectedImage;

private Client() {
	pane.getVerticalScrollBar().setUnitIncrement(16);
	pane2.getVerticalScrollBar().setUnitIncrement(16);
	galleryPane.getVerticalScrollBar().setUnitIncrement(16);
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
	pane2.addComponentListener(new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			if(images != null) {
				updateGallery();
			}
		}
	});
}

// Source: https://stackoverflow.com/questions/27215135/checking-to-see-if-a-url-exists-java
private static boolean isValidURL(String mURL) {
	try {
		final URL url = new URL(mURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("HEAD");
		int responseCode = con.getResponseCode();
		return (responseCode == 200);
	} catch(Exception e) {
		System.err.println("Invalid URL");
		return false;
	}
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
			images = new ArrayList<BufferedImage>();
			((CardLayout) cards.getLayout()).show(cards, "overview");
			web.removeAll();
			web.updateUI();
			gallery.removeAll();
			gallery.updateUI();
			for(int i = 1; ; i += 2) {
				final BufferedImage image = getImage(i);
				if(image == null) break;
				images.add(image);
				JPanel overviewPanel = getImageCard(image, i);
				overviewPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						((CardLayout) cards.getLayout()).show(cards, "gallery");
						selectedImage = images.indexOf(image);
						updateGallery();
					}
				});
				web.add(overviewPanel);
				web.updateUI();

				int width = gallery.getWidth() - 30;
				int height = image.getHeight() * width / image.getWidth();
				JPanel galleryPanel = getImageCard(getScaledInstance(image, width, height, RenderingHints.VALUE_INTERPOLATION_BILINEAR, false), i);
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
		}
	}).start();
}

// Create an etched border and title around image
private JPanel getImageCard(BufferedImage image, int num) {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.setBorder(new EtchedBorder());
	JLabel label = new JLabel("Question " + num + ":");
	panel.add(label);
	panel.add(new JLabel(new ImageIcon(image)));
	return panel;
}

// Update image shown in the gallery view
void updateGallery() {
	questionImage.removeAll();
	int width = questionImage.getWidth() - 30;
	int height = images.get(selectedImage).getHeight() * width / images.get(selectedImage).getWidth();
	questionImage.add(new JLabel(new ImageIcon(getScaledInstance(images.get(selectedImage), width, height, RenderingHints.VALUE_INTERPOLATION_BILINEAR, false))));
	question.setText("Question " + (selectedImage * 2 + 1));
}

// Get image from server
private BufferedImage getImage(int i) {
	String url = "http://c811114.r14.cf2.rackcdn.com/";
	url += "se";
	url += String.format("%2d", (Integer) chapter.getValue());
	url += "" + (char) ('a' + (Integer) section.getValue() - 1);
	url += "01";
	url += String.format("%3d", i);
	url += ".gif";
	url = url.replaceAll(" ", "0");
	System.out.println("Fetching " + url);
	if(isValidURL(url))
		try {
			return ImageIO.read(new URL(url));
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	return null;
}

// Set up custom LayoutManagers
private void createUIComponents() {
	web = new JPanel(new WrapLayout(WrapLayout.LEFT));
	gallery = new JPanel();
	gallery.setLayout(new BoxLayout(gallery, BoxLayout.Y_AXIS));
}

// Source: https://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html
public BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
	int type = (img.getTransparency() == Transparency.OPAQUE) ?
			           BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
	BufferedImage ret = img;
	int w, h;
	if(higherQuality) {
		// Use multi-step technique: start with original size, then
		// scale down in multiple passes with drawImage()
		// until the target size is reached
		w = img.getWidth();
		h = img.getHeight();
	} else {
		// Use one-step technique: scale directly from original
		// size to target size with a single drawImage() call
		w = targetWidth;
		h = targetHeight;
	}

	do {
		if(higherQuality && w > targetWidth) {
			w /= 2;
			if(w < targetWidth) {
				w = targetWidth;
			}
		}

		if(higherQuality && h > targetHeight) {
			h /= 2;
			if(h < targetHeight) {
				h = targetHeight;
			}
		}

		BufferedImage tmp = new BufferedImage(w, h, type);
		Graphics2D g2 = tmp.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
		g2.drawImage(ret, 0, 0, w, h, null);
		g2.dispose();

		ret = tmp;
	} while(w != targetWidth || h != targetHeight);

	return ret;
}
}