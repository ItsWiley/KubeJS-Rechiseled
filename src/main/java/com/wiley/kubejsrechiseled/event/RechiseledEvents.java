package com.wiley.kubejsrechiseled.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface RechiseledEvents {
    EventGroup GROUP = EventGroup.of("RechiseledEvents");

    EventHandler CHISELING = GROUP.server("chiseling", () -> RechiseledChiselingKubeEvent.class);
}
