package mio.example.features.gui.items.buttons;

import mio.example.Mio;
import mio.example.features.gui.Component;
import mio.example.features.gui.MioGui;
import mio.example.features.gui.items.Item;
import mio.example.features.modules.client.ClickGui;
import mio.example.util.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

public class Button
        extends Item {
    private boolean state;

    public Button(String name) {
        super(name);
        this.height = 15;
    }

    @Override
    public void drawScreen(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        RenderUtil.rect(context.getMatrices(), this.x, this.y, this.x + (float) this.width, this.y + (float) this.height - 0.5f, this.getState() ? (!this.isHovering(mouseX, mouseY) ? Mio.colorManager.getColorWithAlpha(Mio.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue()) : Mio.colorManager.getColorWithAlpha(Mio.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue())) : (!this.isHovering(mouseX, mouseY) ? 0x11555555 : -2007673515));
        drawString(this.getName(), this.x + 2.3f, this.y - 2.0f - (float) MioGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.onMouseClick();
        }
    }

    public void onMouseClick() {
        this.state = !this.state;
        this.toggle();
        mc.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1f));
    }

    public void toggle() {
    }

    public boolean getState() {
        return this.state;
    }

    @Override
    public int getHeight() {
        return 14;
    }

    public boolean isHovering(int mouseX, int mouseY) {
        for (Component component : MioGui.getClickGui().getComponents()) {
            if (!component.drag) continue;
            return false;
        }
        return (float) mouseX >= this.getX() && (float) mouseX <= this.getX() + (float) this.getWidth() && (float) mouseY >= this.getY() && (float) mouseY <= this.getY() + (float) this.height;
    }
}