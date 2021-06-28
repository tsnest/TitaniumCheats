package ru.mchacked.titanium.api;

public interface ICommandHandler {
	
	/**
	 * Вызывается при вводе юзером ранее зарегистрированной команды.
	 */
	void handle(String cmd, String[] args);
	
}
