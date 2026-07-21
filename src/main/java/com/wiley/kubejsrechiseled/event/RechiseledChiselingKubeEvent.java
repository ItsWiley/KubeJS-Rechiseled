package com.wiley.kubejsrechiseled.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.IngredientWrapper;
import dev.latvian.mods.kubejs.util.ID;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.rhino.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class RechiseledChiselingKubeEvent implements KubeEvent {
    private static final String CHISELING_RECIPE_PATH = "chiseling_recipes/";

    private final KubeDataGenerator generator;

    public RechiseledChiselingKubeEvent(KubeDataGenerator generator) {
        this.generator = generator;
    }

    public RechiseledChiselingRecipeBuilder add(Context cx, Object id, Object entries) {
        return recipe(cx, id, entries);
    }

    public RechiseledChiselingRecipeBuilder recipe(Context cx, Object id, Object entries) {
        var recipeId = ID.kjs(id);
        var json = createRecipeJson(false);
        var builder = new RechiseledChiselingRecipeBuilder(cx, generator, recipeId, json);
        builder.entries(entries);
        write(recipeId, json);
        return builder;
    }

    public RechiseledChiselingRecipeBuilder overwrite(Context cx, Object id, Object entries) {
        var recipeId = ID.kjs(id);
        var json = createRecipeJson(true);
        var builder = new RechiseledChiselingRecipeBuilder(cx, generator, recipeId, json);
        builder.entries(entries);
        write(recipeId, json);
        return builder;
    }

    private void write(ResourceLocation recipeId, JsonObject json) {
        generator.json(recipeId.withPath(CHISELING_RECIPE_PATH + recipeId.getPath()), json);
    }

    private static JsonObject createRecipeJson(boolean overwrite) {
        var json = new JsonObject();
        json.addProperty("type", "rechiseled:chiseling");
        json.addProperty("overwrite", overwrite);
        json.add("entries", new JsonArray());
        return json;
    }

    public static class RechiseledChiselingRecipeBuilder {
        private final Context cx;
        private final KubeDataGenerator generator;
        private final ResourceLocation id;
        private final JsonObject json;
        private final JsonArray entries;

        private RechiseledChiselingRecipeBuilder(Context cx, KubeDataGenerator generator, ResourceLocation id, JsonObject json) {
            this.cx = cx;
            this.generator = generator;
            this.id = id;
            this.json = json;
            this.entries = json.getAsJsonArray("entries");
        }

        public RechiseledChiselingRecipeBuilder entry(Object entry) {
            entries.add(encodeIngredient(IngredientWrapper.wrap(cx, entry)));
            return this;
        }

        public RechiseledChiselingRecipeBuilder entries(Object entries) {
            var list = ListJS.of(entries);

            if (list == null) {
                return entry(entries);
            }

            for (var entry : list) {
                entry(entry);
            }

            return this;
        }

        public RechiseledChiselingRecipeBuilder overwrite(boolean overwrite) {
            json.addProperty("overwrite", overwrite);
            return this;
        }

        public RechiseledChiselingRecipeBuilder clear() {
            while (!entries.isEmpty()) {
                entries.remove(0);
            }

            return this;
        }

        public ResourceLocation id() {
            return id;
        }

        public JsonObject json() {
            return json;
        }

        private JsonElement encodeIngredient(Ingredient ingredient) {
            return Ingredient.CODEC.encodeStart(generator.getRegistries().json(), ingredient)
                .getOrThrow(message -> new IllegalArgumentException("Failed to encode Rechiseled chiseling entry for " + id + ": " + message));
        }
    }
}
