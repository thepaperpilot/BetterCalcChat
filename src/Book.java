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

	/* Precalculus and College Algebra books */
	precalc.add(new Book("Precalculus 9e", "precalc_9e", "pr9e_mini"));
	precalc.add(new Book("Algebra and Trigonometry 9e", "algebra_trig_9e", "at9e_mini"));
	precalc.add(new Book("College Algebra 9e", "college_algebra_9e", "ca9e_mini"));
	precalc.add(new Book("Precalculus with Limits 3e", "precalc_wl_3e", "pl3e_mini"));
	precalc.add(new Book("Trigonometry 9e", "trig_9e", "tg9e_mini"));
	precalc.add(new Book("Precalculus: A Concise Course 3e", "precalc_acc_3e", "pcacc3e_mini"));
	precalc.add(new Book("Precalculus with Limits 3e Texas Edition", "precalc_wl_3e", "pl3e_tx_mini"));
	precalc.add(new Book("Algebra and Trigonometry 8e", "algebra_trig_8e", "at8e_mini"));
	precalc.add(new Book("Precalculus 8e", "precalc_8e", "pr8e_mini"));
	precalc.add(new Book("Precalculus With Limits 2e", "precalc_wl_2e", "pl2e_mini"));
	precalc.add(new Book("Precalculus: A Concise Course 2e", "precalc_acc_2e", "pc2e_mini"));
	precalc.add(new Book("Trigonometry 8e", "trig_8e", "tg8e_mini"));
	precalc.add(new Book("Precalculus With Limits AGA 6e", "precalc_limits_aga_6e", "plrmrp6e_mini"));
	precalc.add(new Book("Precalculus With Limits AGA 6e Texas Edition", "precalc_limits_aga_6e", "plrmrp6e_tx_mini"));
	precalc.add(new Book("College Algebra Real Mathematics Real People 6e", "college_algebra_rmrp_6e", "carmrp6e_mini"));
	precalc.add(new Book("Precalculus Real Mathematics Real People 6e", "precalc_rmrp_6e", "prrmrp6e_mini"));
	precalc.add(new Book("Algebra and Trigonometry Real Mathematics Real People 6e", "alg_trig_rmrp_6e", "atrmrp6e_mini"));
	precalc.add(new Book("Precalculus Real Mathematics Real People Alternate 6e", "precalc_rmrp_alt_6e", "prrmrpalt6e_mini"));
	precalc.add(new Book("College Algebra 7e", "college_algebra_7e", "ca7e_mini"));
	precalc.add(new Book("Algebra and Trigonometry 7e", "algebra_trig_7e", "at7e_mini"));
	precalc.add(new Book("Precalculus 7e", "precalc_7e", "pr7e_mini"));
	precalc.add(new Book("Precalculus With Limits", "precalc_wl_1e", "pl1e_mini"));
	precalc.add(new Book("Precalculus A Concise Course", "precalc_acc_1e", "pc1e_mini"));
	precalc.add(new Book("Trigonometry 7e", "trig_7e", "tg7e_mini"));
	precalc.add(new Book("College Algebra AGA 5e", "college_algebra_aga_5e", "caaga5e_mini"));
	precalc.add(new Book("Precalculus With Limits AGA 5e", "precalc_wl_aga_5e", "plaga5e_mini"));
	precalc.add(new Book("Algebra and Trigonometry AGA 5e", "algebra_trig_aga_5e", "ataga5e_mini"));
	precalc.add(new Book("Precalculus Functions and Graphs AGA 5e", "precalc_fg_aga_5e", "pfaga5e_mini"));
	precalc.add(new Book("College Algebra 8e", "college_algebra_8e", "ca8e_mini"));
	precalc.add(new Book("Precalculus AGA 5e", "precalc_aga_5e", "praga5e_mini"));

	/* Applied Series books */
	applied.add(new Book("Calculus An Applied Approach 9e", "calc_aa_9e", "caaa9e_mini"));
	applied.add(new Book("Brief Calculus An Applied Approach 9e", "brief_calc_aa_9e", "bcaaa9e_mini"));
	applied.add(new Book("College Algebra with Applications for Business and the Life Sciences 2e", "college_algebra_abls_2e", "caabls2e_mini"));
	applied.add(new Book("College Algebra and Calculus An Applied Approach 2e", "caabls_combo_2e", "caca2e_mini"));
	applied.add(new Book("Calculus An Applied Approach 8e", "calc_aa_8e", "caa8e_mini"));
	applied.add(new Book("Brief Calculus An Applied Approach 8e", "brief_calc_aa_8e", "bc8e_mini"));
	applied.add(new Book("College Algebra with Applications for Business and the Life Sciences", "college_algebra_abls_1e", "caabls1e_mini"));
	applied.add(new Book("Applied Calculus for the Life and Social Sciences", "calc_lss_1e", "clss1e_mini"));
	applied.add(new Book("College Algebra and Calculus  An Applied Approach", "caabls_combo_1e", "caac1e_mini"));
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
