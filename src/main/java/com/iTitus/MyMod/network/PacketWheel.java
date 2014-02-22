package com.iTitus.MyMod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class PacketWheel extends AbstractPacket {

	private double acc, velo, deg;

	public PacketWheel(double acc, double velo, double deg) {
		this.acc = acc;
		this.velo = velo;
		this.deg = deg;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeDouble(acc);
		buffer.writeDouble(velo);
		buffer.writeDouble(deg);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		acc = buffer.readDouble();
		velo = buffer.readDouble();
		deg = buffer.readDouble();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {

	}

}
