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
		int maxCells = (int) Math.ceil(maxWidth / (float) cellWidth);
		int members = target.getComponentCount();

		int y = 0;
		int x = 0;
		cells.clear();

		outer:
		for(int i = 0; i < members; ++i) {
			Component m = target.getComponent(i);
			if(m.isVisible()) {
				Dimension d = m.getPreferredSize();
				m.setSize(d.width, d.height);
				int cellsWide = (int) Math.ceil(d.width / (float) cellWidth);
				int cellsTall = Math.round(d.height / (float) cellHeight);

				while(true) {
					if(cellsWide >= maxCells) {
						y++;
						x = 0;
						//place
						m.setLocation(insets.left + hgap + x * (cellWidth + hgap), insets.top + vgap + y * (cellHeight + vgap));
						for(int j = y; j < y + cellsTall; j++) {
							for(int k = x; k < x + cellsWide && k <= maxCells; k++) {
								while(cells.size() < j + 1)
									cells.add(new boolean[maxCells]);
								cells.get(j)[k] = true;
							}
						}
						continue outer;
					}

					while(cells.size() < y + 1)
						cells.add(new boolean[maxCells]);

					inner: for(; x <= cells.get(y).length - cellsWide; x++) {
						for(int k = x; k < x + cellsWide; k++) {
							if(cells.get(y)[k])
								continue inner;
							if(k == maxCells - 1) {
								int extraWidth = d.width - (cellsWide - 1) * cellWidth;
								int marginWidth = maxWidth - maxCells * cellWidth;
								if(extraWidth > marginWidth)
									continue inner;
							}
						}
						//place
						m.setLocation(insets.left + hgap + x * (cellWidth + hgap), insets.top + vgap + y * (cellHeight + vgap));
						for(int j = y; j < y + cellsTall; j++) {
							for(int k = x; k < x + cellsWide; k++) {
								while(cells.size() < j + 1)
									cells.add(new boolean[maxCells]);
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
		dim = new Dimension(maxWidth, cells.size() * (cellHeight + vgap) + vgap * 2);
	}
}
}