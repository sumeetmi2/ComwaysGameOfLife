import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
final class LifePanel extends JPanel {

	private static final int PREFERRED_CELL_PX = 20;
	private static final Color BACKGROUND = new Color(0x111827);
	private static final Color DEAD = new Color(0x1f2937);
	private static final Color ALIVE = new Color(0x4ade80);

	private final Life life;

	LifePanel(Life life) {
		this.life = life;
		setBackground(BACKGROUND);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int size = cellSize();
				int row = Math.floorDiv(e.getY() - offsetY(), size);
				int col = Math.floorDiv(e.getX() - offsetX(), size);
				if (Life.inBounds(row, col)) {
					life.toggle(row, col);
					repaint();
				}
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
		int px = Life.SIZE * PREFERRED_CELL_PX;
		return new Dimension(px, px);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int size = cellSize();
		int fill = Math.max(1, size - 1);
		int ox = offsetX();
		int oy = offsetY();
		for (int row = 0; row < Life.SIZE; row++) {
			for (int col = 0; col < Life.SIZE; col++) {
				g.setColor(life.isAlive(row, col) ? ALIVE : DEAD);
				g.fillRect(ox + col * size, oy + row * size, fill, fill);
			}
		}
	}

	private int cellSize() {
		return Math.max(1, Math.min(getWidth(), getHeight()) / Life.SIZE);
	}

	private int offsetX() {
		return (getWidth() - cellSize() * Life.SIZE) / 2;
	}

	private int offsetY() {
		return (getHeight() - cellSize() * Life.SIZE) / 2;
	}
}
