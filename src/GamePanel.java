package src;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import components.Dino;
import components.Obstacles;

class GamePanel extends JPanel implements KeyListener, Runnable {

	public static int WIDTH;
	public static int HEIGHT;
	private Thread animator;

	private boolean running = false;
	private boolean gameOver = false;

	Dino dino;
	Obstacles obstacles;

	public GamePanel() {
		WIDTH = GUI_Launcher.WIDTH;
		HEIGHT = GUI_Launcher.HEIGHT;

		dino = new Dino();
		obstacles = new Obstacles((int) (WIDTH * 1.5));

		setSize(WIDTH, HEIGHT);
		setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
		dino.create(g);
		obstacles.create(g);
	}

	public void run() {
		running = true;

		while (running) {
			updateGame();
			repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateGame() {

		obstacles.update();

		if (obstacles.hasCollided()) {
			dino.die();
			repaint();
			running = false;

			gameOver = true;
		}
	}

	public void reset() {
		obstacles.resume();
		gameOver = false;
	}

	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == ' ') {
			if (gameOver)
				reset();
			if (animator == null || !running) {

				animator = new Thread(this);
				animator.start();
				dino.startRunning();
			} else {
				dino.jump();
			}
		}
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}