package ru.rushcraft.legacy.clans;

public enum ClanStatus {
	
	WITHOUT((byte) 0),
	MEMBER((byte) 1),
	ZAM((byte) 2),
	LEADER((byte) 3);
	
	private final byte priority;
	
	ClanStatus(byte priority) {
		this.priority = priority;
	}

	public byte getPriority() {
		return priority;
	}
}
