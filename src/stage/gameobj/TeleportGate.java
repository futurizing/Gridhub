package stage.gameobj;

import java.awt.Graphics2D;

import core.geom.Vector3;
import stage.Camera;
import stage.ObjectMap;
import stage.gameobj.IDrawable;
import stage.gameobj.IWalkOnAble;

/**
 * @author Thanat
 *
 */
public abstract class TeleportGate implements IDrawable, IWalkOnAble ,IControlable{
	protected int x, y, z;
	protected boolean isActive, isAsserted;
	protected transient ObjectMap objectMap;

	public TeleportGate(int x, int y, int z) {
		this.x = x;
		this.y = y; 
		this.z = z;
		isAsserted = true;
	}

	protected abstract void teleport(Player p);

	// the progress checking is just a mock up. It should be set up later
	protected final int teleportProgressControl = 100 * 50;
	protected int teleportProgress = 0;
	
	public void setObjectMap(ObjectMap objectMap) {
		this.objectMap = objectMap;
	}

	public void update(int step) {
		
		if (!isObjectAbove()) {
			isActive = true;
		}
		if (isObjectAbove() && isActive && isAsserted) {
			teleportProgress += step;

		} else {
			teleportProgress = 0;
		}
		if (teleportProgress >= teleportProgressControl) {
			teleportProgress = 0;
			teleport(getPlayerAbove());
		}

	}

	@Override
	public void draw(Graphics2D g, Camera camera) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isObjectAbove() {
		// teleportGate check only player above
		if (getPlayerAbove() != null) {
			return true;
		}
		return false;
	}

	public Player getPlayerAbove() {
		if (objectMap.drawableObjectHashMap
				.get(new ObjectVector(x, y, z, "Player" + util.Constants.PLAYER1_ID)) != null) {
			return (Player) (objectMap.drawableObjectHashMap
					.get(new ObjectVector(x, y, z, "Player" + util.Constants.PLAYER1_ID)));

		} else if (objectMap.drawableObjectHashMap
				.get(new ObjectVector(x, y, z, "Player" + util.Constants.PLAYER2_ID)) != null) {
			return (Player) (objectMap.drawableObjectHashMap
					.get(new ObjectVector(x, y, z, "Player" + util.Constants.PLAYER2_ID)));
		}

		return null;
	}
	
	@Override
	public void activate() {
		this.isAsserted = true;
		
	}

	@Override
	public void deActivate() {
		this.isAsserted = false;
		
	}
	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isAsserted() {
		return isAsserted;
	}

	public void setAsserted(boolean isAsserted) {
		this.isAsserted = isAsserted;
	}

	@Override
	public Vector3 getDrawPosition() {
		return new Vector3(this.x, this.y, this.z);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

}
