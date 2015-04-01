class Section {

public String name;
public String pre;
public int lastProblem;

public void setName(String name) {
	this.name = name;
}

public void setPre(String pre) {
	this.pre = pre;
}

public void setLastProblem(String lastProblem) {
	this.lastProblem = Integer.parseInt(lastProblem);
}

@Override
public String toString() {
	return "Section{" +
			       "name='" + name + '\'' +
			       ", pre='" + pre + '\'' +
			       ", lastProblem=" + lastProblem +
			       '}';
}
}
