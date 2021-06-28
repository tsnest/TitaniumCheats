package ru.mchacked.titanium.api;

public interface IKeyBind {
	
	/**
	 * Вызывается при нажатии забинденной кнопки.
	 * key - скан-код кнопки
	 * newState - нажата - true, отжата - false
	 */
	void onKeyEvent(int key, boolean newState);
	
}
