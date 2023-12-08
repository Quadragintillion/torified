package xyz.dragin.torified.mixin;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.dragin.torified.Torified;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenOpen {
    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/multiplayer/MultiplayerScreen;updateButtonActivationStates()V"))
    public void multiplayerGuiOpen(CallbackInfo ci) {
        updateToggleButton();
    }

    @Unique
    private void updateToggleButton() {
        MultiplayerScreen ms = (MultiplayerScreen) (Object) this;

        ScreenAccessor si = (ScreenAccessor) ms;
        si.getDrawables().remove(Torified.menuToggleButton);
        si.getSelectables().remove(Torified.menuToggleButton);
        si.getChildren().remove(Torified.menuToggleButton);

        Torified.menuToggleButton = ButtonWidget.builder(Text.literal("Tor: " + (Torified.enabled ? Text.translatable("torified.menu.enabled").getString() : Text.translatable("torified.menu.disabled").getString())), (buttonWidget) -> {
            Torified.enabled = ! Torified.enabled;
            updateToggleButton();
        }).dimensions(ms.width - 125, 5, 120, 20).build();

        si.getDrawables().add(Torified.menuToggleButton);
        si.getSelectables().add(Torified.menuToggleButton);
        si.getChildren().add(Torified.menuToggleButton);
    }
}
