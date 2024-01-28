package mio.example.font;

import mio.example.Mio;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontRenderers {
  public static FontAdapter main;
  public static FontAdapter mainBig;
  public static FontAdapter secondary;

  public static @NotNull RendererFontAdapter createDefault(float size, String name) throws IOException, FontFormatException {
    return new RendererFontAdapter(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(
        Mio.class.getClassLoader().getResourceAsStream("assets/mio/fonts/" + name + ".ttf"))).deriveFont(Font.PLAIN, size / 2f), size / 2f);
  }
}