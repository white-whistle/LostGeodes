package com.bajookie.lost_geodes.system.Capability;

import com.bajookie.lost_geodes.util.ModIdentifier;

public interface IHasCapability {
    String CAPABILITIES_KEY = ModIdentifier.string("capabilities");

    boolean echoesOfTheElders$hasCapabilities();

    Capabilities echoesOfTheElders$getCapabilities();

    default Capabilities echoesOfTheElders$getOrCreateCapabilities() {
        if (!echoesOfTheElders$hasCapabilities()) {
            echoesOfTheElders$setCapabilities(new Capabilities());
        }

        return echoesOfTheElders$getCapabilities();
    }

    void echoesOfTheElders$setCapabilities(Capabilities capabilities);
}
