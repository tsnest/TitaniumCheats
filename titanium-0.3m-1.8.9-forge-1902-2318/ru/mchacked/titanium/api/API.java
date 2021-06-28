package ru.mchacked.titanium.api;

import ru.mchacked.titanium.Commands;
import ru.mchacked.titanium.Titanium;
import ru.mchacked.titanium.WindowMain;

/**
 * Класс для взаимодействия с API.
 */
public class API {
	
	private static API api = new API();
	
	/**
	 * Добавляет строчку в начальное окно GUI, при клике по которой открывается
	 * окно, переданное во втором параметре.
	 */
	public void registerGUI(String caption, IWindow window) {
		WindowMain.Method158(window, caption);
	}
	
	/**
	 * Регистрирует новый бинд на сканкод key, при изменении состояния которого
	 * вызывается метод onKeyEvent для объекта bind. Все сканкоды можно посмотреть
	 * в интернете или в классе Keyboard (там они доступы в виде переменных с именами
	 * KEY_*)
	 * ПРИМЕЧАНИЕ: не рекомендуется использовать данный метод для бинда к буквенным клавишам.
	 * Зарегистрируйте команду и дайте юзеру самому выбрать, на какую кнопку ему
	 * вешать данную возможность.
	 */
	public void registerBind(int key, IKeyBind bind) {
		Titanium.getTitanium().registerBind(key, bind);
	}
	
	/**
	 * Регистрирует команды, перечисленные в массиве cmds, и связывает их с обработчиком ch.
	 */
	public void registerCommands(String[] cmds, ICommandHandler ch) {
		Commands.Method0(cmds, ch);
	}
	
	/**
	 * Возвращает объект основного чит-GUI.
	 */
	public IMainGui getGUI() {
		return Titanium.getTitanium().getTGui();
	}
	
	/**
	 * Возвращает значение определенной настройки. Пример:
	 * getSetting("xr") - вернет true, если X-Ray включен
	 */
	public Object getSetting(String key) {
		return Titanium.getTitanium().getSettings().getValue(key);
	}
	
	/**
	 * Устанавливает новое значение настройки. Пример:
	 * setSetting("xr", true) - включает X-Ray.
	 * Кроме того, при любом вызове этого метода идет flush() в GUI.
	 */
	public void setSetting(String key, Object v) {
		Titanium.getTitanium().getSettings().setValue(key, v);
		this.getGUI().flush();
	}
	
	/**
	 * Возвращает объект текущего класса.
	 */
	public static API getAPI() {
		return api;
	}
	
}
