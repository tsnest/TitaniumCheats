package ru.mchacked.titanium;

public class WindowAbout extends TWindow {
	
	private static String[] Field232 = new String[]{"§eTitanium v" + Titanium.version, "§6Coded by: §bnginx", "§6ReBuild by: §bMcHacked Team", "", "§7Addons loaded: §a%al"};
	private String[] Field233;
	
	public WindowAbout(String caption, int width) {
		super(caption, width);
		this.onShow();
	}
	
	@Override
	protected String getText(int i, int rp) {
		if(i < Field232.length) {
			return this.Field233[i];
		} else {
			switch(i - Field232.length) {
			case 0:
				return "Addons...";
			default:
				return "<unknown>";
			}
		}
	}
	
	@Override
	protected int getSize() {
		return this.Field233.length + 1;
	}
	
	@Override
	protected boolean canHighlight(int i) {
		return i >= Field232.length;
	}
	
	@Override
	protected void onEntryClick(int i) {
		if(i >= Field232.length) {
			switch(i - Field232.length) {
				case 0:
					Titanium.getTitanium().getTGui().showWindow(WindowRegistrator.getHashedWindow("addons"));
				default:
			}
		}
	}
	
	@Override
	public void onShow() {
		super.onShow();
		this.Field233 = new String[Field232.length];
		
		for(int i = 0; i < Field232.length; ++i) {
			String t = Field232[i];
			t = t.replace("%al", String.valueOf(Titanium.getTitanium().Method41().Method11().length));
			this.Field233[i] = t;
		}
	}
	
}
