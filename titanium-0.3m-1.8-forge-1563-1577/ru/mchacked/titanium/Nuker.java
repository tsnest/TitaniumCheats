package ru.mchacked.titanium;

import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Nuker extends TimerTask {
	
	private ArrayBlockingQueue Field125 = new ArrayBlockingQueue(1331);
	private int Field126 = 0;
	private Minecraft mc = Minecraft.getMinecraft();
	
	public void Method154(int x, int y, int z) {
		this.Field125.add(new NukePos(x, y, z));
	}
	
	public void Method157() {
		if(!this.Field125.isEmpty()) {
			if(mc.thePlayer != null) {
				if(this.Field126 == 0) {
					this.Field126 = this.Field125.size();
				}
				
				NukePos pos = (NukePos)this.Field125.poll();
				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(pos.x, pos.y, pos.z), EnumFacing.DOWN));
				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, new BlockPos(pos.x, pos.y, pos.z), EnumFacing.DOWN));
				System.out.println("Nuking " + pos.x + ", " + pos.y + ", " + pos.z);
			}
		}
	}
	
	@Override
	public void run() {
		this.Method157();
	}
	
	private class NukePos {
		
		public int x;
		public int y;
		public int z;
		
		public NukePos(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
	}
	
}
