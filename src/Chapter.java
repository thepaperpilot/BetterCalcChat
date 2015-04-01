import java.util.ArrayList;

class Chapter {

final ArrayList<Section> sections = new ArrayList<Section>();
public String name;

public void setName(String name) {
	this.name = name;
}

public void addSection(Section section) {
	sections.add(section);
}

@Override
public String toString() {
	return "Chapter{" +
			       "name='" + name + '\'' +
			       ", sections=" + sections +
			       '}';
}
}
