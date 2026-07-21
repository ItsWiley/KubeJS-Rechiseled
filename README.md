# KubeJS Rechiseled

**Rechiseled integration for KubeJS.**

KubeJS Rechiseled adds a dedicated scripting event for creating and modifying Rechiseled chiseling sets through KubeJS.

Instead of generating datapack files or manually overriding JSON resources, chiseling sets can be managed directly from readable JavaScript files inside your `kubejs/server_scripts` folder.

## Usage

Register and modify chiseling sets with the `RechiseledEvents.chiseling` event:

```js
RechiseledEvents.chiseling(event => {
    // Chiseling sets go here
})
```

## Adding a Chiseling Set

Use `event.add()` to register a new chiseling set.

```js
RechiseledEvents.chiseling(event => {
    event.add('andesite_set', [
        'minecraft:andesite',
        'minecraft:polished_andesite',
        '#minecraft:stone_tool_materials'
    ])
})
```

The first argument is the identifier for the new chiseling set.

The second argument is an array containing the blocks or tags that should be included in the set.

Both individual block IDs and block tags are supported.

## Overwriting a Chiseling Set

Use `event.overwrite()` to replace the contents of an existing chiseling set.

```js
RechiseledEvents.chiseling(event => {
    event.overwrite('custom_stone_set', [
        'minecraft:stone',
        'minecraft:smooth_stone'
    ])
})
```

This replaces the existing entries in the specified chiseling set with the entries supplied by the script.

## Complete Example

```js
RechiseledEvents.chiseling(event => {
    event.add('andesite_set', [
        'minecraft:andesite',
        'minecraft:polished_andesite',
        '#minecraft:stone_tool_materials'
    ])

    event.overwrite('custom_stone_set', [
        'minecraft:stone',
        'minecraft:smooth_stone'
    ])
})
```

## Dynamic Set Generation

Because chiseling sets are defined through JavaScript, larger collections can be generated using arrays and loops.

```js
const colors = [
    'red',
    'blue',
    'green',
    'yellow'
]

RechiseledEvents.chiseling(event => {
    const entries = [
        'examplemod:metal_panel'
    ]



    colors.forEach(color => {
        entries.push(`examplemod:${color}_metal_panel`)
    })

    event.add('colored_metal_panels', entries)
})
```

This makes it easy to create large chiseling sets without manually listing every block in multiple JSON files.

## Requirements

- [KubeJS](https://www.curseforge.com/minecraft/mc-mods/kubejs)
- [Rechiseled](https://www.curseforge.com/minecraft/mc-mods/rechiseled)

[![bisect](https://www.bisecthosting.com/partners/custom-banners/872e5729-0206-45b2-8fab-ed450bb9967f.webp)](https://github.com/ItsWiley/NirvanaBV/wiki/BisectHosting)