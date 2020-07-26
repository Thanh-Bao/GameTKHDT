package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Obstacles {
	private class Obstacle {
		BufferedImage image;
		int x;
		int y;

		Rectangle getObstacle() {
			Rectangle obstacle = new Rectangle();
			obstacle.x = x;
			obstacle.y = y;
			obstacle.width = image.getWidth();
			obstacle.height = image.getHeight();
			return obstacle;
		}
	}

	private int firstX;
	private int obstacleInterval;
	private int movementSpeed;

	private ArrayList<BufferedImage> imageList;
	private ArrayList<Obstacle> obList;

	private Obstacle blockedAt;

	public Obstacles(int firstPos) {
		obList = new ArrayList<Obstacle>();
		imageList = new ArrayList<BufferedImage>();

		firstX = firstPos;
		obstacleInterval = 1100;
		movementSpeed = 25;

		imageList.add(new Resource().getResourceImage("../images/obstacle.jpg"));

		for (BufferedImage bi : imageList) {
			Obstacle ob = new Obstacle();
			ob.image = bi;
			ob.y = 465 - bi.getHeight();
			obList.add(ob);
		}
	}

	public void update() {
		Iterator<Obstacle> looper = obList.iterator();

		Obstacle firstOb = looper.next();
		firstOb.x -= movementSpeed;

		while (looper.hasNext()) {
			Obstacle ob = looper.next();
			ob.x -= movementSpeed;
		}

		if (firstOb.x < -firstOb.image.getWidth()) {
			obList.remove(firstOb);
			firstOb.x = firstOb.x + obstacleInterval;
			obList.add(firstOb);
		}
	}

	public void create(Graphics g) {
		for (Obstacle ob : obList) {
			g.setColor(Color.black);
			g.drawImage(ob.image, ob.x, ob.y, null);
		}
	}

	public boolean hasCollided() {
		for (Obstacle ob : obList) {
			if (Dino.getDino().intersects(ob.getObstacle())) {
				blockedAt = ob;
				return true;
			}
		}
		return false;
	}

	public void resume() {
		int x = firstX / 2;
		obList = new ArrayList<Obstacle>();

		for (BufferedImage bi : imageList) {

			Obstacle ob = new Obstacle();

			ob.image = bi;
			ob.x = x;
			ob.y = 465 - bi.getHeight();
			x += obstacleInterval;

			obList.add(ob);
		}
	}

}