import java.awt.*;

class GridLayout implements LayoutManager {

private final int hgap;
private final int vgap;
private final int gridSize;
private final int minWidth;
private Dimension dim;

public GridLayout() {
	this(5, 5, 10, 357);
}

public GridLayout(int hgap, int vgap, int gridSize, int minWidth) {
	this.hgap = hgap;
	this.vgap = vgap;
	this.gridSize = gridSize;
	this.minWidth = minWidth;
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

		int x = insets.left + hgap;
		int y = insets.top + vgap;
		int lowY = y;
		int highY = y;

		outer:
		for(int i = 0; i < members; ++i) {
			Component m = target.getComponent(i);
			if(m.isVisible()) {
				Dimension d = m.getPreferredSize();
				m.setSize(d.width, d.height);

				while(true) {
					row:
					while(x < maxWidth - d.width) {
						for(int j = 0; j < i; j++) {
							Component com = target.getComponent(j);
							Rectangle bounds = com.getBounds();
							bounds.width = Math.max(bounds.width, minWidth);
							if(bounds.intersects(x, y, d.width + hgap * 2, d.height + vgap * 2)) {
								x = bounds.x + bounds.width;
								while((x - insets.left) % gridSize != 0) x++;
								continue row;
							}
						}
						m.setLocation(x + hgap, y + vgap);
						x += d.width + hgap;
						while((x - insets.left) % gridSize != 0) x++;
						if(lowY > m.getY() + m.getHeight())
							lowY = m.getY() + m.getHeight();
						highY = Math.max(highY, y + d.height);
						continue outer;
					}
					x = insets.left + hgap;
					if(y == lowY) y = lowY + 1;
					else y = lowY;
					while((y - insets.top) % gridSize != 0) y++;
					lowY = y;
				}
			}
		}
		dim = new Dimension(maxWidth, highY + vgap);
	}
}
}