package ru.mchacked.titanium;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;

public class Xray {
	
	public static int[] defBlocks = new int[]{ 5, 14, 15, 16, 21, 22, 41, 42, 48, 49, 56, 57, 73, 74, 98, 89, 129, 133, 153 };
	private boolean[][] blocks = new boolean[4096][16];
	
	public Xray() {
		this.clear();
	}
	
	public void clear() {
		for(int i = 0; i < 4096; ++i) {
			for(int j = 0; j < 16; ++j) {
				this.blocks[i][j] = false;
			}
		}
	}
	
	public boolean isXrayBlock(int id, int meta) {
		if(meta < 0) {
			meta = -meta;
		}
		
		return this.blocks[id % 4096][meta % 16];
	}
	
	private void setBlock(int id, boolean value) {
		for(int i = 0; i < 16; ++i) {
			this.blocks[id][i] = value;
		}
	}
	
	private int[] idParser(String s) {
		String[] sp = s.split(":");
		if(sp.length > 2) {
			return null;
		} else {
			try {
				int e = Integer.parseInt(sp[0]);
				if(sp.length == 2) {
					if(sp[1].equals("*")) {
						return new int[]{e};
					} else {
						int meta = Integer.parseInt(sp[1]);
						return new int[]{e, meta};
					}
				} else {
					return new int[]{e, 0};
				}
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}
	
	public void addBlock(String s) {
		int[] id = this.idParser(s);
		if(id != null) {
			if(id.length == 1) {
				this.setBlock(id[0], true);
			} else {
				this.blocks[id[0]][id[1]] = true;
			}
		}
	}
	
	public void removeBlock(String s) {
		int[] id = this.idParser(s);
		if(id != null) {
			if(id.length == 1) {
				this.setBlock(id[0], false);
			} else {
				this.blocks[id[0]][id[1]] = false;
			}
		}
	}
	
	private void addDefaultBlocks() {
		this.clear();
		System.out.println("Adding XRay defaults...");
		
		int[] arr$ = defBlocks;
		int len$ = arr$.length;
		for(int i$ = 0; i$ < len$; ++i$) {
			int i = arr$[i$];
			this.setBlock(i, true);
		}
	}
	
	public void loadBlocks(NBTTagCompound nbt) {
		this.clear();
		if(!nbt.hasKey("XRay")) {
			this.addDefaultBlocks();
		} else {
			NBTTagCompound xr = nbt.getCompoundTag("XRay");
			int v = xr.getInteger("StorageVersion");
			if(v != 1) {
				this.addDefaultBlocks();
			} else {
				byte[] stored = xr.getByteArray("Blocks");
				
				for(int i = 0; i < 4096; ++i) {
					for(int j = 0; j < 16; ++j) {
						int b = stored[i * 2 + j / 8] & 255;
						this.blocks[i][j] = (b & 1 << j % 8) != 0;
					}
				}
			}
		}
	}
	
	private void appendDelimiter(StringBuilder sb) {
		if(sb.length() != 0) {
			sb.append(", ");
		}
	}
	
	public String getBlocksList() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < 4096; ++i) {
			int blockState = 0;
			
			int j;
			for(j = 0; j < 16; ++j) {
				if(this.blocks[i][j]) {
					++blockState;
				}
			}
			
			if(blockState != 0) {
				this.appendDelimiter(sb);
				if(blockState == 16) {
					sb.append(i);
				} else {
					for(j = 0; j < 16; ++j) {
						if(this.blocks[i][j]) {
							this.appendDelimiter(sb);
							sb.append(i);
							sb.append(':');
							sb.append(j);
						}
					}
				}
			}
		}
		
		return sb.toString();
	}
	
	public void init(NBTTagCompound nbt) {
		NBTTagCompound xr = new NBTTagCompound();
		byte[] storage = new byte[8192];
		Arrays.fill(storage, (byte)0);
		
		for(int i = 0; i < 4096; ++i) {
			int mask = 0;
			
			for(int j = 0; j < 16; ++j) {
				if(this.blocks[i][j]) {
					mask |= 1 << j;
				}
			}
			
			storage[i * 2] = (byte)mask;
			storage[i * 2 + 1] = (byte)(mask >> 8);
		}
		
		xr.setByteArray("Blocks", storage);
		xr.setInteger("StorageVersion", 1);
		nbt.setTag("XRay", xr);
	}
	
}
