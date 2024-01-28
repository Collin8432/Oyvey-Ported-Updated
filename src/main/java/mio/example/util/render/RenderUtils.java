package mio.example.util.render;

import mio.example.Mio;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtils {
  public static void start() {
    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
  }

  public static void end() {
    RenderSystem.disableBlend();
  }
  public static void drawBlock(Vec3d targetPosition, Camera camera) {
    start();
    MinecraftClient mc = MinecraftClient.getInstance();

    MatrixStack matrixStack = new MatrixStack();
    Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = tessellator.getBuffer();
    TextRenderer textRenderer = mc.textRenderer;

    Vec3d transformedPosition = targetPosition.subtract(camera.getPos());

    matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
    matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
    matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);

    Color highlightColor = Mio.colorManager.getColor();
    float red = highlightColor.getRed() / 255f;
    float green = highlightColor.getGreen() / 255f;
    float blue = highlightColor.getBlue() / 255f;
    float alpha = 100 / 255f;

    RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);

    buffer.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

    buffer.vertex(positionMatrix, 0, 1, 0).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 0, 0, 0).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 0, 0).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 1, 0).color(red, green, blue, alpha).next();

    buffer.vertex(positionMatrix, 0, 1, 1).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 0, 0, 1).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 0, 1).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 1, 1).color(red, green, blue, alpha).next();

    buffer.vertex(positionMatrix, 0, 1, 0).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 0, 1, 1).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 1, 1).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 1, 0).color(red, green, blue, alpha).next();

    buffer.vertex(positionMatrix, 0, 0, 0).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 0, 0, 1).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 0, 1).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 0, 0).color(red, green, blue, alpha).next();

    buffer.vertex(positionMatrix, 0, 1, 0).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 0, 0, 0).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 0, 0, 1).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 0, 1, 1).color(red, green, blue, alpha).next();

    buffer.vertex(positionMatrix, 1, 1, 0).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 0, 0).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 0, 1).color(red, green, blue, alpha).next();
    buffer.vertex(positionMatrix, 1, 1, 1).color(red, green, blue, alpha).next();

    RenderSystem.disableCull();
    RenderSystem.depthFunc(GL11.GL_ALWAYS);

    tessellator.draw();

    Vec3d textPosition = targetPosition.add(0, 1, 0);

    RenderSystem.depthFunc(GL11.GL_LEQUAL);

    end();
  }

  public static void draw3DBox(MatrixStack matrixStack, Box box) {
    start();
    Color color = Mio.colorManager.getColor();

    Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = tessellator.getBuffer();

    RenderSystem.setShader(GameRenderer::getPositionProgram);
    RenderSystem.setShaderColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

    buffer.begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION);

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.minZ).next();

    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).next();

    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.maxZ).next();

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.minZ).next();

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.minZ).next();

    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).next();

    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).next();

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).next();

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).next();

    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).next();

    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).next();

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.minZ).next();

    tessellator.draw();

    RenderSystem.setShader(GameRenderer::getPositionProgram);
    RenderSystem.setShaderColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

    buffer.begin(DrawMode.QUADS, VertexFormats.POSITION);

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.maxZ).next();

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).next();

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.minZ).next();

    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).next();

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).next();

    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.minZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.minY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).next();
    buffer.vertex(positionMatrix, (float) box.minX, (float) box.maxY, (float) box.minZ).next();

    tessellator.draw();
    end();
  }
}
