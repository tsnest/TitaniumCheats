package ru.mchacked.titanium;

public class WindowFavorites extends WindowBase {
	
	private Favorites Field133 = Titanium.getTitanium().Method35();
	
	public WindowFavorites(ISettings properties, int width) {
		super(properties, new ModuleRegistrator[0], "Favorites", 100);
		this.flush();
	}
	
	@Override
	public void flush() {
		if(this.Field133 != null) {
			super.Field160 = this.Field133.Method119();
			super.flush();
		}
	}
	
}
