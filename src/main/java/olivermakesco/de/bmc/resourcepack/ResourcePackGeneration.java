package olivermakesco.de.bmc.resourcepack;

import com.nimbusds.jose.shaded.json.JSONObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResourcePackGeneration {

    public static Path modsPath = Path.of(FabricLoader.getInstance().getGameDir() + "/mods");
    public static Path bedrockResoucePath = Path.of(FabricLoader.getInstance().getGameDir() + "/bedrockResources");
    public static Path bedrockTexturePath = Path.of(FabricLoader.getInstance().getGameDir() + "/bedrockResources/textures");
    public static Path bedrockItemTexturePath = Path.of(FabricLoader.getInstance().getGameDir() + "/bedrockResources/textures/items");
    public static Path bedrockBlockTexturePath = Path.of(FabricLoader.getInstance().getGameDir() + "/bedrockResources/textures/blocks");
    public static File bedrockBlockTextureJsonPath = new File(FabricLoader.getInstance().getGameDir() + "/bedrockResources/textures/terrain_texture.json");
    public static File bedrockItemTextureJsonPath = new File(FabricLoader.getInstance().getGameDir() + "/bedrockResources/textures/item_texture.json");
    public static File bedrockBlockJsonPath = new File(FabricLoader.getInstance().getGameDir() + "/bedrockResources/blocks.json");
    public static Path bedrockLangPath = Path.of(FabricLoader.getInstance().getGameDir() + "/bedrockResources/texts");
    public static File bedrockEnUsPath = new File(FabricLoader.getInstance().getGameDir() + "/bedrockResources/texts/en_US.lang");

    public static void generateResourcePack() {
        if (bedrockResoucePath.toFile().exists()) {
            if (!bedrockTexturePath.toFile().exists()) {
                try {
                    Files.createDirectory(bedrockTexturePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!bedrockItemTexturePath.toFile().exists()) {
                try {
                    Files.createDirectory(bedrockItemTexturePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!bedrockBlockTexturePath.toFile().exists()) {
                try {
                    Files.createDirectory(bedrockBlockTexturePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!bedrockItemTextureJsonPath.exists()) {
                try {
                    Files.createFile(bedrockItemTextureJsonPath.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!bedrockBlockJsonPath.exists()) {
                try {
                    Files.createFile(bedrockBlockJsonPath.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!bedrockBlockTextureJsonPath.exists()) {
                try {
                    Files.createFile(bedrockBlockTextureJsonPath.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!bedrockLangPath.toFile().exists()) {
                try {
                    Files.createDirectory(bedrockLangPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!bedrockEnUsPath.exists()) {
                try {
                    Files.createFile(bedrockEnUsPath.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            List<String> langs = new ArrayList<>();
            for (int i = 0; i < Registries.ITEM.stream().count(); i++) {
                if (!Registries.ITEM.getId(Registries.ITEM.get(i)).getNamespace().equals("minecraft")) {
                    String itemPath = Registries.ITEM.getId(Registries.ITEM.get(i)).getPath();
                    String itemName = snakeToCamel(itemPath);
                    String cleanItemName = itemName.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");

                    langs.add("item." + Registries.ITEM.getId(Registries.ITEM.get(i)).getPath() + ".name=" + cleanItemName);
                }
            }
            try {
                Files.write(bedrockEnUsPath.toPath(), langs, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Items
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("resource_pack_name", "BedrockModCompatResources");
                jsonObject.put("texture_name", "atlas.items");

                JSONObject newObject = new JSONObject();
                jsonObject.put("texture_data", newObject);

                for (int i = 0; i < Registries.ITEM.stream().count(); i++) {
                    if (!Registries.ITEM.getId(Registries.ITEM.get(i)).getNamespace().equals("minecraft")) {
                        JSONObject textureData = new JSONObject();
                        textureData.put("textures", "textures/items/" + Registries.ITEM.getId(Registries.ITEM.get(i)).getPath());
                        newObject.put(Registries.ITEM.getId(Registries.ITEM.get(i)).getPath(), textureData);
                    }
                }

                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(bedrockItemTextureJsonPath.getPath()));
                stream.writeObject(jsonObject.toString());
                stream.flush();
                stream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            //Blocks
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("resource_pack_name", "BedrockModCompatResources");
                jsonObject.put("texture_name", "atlas.terrain");
                jsonObject.put("padding", 8);
                jsonObject.put("num_mip_levels", 4);

                JSONObject newObject = new JSONObject();
                jsonObject.put("texture_data", newObject);

                for (int i = 0; i < Registries.BLOCK.stream().count(); i++) {
                    if (!Registries.BLOCK.getId(Registries.BLOCK.get(i)).getNamespace().equals("minecraft")) {
                        JSONObject textureData = new JSONObject();
                        textureData.put("textures", "textures/blocks/" + Registries.BLOCK.getId(Registries.BLOCK.get(i)).getPath());
                        newObject.put(Registries.BLOCK.getId(Registries.BLOCK.get(i)).getPath(), textureData);
                    }
                }

                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(bedrockBlockTextureJsonPath.getPath()));
                stream.writeObject(jsonObject.toString());
                stream.flush();
                stream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < Registries.BLOCK.stream().count(); i++) {
                    if (!Registries.BLOCK.getId(Registries.BLOCK.get(i)).getNamespace().equals("minecraft")) {
                        JSONObject component = new JSONObject();
                        component.put("textures", Registries.BLOCK.getId(Registries.BLOCK.get(i)).getPath());
                        jsonObject.put(Registries.BLOCK.getId(Registries.BLOCK.get(i)).getNamespace() + ":zzz" + Registries.BLOCK.getId(Registries.BLOCK.get(i)).getPath(), component);
                    }
                }

                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(bedrockBlockJsonPath.getPath()));
                stream.writeObject(jsonObject.toString());
                stream.flush();
                stream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            //Extract textures
            try {
                Files.walk(Path.of(FabricLoader.getInstance().getGameDir() + "/mods")).forEach((path -> {
                    try {
                        extractModJar(path.toFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Files.walk(bedrockTexturePath).forEach(path -> renameFolder(path.toFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Finished generating Bedrock resource pack");
        } else {
            try {
                Files.createDirectory(bedrockResoucePath);

                if (!bedrockTexturePath.toFile().exists()) {
                    try {
                        Files.createDirectory(bedrockTexturePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!bedrockItemTexturePath.toFile().exists()) {
                    try {
                        Files.createDirectory(bedrockItemTexturePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!bedrockItemTextureJsonPath.exists()) {
                    try {
                        Files.createFile(bedrockItemTextureJsonPath.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!bedrockLangPath.toFile().exists()) {
                    try {
                        Files.createDirectory(bedrockLangPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!bedrockEnUsPath.exists()) {
                    try {
                        Files.createFile(bedrockEnUsPath.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                List<String> langs = new ArrayList<>();
                for (int i = 0; i < Registries.ITEM.stream().count(); i++) {
                    if (!Registries.ITEM.getId(Registries.ITEM.get(i)).getNamespace().equals("minecraft")) {
                        String itemPath = Registries.ITEM.getId(Registries.ITEM.get(i)).getPath();
                        String itemName = snakeToCamel(itemPath);
                        String cleanItemName = itemName.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");

                        langs.add("item." + Registries.ITEM.getId(Registries.ITEM.get(i)).getPath() + ".name=" + cleanItemName);
                    }
                }
                try {
                    Files.write(bedrockEnUsPath.toPath(), langs, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Items
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("resource_pack_name", "BedrockModCompatResources");
                    jsonObject.put("texture_name", "atlas.items");

                    JSONObject newObject = new JSONObject();
                    jsonObject.put("texture_data", newObject);

                    for (int i = 0; i < Registries.ITEM.stream().count(); i++) {
                        if (!Registries.ITEM.getId(Registries.ITEM.get(i)).getNamespace().equals("minecraft")) {
                            JSONObject textureData = new JSONObject();
                            textureData.put("textures", "textures/items/" + Registries.ITEM.getId(Registries.ITEM.get(i)).getPath());
                            newObject.put(Registries.ITEM.getId(Registries.ITEM.get(i)).getPath(), textureData);
                        }
                    }

                    ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(bedrockItemTextureJsonPath.getPath()));
                    stream.writeObject(jsonObject.toString());
                    stream.flush();
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Blocks
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("resource_pack_name", "BedrockModCompatResources");
                    jsonObject.put("texture_name", "atlas.terrain");
                    jsonObject.put("padding", 8);
                    jsonObject.put("num_mip_levels", 4);

                    JSONObject newObject = new JSONObject();
                    jsonObject.put("texture_data", newObject);

                    for (int i = 0; i < Registries.BLOCK.stream().count(); i++) {
                        if (!Registries.BLOCK.getId(Registries.BLOCK.get(i)).getNamespace().equals("minecraft")) {
                            JSONObject textureData = new JSONObject();
                            textureData.put("textures", "textures/blocks/" + Registries.BLOCK.getId(Registries.BLOCK.get(i)).getPath());
                            newObject.put(Registries.BLOCK.getId(Registries.BLOCK.get(i)).getPath(), textureData);
                        }
                    }

                    ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(bedrockBlockTextureJsonPath.getPath()));
                    stream.writeObject(jsonObject.toString());
                    stream.flush();
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 0; i < Registries.BLOCK.stream().count(); i++) {
                        if (!Registries.BLOCK.getId(Registries.BLOCK.get(i)).getNamespace().equals("minecraft")) {
                            JSONObject component = new JSONObject();
                            component.put("textures", Registries.BLOCK.getId(Registries.BLOCK.get(i)).getPath());
                            jsonObject.put(Registries.BLOCK.getId(Registries.BLOCK.get(i)).getNamespace() + ":zzz" + Registries.BLOCK.getId(Registries.BLOCK.get(i)).getPath(), component);
                        }
                    }

                    ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(bedrockBlockJsonPath.getPath()));
                    stream.writeObject(jsonObject.toString());
                    stream.flush();
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Finished generating Bedrock resource pack");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Extract textures
            try {
                Files.walk(Path.of(FabricLoader.getInstance().getGameDir() + "/mods")).forEach((path -> {
                    try {
                        extractModJar(path.toFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Files.walk(bedrockTexturePath).forEach(path -> renameFolder(path.toFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void extractModJar(File file) throws IOException {
        if (file.getName().contains(".jar")) {

        }
    }

    public static void renameFolder(File file){
        if (file.getName() == "item") {
            file.renameTo(new File("items"));
        } else if (file.getName() == "block") {
            file.renameTo(new File("blocks"));
        }
    }

    public static String snakeToCamel (String str){
        str = str.substring(0, 1).toUpperCase()
                + str.substring(1);

        StringBuilder builder
                = new StringBuilder(str);

        for (int i = 0; i < builder.length(); i++) {

            if (builder.charAt(i) == '_') {

                builder.deleteCharAt(i);
                builder.replace(
                        i, i + 1,
                        String.valueOf(
                                Character.toUpperCase(
                                        builder.charAt(i))));
            }
        }

        return builder.toString();
    }
}
