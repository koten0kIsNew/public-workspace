package ru.kotenokdev.kenderchest;

import java.util.UUID;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;

public class Hook {

	private PlayerPointsAPI sapphires;
	
	public Hook() {
		sapphires = PlayerPoints.getInstance().getAPI();
	}
	
	public void take(UUID uuid, int amount) {
		sapphires.take(uuid, amount);
	}
	
	public int get(UUID uuid) {
		return sapphires.look(uuid);
	}
}
