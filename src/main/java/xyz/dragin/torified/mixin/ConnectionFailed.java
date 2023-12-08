package xyz.dragin.torified.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.dragin.torified.Torified;

@Mixin(DisconnectedScreen.class)
public class ConnectionFailed {
    @Shadow @Final private Text reason;
    @Shadow @Final private Screen parent;

    @Inject(method = "init()V", at = @At("HEAD"), cancellable = true)
    public void disconnectScreenOpen(CallbackInfo ci) {
        if (!Torified.enabled) return;
        if (reason.getString().equals("Connection refused: no further information")) {
            ci.cancel();
            MinecraftClient.getInstance().setScreen(new DisconnectedScreen(parent, Text.translatable("connect.failed"), Text.translatable("torified.connect.failed", reason.getString())));
        }
    }
}
