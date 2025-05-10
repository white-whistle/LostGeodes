package com.bajookie.lost_geodes.entity;

import com.bajookie.lost_geodes.EOTE;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public interface ModDamageTypes {
    RegistryKey<DamageType> DIVINE_ATTACK = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(EOTE.MOD_ID, "divine_attack"));
    RegistryKey<DamageType> ECHO_ATTACK = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(EOTE.MOD_ID, "echo_attack"));

    static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        damageTypeRegisterable.register(DIVINE_ATTACK, new DamageType("player", 0.1f));
        damageTypeRegisterable.register(ECHO_ATTACK,new DamageType("echo",0.1f));
    }
}
