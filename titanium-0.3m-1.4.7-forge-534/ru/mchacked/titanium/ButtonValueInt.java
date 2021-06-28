package ru.mchacked.titanium;

public class ButtonValueInt extends ButtonValueA {
	
	public int start;
	public int step;
	public int end;
	public int def;
	
	public ButtonValueInt(int start, int end, int def) {
		this(start, start < end ? 1 : -1, end, def);
	}
	
	public ButtonValueInt(int start, int step, int end, int def) {
		this.start = start;
		this.step = step;
		this.end = end;
		this.def = def;
	}
	
}
