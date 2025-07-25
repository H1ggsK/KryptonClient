package skid.krypton.mixin;

import net.minecraft.client.Mouse;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import skid.krypton.event.events.MouseButtonEvent;
import skid.krypton.event.events.MouseScrolledEvent;
import skid.krypton.event.events.MouseUpdateEvent;
import skid.krypton.manager.EventManager;

@Mixin({Mouse.class})
public abstract class MouseMixin {

    @Shadow
    private double cursorDeltaX;
    @Shadow
    private double cursorDeltaY;

    @Inject(method = {"onMouseButton"}, at = {@At("HEAD")}, cancellable = true)
    private void onMouseButton(final long window, final int button, final int action, final int mods, final CallbackInfo ci) {
        if (button == GLFW.GLFW_KEY_UNKNOWN) return;
        final MouseButtonEvent event = new MouseButtonEvent(button, window, action);
        EventManager.b(event);
        if (event.isCancelled()) ci.cancel();

    }

    @Inject(method = {"onMouseScroll"}, at = {@At("HEAD")}, cancellable = true)
    private void onMouseScroll(final long window, final double horizontal, final double vertical, final CallbackInfo ci) {
        final MouseScrolledEvent event = new MouseScrolledEvent(vertical);
        EventManager.b(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void onTick(CallbackInfo ci)
    {
        MouseUpdateEvent event = new MouseUpdateEvent(cursorDeltaX, cursorDeltaY);
        EventManager.b(event);
        cursorDeltaX = event.getDeltaX();
        cursorDeltaY = event.getDeltaY();
    }
}