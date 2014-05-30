package io.github.iTitus.MyMod.client.render.item;

import io.github.iTitus.MyMod.client.model.ModelGun;
import io.github.iTitus.MyMod.item.EnumItemType;
import io.github.iTitus.MyMod.lib.LibTextures;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderItemGun implements IItemRenderer {

	public static final RenderItemGun INSTANCE = new RenderItemGun();

	private final ModelGun model;

	private RenderItemGun() {
		model = new ModelGun();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		switch (type) {
		case ENTITY: {
			renderItemGun(-0.5F, 0.0F, 0.5F);
			return;
		}
		case EQUIPPED: {
			renderItemGun(0.0F, 0.0F, 1.0F);
			return;
		}
		case EQUIPPED_FIRST_PERSON: {
			renderItemGun(0.0F, 0.0F, 1.0F);
			return;
		}
		case INVENTORY: {
			renderItemGun(0.0F, -0.1F, 1.0F);
			return;
		}
		default:
		}

	}

	private void renderItemGun(float scaleX, float scaleY, float scaleZ) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(scaleX, scaleY, scaleZ);

		FMLClientHandler.instance().getClient().renderEngine
				.bindTexture(LibTextures.getModelResourceLoc(EnumItemType.GUN));

		model.render();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();

	}

}
