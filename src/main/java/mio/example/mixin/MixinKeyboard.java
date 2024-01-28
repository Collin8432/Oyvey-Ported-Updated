package mio.example.mixin;

import mio.example.event.impl.KeyEvent;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static mio.example.util.traits.Util.EVENT_BUS;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        KeyEvent event = new KeyEvent(key);
        EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }
}
