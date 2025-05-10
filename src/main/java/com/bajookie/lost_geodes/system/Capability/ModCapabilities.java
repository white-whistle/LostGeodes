package com.bajookie.lost_geodes.system.Capability;

import com.bajookie.lost_geodes.system.Raid.networking.s2c.SyncSingleCapability;
import com.bajookie.lost_geodes.system.screen_switch.ScreenSwitchCapability;
import com.bajookie.lost_geodes.util.ModIdentifier;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ModCapabilities {
    public static Map<String, Function<Object, Capability<?>>> lookup = new HashMap<>();

    public static CapabilityWrapper<LivingEntity, ScreenSwitchCapability> SCREEN_SWITCH_OBJECTIVE = registerCapability("tv_arrow_objective", ScreenSwitchCapability::new);
    public static CapabilityWrapper<PlayerEntity, PersistentCooldownCapability> PERSISTENT_COOLDOWN = registerCapability("persistent_cooldown", PersistentCooldownCapability::new);


    public static <H, T extends Capability<H>> CapabilityWrapper<H, T> registerCapability(String name, Function<H, T> factory) {
        var namespaceName = ModIdentifier.string(name);

        // noinspection unchecked
        lookup.put(namespaceName, (Function<Object, Capability<?>>) factory);

        return new CapabilityWrapper<>(namespaceName);
    }

    public static class CapabilityWrapper<H, T extends Capability<H>> {
        public String name;

        CapabilityWrapper(String name) {
            this.name = name;
        }

        /**
         * @param o  potential capability holder
         * @param fn capability consumer
         * @return true if the object has this capability
         */
        public boolean use(H o, Consumer<T> fn) {
            if (o instanceof IHasCapability iHasCapability) {
                var capabilities = iHasCapability.echoesOfTheElders$getCapabilities();
                if (capabilities == null) return false;

                var capability = capabilities.get(this.name);
                if (capability == null) return false;

                // noinspection unchecked
                fn.accept((T) capability);
                return true;
            }
            return false;
        }

        public <TRet> Optional<TRet> use(H o, Function<T, Optional<TRet>> fn) {
            if (o instanceof IHasCapability iHasCapability) {
                var capabilities = iHasCapability.echoesOfTheElders$getCapabilities();
                if (capabilities == null) return Optional.empty();

                var capability = capabilities.get(this.name);
                if (capability == null) return Optional.empty();

                // noinspection unchecked
                return fn.apply((T) capability);
            }
            return Optional.empty();
        }

        public void attach(H o, @Nullable Consumer<T> init) {
            if (o instanceof IHasCapability iHasCapability) {
                var capabilities = iHasCapability.echoesOfTheElders$getOrCreateCapabilities();

                var capabilityFactory = ModCapabilities.lookup.get(this.name);
                if (capabilityFactory == null) return;

                var capability = capabilities.get(this.name);
                if (capability == null) {
                    capability = capabilityFactory.apply(o);
                    capabilities.put(this.name, capability);
                }
                if (init != null) {
                    // noinspection unchecked
                    init.accept((T) capability);
                }

            }
        }

        public boolean hasCapability(H o) {
            if (o instanceof IHasCapability iHasCapability) {
                var capabilities = iHasCapability.echoesOfTheElders$getCapabilities();
                if (capabilities == null) return false;

                var capability = capabilities.get(this.name);
                return capability != null;
            }
            return false;
        }

        public void attach(H o) {
            this.attach(o, null);
        }

        public void syncEntityCapability(H o) {
            if (o instanceof IHasCapability iHasCapability && o instanceof LivingEntity livingEntity) {
                if (livingEntity.getWorld().isClient) return;

                var capabilities = iHasCapability.echoesOfTheElders$getCapabilities();
                if (capabilities == null) return;

                var capability = capabilities.get(this.name);
                if (capability == null) return;

                PlayerLookup.tracking(livingEntity).forEach(p -> SyncSingleCapability.send(p, livingEntity, this.name));
            }
        }

        @Nullable
        public T tryGetCapability(@Nullable H o) {
            if (!(o instanceof IHasCapability iHasCapability)) return null;

            var capabilities = iHasCapability.echoesOfTheElders$getCapabilities();
            if (capabilities == null) return null;

            var capability = capabilities.get(this.name);
            if (capability == null) return null;

            // noinspection unchecked
            return (T) capability;
        }
    }
}
