package ru.mchacked.titanium;

import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import ru.mchacked.titanium.api.Hooks;
import ru.mchacked.titanium.api.ICommandHandler;

public class Commands implements ICommandHandler {
	
	private static HashMap Field0 = new HashMap();
	private Minecraft mc = Minecraft.getMinecraft();
	private static String cmds = "bind,unbind,rocket,nuke,chat,fdelay,ftype,fstart,fstop";
	
	public static void register() {
		Commands inst = new Commands();
		Method0(cmds.split(","), inst);
		String[] s = new String[1];
		
		ModuleRegistrator[][] arr$ = ModuleRegistrator.categories;
		int len$ = arr$.length;
		for(int i$ = 0; i$ < len$; ++i$) {
			ModuleRegistrator[] p = arr$[i$];
			ModuleRegistrator[] arr$1 = p;
			int len$1 = p.length;
			for(int i$1 = 0; i$1 < len$1; ++i$1) {
				ModuleRegistrator pp = arr$1[i$1];
				s[0] = pp.key;
				Method0(s, inst);
			}
		}
	}
	
	private void echo(String s) {
		mc.thePlayer.addChatMessage(s);
	}
	
	@Override
	public void handle(String cmd, String[] args) {
		Settings settings = Titanium.getTitanium().getSettings();
		if(settings.isCmdExists(cmd) && args.length == 0) {
			settings.setCmd(cmd);
			Titanium.getTitanium().getTGui().flush();
		} else {
			int e;
			StringBuilder var28;
			char var33;
			if(cmd.equalsIgnoreCase("bind")) {
				if(args.length >= 2 && args[0].length() == 1 && args[1].length() != 0) {
					var33 = args[0].charAt(0);
					var28 = new StringBuilder();
					
					for(e = 1; e < args.length; ++e) {
						if(e != 1) {
							var28.append(' ');
						}
						
						var28.append(args[e]);
					}
					
					Titanium.getTitanium().Method37().Method22(var33, var28.toString());
				} else {
					this.echo("§c`bind [key] [command] - биндит команду на кнопку");
				}
			} else if(cmd.equals("unbind")) {
				if(args.length == 1 && args[0].length() == 1) {
					var33 = args[0].charAt(0);
					Titanium.getTitanium().Method37().Method23(var33);
				} else {
					this.echo("§c`unbind [key] - разбиндивает кнопку");
				}
			} else {
				boolean var29;
				if(cmd.equals("xr")) {
					Xray var32 = Titanium.getTitanium().getXray();
					if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
						this.echo("§aСейчас отображаются блоки с ID: " + var32.getBlocksList());
					} else if(args.length >= 2 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("del"))) {
						var29 = args[0].equalsIgnoreCase("add");
						
						for(e = 1; e < args.length; ++e) {
							if(var29) {
								var32.addBlock(args[e]);
							} else {
								var32.removeBlock(args[e]);
							}
						}
						
						this.echo("§aСписок блоков обновлен");
						if(Hooks.xray) {
							mc.renderGlobal.loadRenderers();
						}
					} else {
						this.echo("§c`xray [del|add] id id2 id3... - добавление или удаление блоков из XRay-списка");
						this.echo("§c`xray list - вывод активных блоков");
					}
				} else if(cmd.equals("ka")) {
					KillAuraFriends var31 = Titanium.getTitanium().Method38();
					if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
						this.echo("§aИгроки, игнорируемые KillAur\'ой: " + Arrays.toString(var31.Method27()));
					} else if(args.length >= 2 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("del"))) {
						var29 = args[0].equalsIgnoreCase("add");
						
						for(e = 1; e < args.length; ++e) {
							if(var29) {
								var31.Method28(args[e]);
							} else {
								var31.Method29(args[e]);
							}
						}
						
						this.echo("§aСписок игроков обновлен");
					} else {
						this.echo("§c`ka [del|add] user1 user2... - добавление или удаление игроков из игнора");
						this.echo("§c`ka list - вывод игнорируемых игроков");
					}
				} else {
					int var27;
					if(cmd.equals("rocket")) {
						var27 = 100;
						if(args.length > 0) {
							try {
								var27 = Integer.parseInt(args[0]);
							} catch (NumberFormatException var20) {
								var20.printStackTrace();
								this.echo("§c`rocket <speed> - взлет вверх. Скорость от нуля до ста");
								return;
							}
						}
						
						if(var27 < 0) {
							var27 = 0;
						}
						
						if(var27 > 100) {
							var27 = 100;
						}
						
						mc.thePlayer.setVelocity(0.0D, 7.5D * (double)((float)var27 / 100.0F), 0.0D);
					} else if(cmd.equals("nuke")) {
						if(args.length < 2) {
							this.echo("§c`nuke <radius> <id1> <id2> <...> - удаляет определенные блоки в радиусе");
							this.echo("§c`nuke <radius> all - удаляет вообще все в радиусе");
						} else {
							try {
								var27 = Integer.parseInt(args[0]);
							} catch (NumberFormatException ex) {
								this.echo("§cРадиус должен быть числом!");
								return;
							}
							
							if(var27 > 5) {
								var27 = 5;
								this.echo("§eМаксимальный радиус - 5");
							}
							
							var29 = args[1].equalsIgnoreCase("all");
							int[] var30 = null;
							int pX;
							if(!var29) {
								var30 = new int[args.length - 1];
								
								for(pX = 1; pX < args.length; ++pX) {
									try {
										var30[pX - 1] = Integer.parseInt(args[pX]);
									} catch (NumberFormatException var21) {
										this.echo("§cАргумент номер " + (pX + 1) + " имеет неверное значение: должно быть число");
										return;
									}
								}
							}
							
							pX = (int)mc.thePlayer.posX;
							int pY = (int)mc.thePlayer.boundingBox.minY;
							int pZ = (int)mc.thePlayer.posZ;
							for(int dX = -var27; dX <= var27; ++dX) {
								for(int dY = -var27; dY <= var27; ++dY) {
									for(int dZ = -var27; dZ <= var27; ++dZ) {
										int id = mc.theWorld.getBlockId(pX + dX, pY + dY, pZ + dZ);
										if(id != 0) {
											boolean nuke = var29;
											if(!var29) {
												int[] arr$ = var30;
												int len$ = var30.length;
												for(int i$ = 0; i$ < len$; ++i$) {
													int j = arr$[i$];
													if(j == id) {
														nuke = true;
														break;
													}
												}
											}
											
											if(nuke) {
												Titanium.getTitanium().Method39().Method154(pX + dX, pY + dY, pZ + dZ);
											}
										}
									}
								}
							}
							
							this.echo("§aNuke them all!");
						}
					} else {
						int c;
						if(cmd.equals("chat")) {
							StringBuilder var26 = new StringBuilder();
							
							for(c = 0; c < args.length; ++c) {
								if(c != 0) {
									var26.append(' ');
								}
								
								var26.append(args[c]);
							}
							
							mc.thePlayer.sendChatMessage(var26.toString());
						} else {
							Flooder flooder = Titanium.getTitanium().Method40();
							if(cmd.equals("fdelay")) {
								if(args.length == 0) {
									this.echo("§eТекущий множитель: " + flooder.Method80() + " §7(x100 ms)");
								} else {
									try {
										flooder.setDelay(Integer.parseInt(args[0]));
										this.echo("§aМножитель установлен");
									} catch (NumberFormatException var23) {
										var23.printStackTrace();
										this.echo("§cЭто не число!");
									}
								}
							} else if(cmd.equals("ftype")) {
								if(args.length < 2) {
									this.echo("§c`ftype rnd [length] - флуд рандомными сообщениями");
									this.echo("§c`ftype msg [msg] - флуд определенным сообщением со случайной припиской");
								} else {
									if(args[0].equalsIgnoreCase("rnd")) {
										try {
											c = Integer.parseInt(args[1]);
										} catch (NumberFormatException var24) {
											var24.printStackTrace();
											this.echo("§cДлинна сообщения должна быть числом!");
											return;
										}
										
										flooder.Method73(c);
									} else if(args[0].equalsIgnoreCase("msg")) {
										var28 = new StringBuilder();
										
										for(e = 1; e < args.length; ++e) {
											if(e != 1) {
												var28.append(' ');
											}
											
											var28.append(args[e]);
										}
										
										flooder.Method74(var28.toString());
									} else {
										this.echo("§cНеизвестный тип флуда");
									}
								}
							} else if(cmd.equals("fstart")) {
								if(args.length < 1) {
									this.echo("§c`fstart [count] - запускает флуд");
								} else {
									try {
										c = Integer.parseInt(args[0]);
									} catch (NumberFormatException var25) {
										var25.printStackTrace();
										this.echo("§cЭто не число!");
										return;
									}
									
									flooder.start(c);
									this.echo("§aФлуд запущен");
								}
							} else if(cmd.equals("fstop")) {
								flooder.Method75();
								this.echo("§aФлуд остановлен");
							}
						}
					}
				}
			}
		}
	}
	
	public static void Method0(String[] cmds, ICommandHandler hndl) {
		String[] arr$ = cmds;
		int len$ = cmds.length;
		
		for(int i$ = 0; i$ < len$; ++i$) {
			String s = arr$[i$];
			Field0.put(s.toLowerCase(), hndl);
		}
	}
	
	public static void Method1(String[] args) {
		if(args.length != 0) {
			String cmd = args[0];
			String[] rargs = new String[args.length - 1];
			
			for(int h = 1; h < args.length; ++h) {
				rargs[h - 1] = args[h];
			}
			
			ICommandHandler var4 = (ICommandHandler)Field0.get(cmd.toLowerCase());
			if(var4 == null) {
				throw new RuntimeException("!NOCMD");
			} else {
				var4.handle(cmd, rargs);
			}
		}
	}
	
}
