package ru.mchacked.titanium;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import net.minecraft.client.Minecraft;

public class Flooder extends TimerTask {
	
	public static String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private volatile int count;
	private volatile int Field75;
	private volatile int delay;
	private volatile int length;
	private volatile String message;
	private int Field79 = 0;
	private Random rnd = new Random();
	private Minecraft mc = Minecraft.getMinecraft();
	private Timer timer = new Timer(true);
	
	public Flooder() {
		this.timer.scheduleAtFixedRate(this, 100L, 100L);
	}
	
	public void Method73(int length) {
		this.Field75 = 1;
		this.length = length;
	}
	
	public void Method74(String msg) {
		this.Field75 = 2;
		this.message = msg;
	}
	
	public void Method75() {
		this.Field75 = 0;
		this.count = 0;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void start(int count) {
		this.count = count;
	}
	
	public int Method79() {
		return this.count;
	}
	
	public int Method80() {
		return this.delay;
	}
	
	@Override
	public void run() {
		if(this.count != 0) {
			if(mc.thePlayer != null) {
				if(++this.Field79 >= this.delay) {
					this.Field79 = 0;
					--this.count;
					StringBuilder sb;
					if(this.Field75 == 1) {
						sb = new StringBuilder();
						
						for(int i = 0; i < this.length; ++i) {
							sb.append(this.characters.charAt(this.rnd.nextInt(this.characters.length())));
						}
						
						mc.thePlayer.sendChatMessage(sb.toString());
					}
					
					if(this.Field75 == 2) {
						sb = new StringBuilder(this.message);
						sb.append(' ');
						sb.append(this.rnd.nextInt());
						mc.thePlayer.sendChatMessage(sb.toString());
					}
				}
			}
		}
	}
	
}
