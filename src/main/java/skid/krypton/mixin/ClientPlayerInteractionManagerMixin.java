package skid.krypton.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skid.krypton.event.Event;
import skid.krypton.event.events.AttackBlockEvent;
import skid.krypton.event.events.InteractItemEvent;
import skid.krypton.manager.EventManager;

@Mixin({ClientPlayerInteractionManager.class})
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = {"attackBlock"}, at = {@At("HEAD")})
    private void onAttackBlock(final BlockPos pos, final Direction dir, final CallbackInfoReturnable<Boolean> cir) {
        EventManager.b(new AttackBlockEvent(pos, dir));
    }

    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    private void onInteractItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        InteractItemEvent event = InteractItemEvent.get(hand);
        EventManager.b(event);
        if (event.toReturn != null) {
            cir.setReturnValue(event.toReturn);
        }
    }
}