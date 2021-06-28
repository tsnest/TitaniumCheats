package ru.mchacked.titanium;

public class WindowBase extends TWindow {
	
	protected ISettings Field159;
	protected ModuleRegistrator[] Field160;
	private String[] Field161;
	private Object[] Field162;
	
	public WindowBase(ISettings properties, ModuleRegistrator[] propertyList, String caption, int width) {
		super(caption, width);
		this.Field159 = properties;
		this.Field160 = propertyList;
		this.flush();
	}
	
	@Override
	protected String getText(int i, int rp) {
		if(rp == 0) {
			return this.Field159.getShortName(this.Field160[i].key);
		} else {
			if(rp == 1) {
				switch(this.Field160[i].buttonType) {
					case 1:
						return ((Boolean)this.Field162[i]).booleanValue() ? "§aON" : "§cOFF";
					case 2:
						return String.valueOf(((Integer)this.Field162[i]).intValue());
				}
			}
			
			return null;
		}
	}
	
	@Override
	protected int getPosition(int i, int rp) {
		return rp * 2;
	}
	
	@Override
	protected int Method193(int i) {
		return 2;
	}
	
	@Override
	protected boolean canHighlight(int i) {
		return true;
	}
	
	@Override
	protected int getSize() {
		return this.Field160.length;
	}
	
	@Override
	protected void onEntryClick(int i) {
		this.Field162[i] = this.Field159.setCmd(this.Field160[i].key);
		Titanium.getTitanium().getTGui().flush();
	}
	
	@Override
	public void flush() {
		this.Field161 = new String[this.Field160.length];
		this.Field162 = new Object[this.Field160.length];
		
		for(int i = 0; i < this.Field161.length; ++i) {
			this.Field161[i] = this.Field159.getShortName(this.Field160[i].key);
			this.Field162[i] = this.Field159.getValue((this.Field160[i].key));
		}
	}
	
}
