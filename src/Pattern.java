enum Pattern {
	TEN_CELL("10 Cell Input", new int[][] {
			{ 14, 14 }, { 14, 15 }, { 14, 16 }, { 14, 17 }, { 14, 18 },
			{ 14, 19 }, { 14, 20 }, { 14, 21 }, { 14, 22 }, { 14, 23 } }),
	GLIDER("Glider", new int[][] { { 0, 1 }, { 1, 2 }, { 2, 0 }, { 2, 1 }, { 2, 2 } }),
	EXPLODER("Exploder", new int[][] {
			{ 12, 12 }, { 13, 12 }, { 14, 12 }, { 15, 12 }, { 16, 12 },
			{ 12, 16 }, { 13, 16 }, { 14, 16 }, { 15, 16 }, { 16, 16 },
			{ 12, 14 }, { 16, 14 } }),
	BLINKER("Blinker", new int[][] { { 5, 5 }, { 6, 5 }, { 7, 5 } }),
	LIGHTWEIGHT_SPACESHIP("Lightweight Spaceship", new int[][] {
			{ 25, 1 }, { 27, 1 }, { 24, 2 }, { 24, 3 }, { 24, 4 },
			{ 24, 5 }, { 25, 5 }, { 26, 5 }, { 27, 4 } });

	private final String label;
	private final int[][] liveRowCols;

	Pattern(String label, int[][] liveRowCols) {
		this.label = label;
		this.liveRowCols = liveRowCols;
	}

	int[][] liveRowCols() {
		return liveRowCols;
	}

	@Override
	public String toString() {
		return label;
	}
}
