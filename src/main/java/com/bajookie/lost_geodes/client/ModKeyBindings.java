package com.bajookie.lost_geodes.client;

import com.bajookie.lost_geodes.item.ability.IHasSlotAbility;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static final KeyBinding HELMET_ABILITY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.lost_geodes.activate_helmet_effect", // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_G, // The keycode of the key
            "category.lost_geodes.artifact_key_bindings" // The translation key of the keybinding's category.
    ));

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (HELMET_ABILITY.wasPressed()) {
                var player = client.player;
                IHasSlotAbility.handleCast(player, EquipmentSlot.HEAD);
            }
        });
    }
}
