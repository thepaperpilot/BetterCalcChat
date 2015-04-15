import java.awt.*;
import java.util.ArrayList;

class GridLayout implements LayoutManager {

private final int hgap;
private final int vgap;
private final int cellWidth;
private final int cellHeight;
private final ArrayList<boolean[]> cells = new ArrayList<>();

private Dimension dim;

public GridLayout(int cellWidth, int cellHeight) {
	this(5, 5, cellWidth, cellHeight);
}

public GridLayout(int hgap, int vgap, int cellWidth, int cellHeight) {
	this.hgap = hgap;
	this.vgap = vgap;
	this.cellWidth = cellWidth;
	this.cellHeight = cellHeight;
}

@Override
public void addLayoutComponent(String s, Component component) {
}

@Override
public void removeLayoutComponent(Component component) {
}

@Override
public Dimension preferredLayoutSize(Container target) {
	return layoutSize(target);
}

@Override
public Dimension minimumLayoutSize(Container target) {
	Dimension minimum = layoutSize(target);
	minimum.width -= (hgap + 1);
	return minimum;
}

private Dimension layoutSize(Container target) {
	if(dim == null) {
		layoutContainer(target);
	}
	return dim;
}

public void layoutContainer(Container target) {
	synchronized(target.getTreeLock()) {
		Insets insets = target.getInsets();
		int maxWidth = target.getWidth() - (insets.left + insets.right + this.hgap * 2);
		int members = target.getComponentCount();

		int y = 0;
		int x = 0;

		outer:
		for(int i = 0; i < members; ++i) {
			Component m = target.getComponent(i);
			if(m.isVisible()) {
				Dimension d = m.getPreferredSize();
				m.setSize(d.width, d.height);
				int cellsWide = (int) Math.ceil(d.width / (float) cellWidth);

				inner: while(true) {
					while(cells.size() < y + 1)
						cells.add(new boolean[getCellsWide(maxWidth)]);
					for(; x <= cells.get(y).length - cellsWide; x++) {
						for(int k = 0; k < cellsWide; k++) {
							if(cells.get(y)[x]) continue inner;
							if(k == cellsWide - 1) {
								int extraWidth = d.width - (cellsWide - 1) * cellWidth;
								int marginWidth = maxWidth - getCellsWide(maxWidth);
								if(extraWidth > marginWidth)
									continue inner;
							}
						}
						//place
						m.setLocation(insets.left + hgap + x * (cellWidth + hgap), insets.top + vgap + y * (cellHeight + vgap));
						for(int j = y; j < y + getCellsTall(d.height); j++) {
							for(int k = x; k < x + cellsWide; k++) {
								while(cells.size() < j + 1)
									cells.add(new boolean[getCellsWide(maxWidth)]);
								cells.get(j)[k] = true;
							}
						}
						continue outer;
					}
					y++;
					x = 0;
				}
			}
		}
		dim = new Dimension(maxWidth, cells.size() * cellHeight + vgap * 2);
	}
}

private int getCellsWide(int width) {
	return (int) Math.ceil(width / (float) cellWidth);
}

private int getCellsTall(int height) {
	return (int) Math.ceil(height / (float) cellHeight);
}
}