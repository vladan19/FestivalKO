package net.etfbl.ip.festivalko.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum UserType implements Serializable{
	GUEST(1),
	REGISTERED(2),
	SUPER_USER(3),
	ADMINISTRATOR(4);
	
	private int value;
	private static Map<Integer, UserType> map = new HashMap<>();
	
	private UserType(int value) {
		this.value = value;
	}
	
	static {
		for (UserType usertype : UserType.values()) {
			map.put(usertype.value, usertype);
		}
	}
	
	public static UserType valueOf(int userType) {
		return map.get(userType);
	}

	public int getValue() {
		return value;
	}
}
