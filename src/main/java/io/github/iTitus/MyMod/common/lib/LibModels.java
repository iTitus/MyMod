package io.github.iTitus.MyMod.common.lib;

import io.github.iTitus.MyMod.MyMod;
import net.minecraft.util.ResourceLocation;

public class LibModels {

	public static enum Models {
		BULLET("bullet");

		public final String model;

		private Models(String model) {
			this.model = model;
		}

		public ResourceLocation getModelResoureLoc() {
			return new ResourceLocation(MyMod.MOD_ID, "models/" + model
					+ ".obj");
		}

	}
}
