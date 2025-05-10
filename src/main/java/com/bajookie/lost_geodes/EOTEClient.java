package com.bajookie.lost_geodes;

import com.bajookie.lost_geodes.block.ModBlocks;
import com.bajookie.lost_geodes.client.*;
import com.bajookie.lost_geodes.client.render.ModArmor;
import com.bajookie.lost_geodes.client.render.ModShaderTypes;
import com.bajookie.lost_geodes.client.render.ModShaders;
import com.bajookie.lost_geodes.client.tooltip.ItemTooltipComponent;
import com.bajookie.lost_geodes.client.tooltip.ItemTooltipData;
import com.bajookie.lost_geodes.entity.client.ModEntityRenderers;
import com.bajookie.lost_geodes.entity.client.ModModelLayers;
import com.bajookie.lost_geodes.events.PlayerAttackHandler;
import com.bajookie.lost_geodes.particles.*;
import com.bajookie.lost_geodes.screen.client.particles.ScreenParticleManager;
import com.bajookie.lost_geodes.util.ModModelPredicateProvider;
import com.bajookie.lost_geodes.util.sided.ShiftingUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.gui.screen.Screen;

public class EOTEClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ShiftingUtil.IMPL = new ShiftingUtil() {
            @Override
            public boolean get() {
                return Screen.hasShiftDown();
            }
        };

        ModShaderTypes.init();
        ModShaders.init();
        ModArmor.init();

        ScreenParticleManager.INSTANCE.init();

        ModModelLayers.registerModMobLayers();
        ModEntityRenderers.init();
        ModBlocks.registerModBlocksModelLayers();

        ModModelPredicateProvider.registerModModels();

        ParticleFactoryRegistry.getInstance().register(ModParticles.SECOND_SUN_PARTICLE, SecondSunParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.STARFALL_TRAIL_PARTICLE, StarfallTrailParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.STARFALL_STAR_PARTICLE, StarParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ELECTRIC_SHOCK, ElectricShockParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.LIGHTNING_PARTICLE, LightningParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGMA_BULLET_SPEED, MagmaBulletParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.GALE_CRIT, GaleCritParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.EARTH_SPIKE_PARTICLE, EarthSpikeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SNOW_FLAKE_PARTICLE, SnowFlakeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.THICK_TRAIL_PARTICLE, ThickTrailParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.LINE_PARTICLE_TYPE, LineParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ZAP_PARTICLE_TYPE, ZapParticle.Factory::new);
        HudRenderCallback.EVENT.register(new StopwatchOverlay());
        HudRenderCallback.EVENT.register(new TvArrowOverlay());
        HudRenderCallback.EVENT.register(new EquipmentCooldownOverlay());

        TooltipComponentCallback.EVENT.register((data) -> {
            if (data instanceof ItemTooltipData itemTooltipData) {
                return new ItemTooltipComponent(itemTooltipData);
            }

            return null;
        });

        CustomItemColors.init();
        ClientNetworking.init();
        AttackEntityCallback.EVENT.register(new PlayerAttackHandler());

        ModKeyBindings.init();
    }
}
