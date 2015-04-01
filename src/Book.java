import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Book {

// calcchat stores the books manually :/
public static final ArrayList<Book> calc = new ArrayList<>();
public static final ArrayList<Book> precalc = new ArrayList<>();
public static final ArrayList<Book> applied = new ArrayList<>();

static {
	/* Calculus books */
	calc.add(new Book("Calculus 10e", "calc_10e", "calc10e"));
	calc.add(new Book("Calculus ETF 6e", "calc_etf_6e", "etf6e_mini"));
	calc.add(new Book("Calculus 9e", "calc_9e", "calc9e_mini"));
	calc.add(new Book("Calculus ETF 5e", "calc_etf_5e", "etf5e_mini"));
	calc.add(new Book("Calculus I with Precalculus 3e", "calc_1_pre_3e", "c1wp3e_mini"));
	calc.add(new Book("Calculus 8e", "calc_8e", "calc8e_mini"));
	calc.add(new Book("Calculus ETF 4e", "calc_etf_4e", "etf4e_mini"));
	calc.add(new Book("Calculus 7e", "calc_7e", "calc7e_mini"));
	calc.add(new Book("Calculus ETF 3e", "calc_etf_3e", "etf3e_mini"));
	calc.add(new Book("Essential Calculus ETF", "calc_eetf_1e", "eetf1e_mini"));
	calc.add(new Book("Elementary Linear Algebra 7e", "calc_eetf_1e", "ela7e_mini"));
}

public final String name;
public URL xmlUrl;
public URL coverUrl;
public String imageUrl;

private Book(String name, String xmlURL, String coverURL) {
	this.name = name;
	try {
		this.xmlUrl = new URL("http://www.calcchat.com/xml/" + xmlURL + "_chapters.xml");
		this.coverUrl = new URL("http://images.calcchat.com/images/book_covers/" + coverURL + ".jpg");
	} catch(MalformedURLException e) {
		e.printStackTrace();
	}
}

public static Book getBookByName(String name) {
	for(Book book : calc) {
		if(book.name.equals(name))
			return book;
	}
	for(Book book : precalc) {
		if(book.name.equals(name))
			return book;
	}
	for(Book book : applied) {
		if(book.name.equals(name))
			return book;
	}
	return null;
}

public void setURL(String URL) {
	this.imageUrl = URL + "/";
}
}
