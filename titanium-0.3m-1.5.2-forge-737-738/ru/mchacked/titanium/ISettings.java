package ru.mchacked.titanium;

public interface ISettings {
	
	int getType(String key);
	
	Object getValue(String key);
	
	Object setCmd(String key);
	
	void setValue(String key, Object o);
	
	String getShortName(String key);
	
}
