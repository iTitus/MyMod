package io.github.ititus.mymod.recipe.dumper;

import com.google.common.collect.Lists;
import io.github.ititus.mymod.compat.jei.MyModJEIPlugin;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecipeDumper {

    private static final String NAME = "dump_recipes";
    private static final String SEPARATOR = ",";
    private static final String FILE_EXTENSION = "txt";
    private static File dumpFolder;

    public static void dumpAll(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("Dumping recipes..."));

        if (dumpFolder == null) {
            init();
        }

        List<IRecipeCategory> recipeCategories = MyModJEIPlugin.jeiRuntime.getRecipeRegistry().getRecipeCategories();
        StringBuilder sb = new StringBuilder();
        String s;

        sb.append("recipeCategory,modName,recipeCount,hasHandler" + System.lineSeparator());
        for (IRecipeCategory<? extends IRecipeWrapper> recipeCategory : recipeCategories) {
            List<String> list = Lists.newArrayList(
                    recipeCategory.getUid(),
                    recipeCategory.getModName(),
                    "" + MyModJEIPlugin.jeiRuntime.getRecipeRegistry().getRecipeWrappers(recipeCategory).size(),
                    "" + !RecipeDumperRegistry.getHandler(recipeCategory).getPropertyList(recipeCategory).isEmpty()
            );
            sb.append(list.stream().map(str -> str.contains(" ") ? '"' + str.replace("\"", "\"\"") + '"' : str).collect(Collectors.joining(SEPARATOR)) + System.lineSeparator());
        }

        s = sb.toString();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(new File(dumpFolder, "recipeCategories" + "." + FILE_EXTENSION)), StandardCharsets.UTF_8)) {
            writer.write(s);
        } catch (Throwable t) {
            t.printStackTrace();
            sender.sendMessage(new TextComponentString("Error: " + t));
        }


        for (IRecipeCategory<? extends IRecipeWrapper> recipeCategory : recipeCategories) {
            List<? extends IRecipeWrapper> recipeWrappers = MyModJEIPlugin.jeiRuntime.getRecipeRegistry().getRecipeWrappers(recipeCategory);

            IRecipeCategoryDumperHandler categoryHandler = RecipeDumperRegistry.getHandler(recipeCategory);
            List<String> list = categoryHandler.getPropertyList(recipeCategory);

            if (!list.isEmpty()) {
                sb = new StringBuilder();
                sb.append(list.stream().map(str -> str.contains(" ") ? '"' + str.replace("\"", "\"\"") + '"' : str).collect(Collectors.joining(SEPARATOR)) + System.lineSeparator());

                for (IRecipeWrapper recipeWrapper : recipeWrappers) {
                    IRecipeDumperHandler recipeHandler = categoryHandler.getRecipeDumperHandler(recipeCategory, recipeWrapper, list);
                    Map<String, String> map = recipeHandler.getPropertyMap(recipeCategory, recipeWrapper, list);
                    sb.append(list.stream().map(map::get).map(str -> str.contains(" ") ? '"' + str.replace("\"", "\"\"") + '"' : str).collect(Collectors.joining(SEPARATOR)) + System.lineSeparator());
                }

                s = sb.toString();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(new File(dumpFolder, recipeCategory.getUid().replaceAll("[:\\\\/*?|<>]", "_") + "." + FILE_EXTENSION)), StandardCharsets.UTF_8)) {
                    writer.write(s);
                } catch (Throwable t) {
                    t.printStackTrace();
                    sender.sendMessage(new TextComponentString("Error: " + t));
                }
            }

        }

        sender.sendMessage(new TextComponentString("Done!"));
    }

    public static void init() {
        dumpFolder = new File(Minecraft.getMinecraft().gameDir, NAME);
        dumpFolder.mkdirs();

        if (Loader.isModLoaded("extrautils2")) {
            RecipeDumperRegistry.addHandler(c -> c.getUid().startsWith("xu2_machine_extrautils2"), new RecipeDumperHandlerXUMachine());
        }
    }

}
