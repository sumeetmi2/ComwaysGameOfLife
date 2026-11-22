final class Life {

	static final int SIZE = 30;

	private boolean[][] cells = new boolean[SIZE][SIZE];
	private int generation;

	static boolean inBounds(int row, int col) {
		return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
	}

	void clear() {
		cells = new boolean[SIZE][SIZE];
		generation = 0;
	}

	void load(Pattern pattern) {
		clear();
		for (int[] rowCol : pattern.liveRowCols()) {
			cells[rowCol[0]][rowCol[1]] = true;
		}
	}

	void toggle(int row, int col) {
		cells[row][col] = !cells[row][col];
	}

	boolean isAlive(int row, int col) {
		return cells[row][col];
	}

	int generation() {
		return generation;
	}

	void step() {
		boolean[][] next = new boolean[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				int neighbors = liveNeighbors(row, col);
				next[row][col] = neighbors == 3 || (cells[row][col] && neighbors == 2);
			}
		}
		cells = next;
		generation++;
	}

	private int liveNeighbors(int row, int col) {
		int count = 0;
		for (int dr = -1; dr <= 1; dr++) {
			for (int dc = -1; dc <= 1; dc++) {
				if ((dr != 0 || dc != 0) && inBounds(row + dr, col + dc) && cells[row + dr][col + dc]) {
					count++;
				}
			}
		}
		return count;
	}
}
