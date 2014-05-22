package com.iTitus.MyMod.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

import com.iTitus.MyMod.tileentity.wheel.TileEntityWheel;

public class PacketWheel extends AbstractPacket {

	private double acc, velo, deg;
	private int x, y, z, mode;

	public PacketWheel() {
	}

	public PacketWheel(int x, int y, int z, double acc, double velo,
			double deg, int mode) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.acc = acc;
		this.velo = velo;
		this.deg = deg;
		this.mode = mode;
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		acc = buffer.readDouble();
		velo = buffer.readDouble();
		deg = buffer.readDouble();
		mode = buffer.readInt();
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeDouble(acc);
		buffer.writeDouble(velo);
		buffer.writeDouble(deg);
		buffer.writeInt(mode);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

		if (player.worldObj.getTileEntity(x, y, z) != null
				&& player.worldObj.getTileEntity(x, y, z) instanceof TileEntityWheel) {
			TileEntityWheel wheel = (TileEntityWheel) player.worldObj
					.getTileEntity(x, y, z);

			wheel.setAcc(acc);
			wheel.setVelo(velo);
			wheel.setDeg(deg);

			wheel.setMode(TileEntityWheel.Mode.getModeForOrdinal(mode));

		}

	}

	@Override
	public void handleServerSide(EntityPlayer player) {

	}

}
