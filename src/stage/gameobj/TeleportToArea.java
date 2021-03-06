package stage.gameobj;

/**
 * This Class represents the TeleportGate which will transfer player to some specific area mark down by
 * {@link TeleportDestination}
 * 
 * @author Thanat
 *
 */
public class TeleportToArea extends TeleportGate {
	private int destinationX, destinationY, destinationZ;
	private transient TeleportDestionation teleportDestination;

	public TeleportToArea(int x, int y, int z, int destinationX, int destinationY, int destinationZ) {
		super(x, y, z);
		// TODO Auto-generated constructor stub
		this.destinationX = destinationX;
		this.destinationY = destinationY;
		this.destinationZ = destinationZ;
		this.teleportDestination = new TeleportDestionation(destinationX, destinationY, destinationZ);
		this.isActive = true;
	}

	/**
	 * Get the TeleportDestination of this TeleportGate.
	 * 
	 * @return
	 */
	public TeleportDestionation getTeleportDestination() {
		return teleportDestination;
	}

	@Override
	public void teleport(Player p) {
		// TODO Auto-generated method stub
		p.setPlayerPosition(destinationX, destinationY, destinationZ);
	}

}
