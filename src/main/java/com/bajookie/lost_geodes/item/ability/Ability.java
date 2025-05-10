package com.bajookie.lost_geodes.item.ability;

import com.bajookie.lost_geodes.client.ModKeyBindings;
import com.bajookie.lost_geodes.item.IHasCooldown;
import com.bajookie.lost_geodes.item.IHasToggledEffect;
import com.bajookie.lost_geodes.system.Text.ModText;
import com.bajookie.lost_geodes.system.Text.TextArgs;
import com.bajookie.lost_geodes.system.Text.TextUtil;
import com.bajookie.lost_geodes.system.Text.TooltipSection;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

import static com.bajookie.lost_geodes.EOTE.MOD_ID;

public class Ability extends TooltipSection {

    @Override
    public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSection.TooltipSectionContext section) {
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        if (this.hasCooldown()) {
            var msg = IHasCooldown.getCooldownMessage(stack, world);
            if (msg != null) {
                tooltip.add(info(msg));
            }
        }
    }

    public enum AbilityType {
        PASSIVE(ModText.PASSIVE_ABILITY),
        ACTIVE(ModText.ACTIVE_ABILITY),
        ON_HIT(ModText.ON_HIT),
        ON_MINE(ModText.PICK),
        SPECIAL(ModText.SPECIAL_ABILITY);

        final Function<MutableText, Text> textWrapper;

        AbilityType(Function<MutableText, Text> textWrapper) {
            this.textWrapper = textWrapper;
        }
    }

    public enum AbilityTrigger {
        RIGHT_CLICK(simpleIcon(ModText.RIGHT_CLICK)),
        LEFT_CLICK(simpleIcon(ModText.LEFT_CLICK)),
        TOGGLED((stack, world, tooltip, context) -> {
            return IHasToggledEffect.getText(stack);
        }),
        CLICK_STACK((stack, world, tooltip, context) -> TextUtil.translatable("ability.lost_geodes.impl.trigger_over_stack")),
        SNEAK_RIGHT_CLICK((stack, world, tooltip, context) -> {
            return TextUtil.translatable("ability.lost_geodes.impl.trigger", new TextArgs().put("trigger", TextUtil.plus(
                    (MutableText) ModText.KEY.apply(TextUtil.translatable("key.lost_geodes.sneak")),
                    (MutableText) ModText.RIGHT_CLICK.apply(Text.empty())
            )));
        }),
        GEAR((stack, world, tooltip, context) -> {
            return TextUtil.translatable("ability.lost_geodes.impl.trigger", new TextArgs().put("trigger", ModText.KEY.apply((world != null && world.isClient) ? (MutableText) ModKeyBindings.HELMET_ABILITY.getBoundKeyLocalizedText() : Text.empty())));
        });

        final ITooltipMessageGetter messageGetter;

        AbilityTrigger(ITooltipMessageGetter messageGetter) {
            this.messageGetter = messageGetter;
        }

        interface ITooltipMessageGetter {
            MutableText getMessage(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context);
        }

        public static ITooltipMessageGetter simpleIcon(Function<MutableText, Text> icon) {
            return (stack, world, tooltip, context) -> TextUtil.translatable("ability.lost_geodes.impl.trigger", new TextArgs().put("trigger", icon.apply(Text.empty())));
        }
    }

    AbilityType abilityType;
    AbilityTrigger abilityTrigger;
    String abilityName;

    public Ability(String name, AbilityType abilityType, @Nullable AbilityTrigger abilityTrigger) {
        super("ability." + MOD_ID + "." + name);
        this.abilityName = name;
        this.abilityType = abilityType;
        this.abilityTrigger = abilityTrigger;
    }

    public Ability(String name, AbilityType abilityType) {
        this(name, abilityType, null);
    }

    @Override
    public MutableText title(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        var msg = TextUtil.withIcon(this.abilityType.textWrapper, super.title(stack, world, tooltip, context));

        if (this.abilityTrigger != null) {
            return TextUtil.join(
                    msg,
                    abilityTrigger.messageGetter.getMessage(stack, world, tooltip, context)
            );
        }

        return msg;
    }

    public boolean cast(World world, PlayerEntity user, ItemStack itemStack, boolean ignoreCooldown) {
        return false;
    }

    public boolean hasCooldown() {
        return false;
    }
}
