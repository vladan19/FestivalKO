package net.etfbl.ip.festivalko.model;

import java.io.Serializable;

public enum UserType implements Serializable{
	GUEST(1),
	REGISTERED(2),
	SUPER_USER(3),
	ADMINISTRATOR(4);
	
	private int value;
	
	private UserType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
