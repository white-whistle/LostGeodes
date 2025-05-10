package com.bajookie.lost_geodes.util;

import net.minecraft.util.Identifier;

import static com.bajookie.lost_geodes.EOTE.MOD_ID;

public class ModIdentifier extends Identifier {
    public ModIdentifier(String id) {
        super(MOD_ID, id);
    }

    public static String string(String s) {
        return new ModIdentifier(s).toString();
    }
}
