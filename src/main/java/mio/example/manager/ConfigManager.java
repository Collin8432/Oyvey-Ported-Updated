package mio.example.manager;

import com.google.gson.*;
import mio.example.Mio;
import mio.example.features.Feature;
import mio.example.features.settings.Bind;
import mio.example.features.settings.EnumConverter;
import mio.example.features.settings.Setting;
import mio.example.util.traits.Jsonable;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigManager {
    private static final Path Mio_PATH = FabricLoader.getInstance().getGameDir().resolve("Mio");
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create();
    private final List<Jsonable> jsonables = List.of(Mio.friendManager, Mio.moduleManager, Mio.commandManager);

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void setValueFromJson(Feature feature, Setting setting, JsonElement element) {
        String str;
        switch (setting.getType()) {
            case "Boolean" -> {
                setting.setValue(element.getAsBoolean());
            }
            case "Double" -> {
                setting.setValue(element.getAsDouble());
            }
            case "Float" -> {
                setting.setValue(element.getAsFloat());
            }
            case "Integer" -> {
                setting.setValue(element.getAsInt());
            }
            case "String" -> {
                str = element.getAsString();
                setting.setValue(str.replace("_", " "));
            }
            case "Bind" -> {
                setting.setValue(new Bind(element.getAsInt()));
            }
            case "Enum" -> {
                try {
                    EnumConverter converter = new EnumConverter(((Enum) setting.getValue()).getClass());
                    Enum value = converter.doBackward(element);
                    setting.setValue((value == null) ? setting.getDefaultValue() : value);
                } catch (Exception exception) {
                }
            }
            default -> {
                Mio.LOGGER.error("Unknown Setting type for: " + feature.getName() + " : " + setting.getName());
            }
        }
    }

    public void load() {
        if (!Mio_PATH.toFile().exists()) Mio_PATH.toFile().mkdirs();
        for (Jsonable jsonable : jsonables) {
            try {
                String read = Files.readString(Mio_PATH.resolve(jsonable.getFileName()));
                jsonable.fromJson(JsonParser.parseString(read));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        if (!Mio_PATH.toFile().exists()) Mio_PATH.toFile().mkdirs();
        for (Jsonable jsonable : jsonables) {
            try {
                JsonElement json = jsonable.toJson();
                Files.writeString(Mio_PATH.resolve(jsonable.getFileName()), gson.toJson(json));
            } catch (Throwable e) {
            }
        }
    }
}
