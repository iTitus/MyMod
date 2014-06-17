package io.github.iTitus.MyMod.lib;

import java.util.Locale;

import net.minecraft.util.ResourceLocation;

public class LibModels {

	public static enum Models {
		BULLET("bullet");

		public final String model;

		private Models(String model) {
			this.model = model;
		}

		public ResourceLocation getModelResoureLoc() {
			return new ResourceLocation(MODEL_LOCATION, "models/" + model
					+ ".obj");
		}

	}

	public static final String MODEL_LOCATION = LibMod.MOD_ID
			.toLowerCase(Locale.ENGLISH);

}
