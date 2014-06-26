package io.github.iTitus.MyMod.common.lib;

import net.minecraft.util.ResourceLocation;

public class LibModels {

	public static enum Models {
		BULLET("bullet");

		public final String model;

		private Models(String model) {
			this.model = model;
		}

		public ResourceLocation getModelResoureLoc() {
			return new ResourceLocation(LibMod.MOD_ID, "models/" + model
					+ ".obj");
		}

	}
}
