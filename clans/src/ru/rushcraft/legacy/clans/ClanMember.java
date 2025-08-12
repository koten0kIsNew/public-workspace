package ru.rushcraft.legacy.clans;

public class ClanMember {
	
	private ClanStatus status;
	private boolean isSecondInput, isSecond;
	
	public ClanMember(ClanStatus status) {
		this.status = status;
		this.isSecondInput = false;
		this.isSecond = false;
	}

	public ClanStatus getStatus() {
		return status;
	}

	public void setStatus(ClanStatus status) {
		this.status = status;
	}

	public boolean isSecondInput() {
		return isSecondInput;
	}

	public void setSecondInput(boolean isSecondInput) {
		this.isSecondInput = isSecondInput;
	}

	public boolean isSecond() {
		return isSecond;
	}

	public void setSecond(boolean isSecond) {
		this.isSecond = isSecond;
	}
}
