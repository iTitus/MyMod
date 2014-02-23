package com.iTitus.MyMod.network;

import com.iTitus.MyMod.tileentiy.TileEntityWheel;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class PacketWheel extends AbstractPacket {

	private int x, y, z;
	private double acc, velo, deg;

	public PacketWheel() {

	}

	public PacketWheel(int x, int y, int z, double acc, double velo, double deg) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.acc = acc;
		this.velo = velo;
		this.deg = deg;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeDouble(acc);
		buffer.writeDouble(velo);
		buffer.writeDouble(deg);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		acc = buffer.readDouble();
		velo = buffer.readDouble();
		deg = buffer.readDouble();
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
		}

	}

	@Override
	public void handleServerSide(EntityPlayer player) {

	}

}
