package com.hbmkotlin.modItems

import com.hbmkotlin.modblocks.Blocks
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object Items {
    val BRICK_CONCRETE_ITEM: Item = BlockItem(Blocks.BRICK_CONCRETE, FabricItemSettings())

    fun registerItems() {
        Registry.register(Registries.ITEM, Identifier("hbmkotlin", "brick_concrete"), BRICK_CONCRETE_ITEM)
    }
}