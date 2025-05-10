package com.bajookie.lost_geodes.system.Text;

import com.bajookie.lost_geodes.client.tooltip.TooltipComponentSlot;
import com.bajookie.lost_geodes.system.Text.components.ModTextComponent;
import com.bajookie.lost_geodes.system.Text.components.ModTranslatableTextComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.function.Function;

@SuppressWarnings("unused")
public class TextUtil {
    private static final TextArgs EMPTY_ARGS = new TextArgs();

    private static float toFixed1(float v) {
        return Float.parseFloat(String.format("%.1f", v));
    }

    public static MutableText f1(float v) {
        return Math.floor(v) == v ? Text.translatable("number.lost_geodes.int", (int) v) : Text.translatable("number.lost_geodes.f1", toFixed1(v));
    }

    public static MutableText translatable(String key, TextArgs args) {
        return MutableText.of(new ModTranslatableTextComponent(key, null, args));
    }

    public static MutableText translatable(String key) {
        return MutableText.of(new ModTranslatableTextComponent(key, null, EMPTY_ARGS));
    }

    public static MutableText plain(String content) {
        return MutableText.of(new ModTextComponent(content, EMPTY_ARGS));
    }

    public static MutableText plain(String content, TextArgs args) {
        return MutableText.of(new ModTextComponent(content, args));
    }

    public static MutableText concat(MutableText t1, MutableText t2) {
        return translatable("tooltip.lost_geodes.join", new TextArgs().put("1", t1).put("2", t2));
    }

    public static MutableText join(MutableText t1, MutableText t2) {
        return translatable("tooltip.lost_geodes.join_spaced", new TextArgs().put("1", t1).put("2", t2));
    }

    public static MutableText plus(MutableText t1, MutableText t2) {
        return translatable("tooltip.lost_geodes.join_plus", new TextArgs().put("1", t1).put("2", t2));
    }

    public static MutableText joinSmall(MutableText t1, MutableText t2) {
        return translatable("tooltip.lost_geodes.join_spaced_small", new TextArgs().put("1", t1).put("2", t2));
    }

    public static MutableText padded(MutableText t1) {
        return join(TextUtil.translatable("tooltip.lost_geodes.pad"), t1);
    }

    public static MutableText withIcon(Function<MutableText, Text> fIcon, MutableText t1) {
        return joinSmall((MutableText) fIcon.apply(Text.empty()), t1);
    }

    public static MutableText formatTime(int ticks) {
        float seconds = ticks / 20f;
        if (seconds > 60) {
            float minutes = seconds / 60;
            if (minutes > 60) {
                float hours = minutes / 60;
                return TextUtil.translatable("tooltip.lost_geodes.hours", new TextArgs().putF("hours", hours));
            }

            return TextUtil.translatable("tooltip.lost_geodes.minutes", new TextArgs().putF("minutes", minutes));
        }
        return TextUtil.translatable("tooltip.lost_geodes.seconds", new TextArgs().putF("seconds", seconds));
    }

    public static TooltipComponentSlot component(TooltipData data) {
        return new TooltipComponentSlot(data);
    }

}
