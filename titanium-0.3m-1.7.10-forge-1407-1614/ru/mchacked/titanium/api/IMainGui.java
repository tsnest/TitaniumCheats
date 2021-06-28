package ru.mchacked.titanium.api;

import net.minecraft.client.gui.FontRenderer;

public interface IMainGui {
	
	/**
	 * Показывает GUI юзеру (закрывая любое другое, если оно активно).
	 */
	void display();
	
	/**
	 * Показывает окно w в центре экрана.
	 */
	void showWindow(IWindow w);
	
	/**
	 * Убирает окно w.
	 */
	void hideWindow(IWindow w);
	
	/**
	 * Отсылает flush всем активным окнам.
	 */
	void flush();
	
	/**
	 * Возвращает объект FontRenderer'a.
	 */
	FontRenderer getFontRenderer();
	
}
