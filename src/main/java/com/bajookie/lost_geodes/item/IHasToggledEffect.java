package com.bajookie.lost_geodes.item;

import com.bajookie.lost_geodes.screen.client.particles.ScreenParticleManager;
import com.bajookie.lost_geodes.screen.client.particles.imps.EffectParticle;
import com.bajookie.lost_geodes.screen.client.particles.imps.LightningParticle;
import com.bajookie.lost_geodes.system.Text.TextArgs;
import com.bajookie.lost_geodes.system.Text.TextUtil;
import com.bajookie.lost_geodes.util.Interator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;

import static com.bajookie.lost_geodes.system.ItemStack.CustomItemNbt.EFFECT_ENABLED;

public interface IHasToggledEffect extends IEmptyClick {

    @Override
    default boolean onEmptyClick(PlayerEntity user, ItemStack self, StackReference cursor) {
        EFFECT_ENABLED.update(self, enabled -> {

            if (user.getWorld().isClient) {
                Interator.of(10).forEach(index -> {

                    var particle = enabled ?
                            new EffectParticle(ScreenParticleManager.getMousePos()).randomizeVelocity(3) :
                            new LightningParticle(ScreenParticleManager.getMousePos()).randomizeVelocity(7);


                    ScreenParticleManager.addParticle(particle);
                });

                user.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, enabled ? 0.5f : 0.8f);
            }

            return !enabled;
        });

        return true;
    }

    static MutableText getText(ItemStack itemStack) {
        var enabled = EFFECT_ENABLED.get(itemStack);
        return TextUtil.translatable("tooltip.lost_geodes.click_to_toggle", new TextArgs().put("state", enabled ? TextUtil.translatable("tooltip.lost_geodes.switch_on") : TextUtil.translatable("tooltip.lost_geodes.switch_off")));
    }
}
