import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public final class GameOfLife {

	private static final int MAX_GENERATIONS_PER_SECOND = 30;
	private static final int DEFAULT_GENERATIONS_PER_SECOND = 5;

	private final Life life = new Life();
	private final LifePanel board = new LifePanel(life);
	private final JComboBox<Pattern> picker = new JComboBox<>(Pattern.values());
	private final JButton playButton = new JButton("Play");
	private final JSlider speed = new JSlider(1, MAX_GENERATIONS_PER_SECOND, DEFAULT_GENERATIONS_PER_SECOND);
	private final JLabel generationLabel = new JLabel();
	private final Timer timer = new Timer(delayMs(), e -> advance());

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameOfLife().show());
	}

	private void show() {
		JFrame frame = new JFrame("Conway's Game of Life");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(buildControls(), BorderLayout.NORTH);
		frame.add(board, BorderLayout.CENTER);
		frame.add(buildStatusBar(), BorderLayout.SOUTH);
		reload();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel buildControls() {
		JButton step = new JButton("Step");
		JButton reset = new JButton("Reset");
		JButton clear = new JButton("Clear");

		picker.addActionListener(e -> reload());
		playButton.addActionListener(e -> togglePlay());
		step.addActionListener(e -> {
			pause();
			advance();
		});
		reset.addActionListener(e -> reload());
		clear.addActionListener(e -> {
			pause();
			life.clear();
			refresh();
		});
		speed.addChangeListener(e -> {
			timer.setDelay(delayMs());
			timer.setInitialDelay(delayMs());
		});

		JPanel controls = new JPanel();
		controls.add(picker);
		controls.add(playButton);
		controls.add(step);
		controls.add(reset);
		controls.add(clear);
		controls.add(new JLabel("Generations/sec"));
		controls.add(speed);
		return controls;
	}

	private JPanel buildStatusBar() {
		JPanel status = new JPanel(new FlowLayout(FlowLayout.LEFT));
		status.add(generationLabel);
		return status;
	}

	private void togglePlay() {
		if (timer.isRunning()) {
			pause();
		} else {
			play();
		}
	}

	private void play() {
		timer.start();
		playButton.setText("Pause");
	}

	private void pause() {
		timer.stop();
		playButton.setText("Play");
	}

	private void reload() {
		pause();
		life.load((Pattern) picker.getSelectedItem());
		refresh();
	}

	private void advance() {
		life.step();
		refresh();
	}

	private void refresh() {
		generationLabel.setText("Generation: " + life.generation());
		board.repaint();
	}

	private int delayMs() {
		return 1000 / speed.getValue();
	}
}
