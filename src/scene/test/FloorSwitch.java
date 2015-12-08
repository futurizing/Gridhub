package scene.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import core.geom.Vector2;
import util.Helper;

public class FloorSwitch {

	private final float EPS = 1e-3f;

	private int x, y, z;
	private boolean defaultAssertion;
	private int minimumWeight;

	private int currentWeight = 0;

	protected int getX() {
		return x;
	}

	protected int getY() {
		return y;
	}

	protected int getZ() {
		return z;
	}

	protected boolean isDefaultAssertion() {
		return defaultAssertion;
	}

	protected int getMinimumWeight() {
		return minimumWeight;
	}

	public FloorSwitch(int x, int y, int z, boolean defaultAssertion, int minimumWeight) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.defaultAssertion = defaultAssertion;
		this.minimumWeight = minimumWeight;
		this.currentWeight = 0;
		this.isAsserting = defaultAssertion;
		this.currentCenterAlpha = defaultAssertion ? 1 : 0;
	}

	// For side bar
	private float currentShowingWeight = 0;
	private final double showingWeightSpeedFactor = Math.pow(10, 1.0 / 100);
	// For center cell and assertion
	private int switchProgress = 0;
	private final int switchProgressFactor = 100 * 30;

	private boolean isAsserting;
	private float currentCenterAlpha;
	private final float centerAlphaSpeedFactor = 100 * 10.0f;

	public void update(int step, Player p) {

		// This is just for testing. In production, link FloorSwitch with the
		// HashMap instead.

		if (p.getCellX() == this.x && p.getCellY() == this.y && p.getCellZ() == this.z) {
			this.currentWeight = 10;
		} else {
			this.currentWeight = 0;
		}

		currentShowingWeight += (currentWeight - currentShowingWeight) / Math.pow(showingWeightSpeedFactor, step);

		this.isAsserting = this.defaultAssertion;
		if (currentWeight * switchProgressFactor >= switchProgress) {
			switchProgress += step * currentWeight;
			if (switchProgress > switchProgressFactor * minimumWeight) {
				switchProgress = switchProgressFactor * minimumWeight;
				this.isAsserting = !this.isAsserting;
			}
		} else {
			switchProgress = currentWeight;
			if (switchProgress < 0)
				switchProgress = 0;
		}

		if (this.isAsserting) {
			currentCenterAlpha += step / centerAlphaSpeedFactor;
			if (currentCenterAlpha > 1)
				currentCenterAlpha = 1;
		} else {
			currentCenterAlpha -= step / centerAlphaSpeedFactor;
			if (currentCenterAlpha < 0)
				currentCenterAlpha = 0;
		}

	}

	private final float innerCellShift = 0.4f;
	private final float innerCellSize = 0.8f;

	public void draw(Graphics2D g, Camera camera) {
		Color baseColor = new Color(200, 250, 100);

		Vector2 cornerA = camera.getDrawPosition(x + innerCellShift, y + innerCellShift, z);
		Vector2 cornerB = camera.getDrawPosition(x + innerCellShift, y - innerCellShift, z);
		Vector2 cornerC = camera.getDrawPosition(x - innerCellShift, y - innerCellShift, z);
		Vector2 cornerD = camera.getDrawPosition(x - innerCellShift, y + innerCellShift, z);

		Path2D.Float innerBorderPath = new Path2D.Float();
		innerBorderPath.moveTo(cornerA.getX(), cornerA.getY());
		innerBorderPath.lineTo(cornerB.getX(), cornerB.getY());
		innerBorderPath.lineTo(cornerC.getX(), cornerC.getY());
		innerBorderPath.lineTo(cornerD.getX(), cornerD.getY());
		innerBorderPath.closePath();

		g.setStroke(new BasicStroke(2));
		g.setColor(Helper.getAlphaColor(baseColor, 150));
		g.draw(innerBorderPath);

		g.setColor(Helper.getAlphaColorPercentage(baseColor, currentCenterAlpha / 2.0f));
		g.fill(innerBorderPath);

		if (currentShowingWeight > -EPS) {
			float lineLength = Math.min(minimumWeight, currentShowingWeight) * innerCellSize / minimumWeight;
			Vector2 toA = camera.getDrawPosition(x + innerCellShift, y + innerCellShift - lineLength, z);
			Vector2 toB = camera.getDrawPosition(x + innerCellShift - lineLength, y - innerCellShift, z);
			Vector2 toC = camera.getDrawPosition(x - innerCellShift, y - innerCellShift + lineLength, z);
			Vector2 toD = camera.getDrawPosition(x - innerCellShift + lineLength, y + innerCellShift, z);

			g.setStroke(new BasicStroke(4));
			g.setColor(new Color(150, 200, 50, 150));
			g.draw(new Line2D.Float(cornerA.getX(), cornerA.getY(), toA.getX(), toA.getY()));
			g.draw(new Line2D.Float(cornerB.getX(), cornerB.getY(), toB.getX(), toB.getY()));
			g.draw(new Line2D.Float(cornerC.getX(), cornerC.getY(), toC.getX(), toC.getY()));
			g.draw(new Line2D.Float(cornerD.getX(), cornerD.getY(), toD.getX(), toD.getY()));
		}
	}

}