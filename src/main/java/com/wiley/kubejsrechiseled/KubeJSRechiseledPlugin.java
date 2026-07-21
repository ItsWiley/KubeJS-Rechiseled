package com.wiley.kubejsrechiseled;

import com.wiley.kubejsrechiseled.event.RechiseledEvents;
import com.wiley.kubejsrechiseled.event.RechiseledChiselingKubeEvent;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;

public class KubeJSRechiseledPlugin implements KubeJSPlugin {

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(RechiseledEvents.GROUP);
    }

    @Override
    public void generateData(KubeDataGenerator generator) {
        RechiseledEvents.CHISELING.post(ScriptType.SERVER, new RechiseledChiselingKubeEvent(generator));
    }
}
