package skid.krypton.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import skid.krypton.imixin.ICapabilityTracker;

@Mixin(targets = "com.mojang.blaze3d.platform.GlStateManager$CapabilityTracker")
public abstract class CapabilityTrackerMixin implements ICapabilityTracker {
    @Shadow
    private boolean state;

    @Shadow
    public abstract void setState(boolean state);

    @Override
    public boolean get() {
        return state;
    }

    @Override
    public void set(boolean state) {
        setState(state);
    }
}