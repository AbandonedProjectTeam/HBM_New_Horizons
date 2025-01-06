package com.hbmkotlin.modblocks

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object Blocks {
    // Создаем сам блок
    val BRICK_CONCRETE: Block = Block(
        FabricBlockSettings.create()
            .mapColor(MapColor.STONE_GRAY)
            .strength(4.0f)
            .requiresTool()
    )

    // Регистрация блока
    fun registerBlocks() {
        Registry.register(Registries.BLOCK, Identifier("hbmkotlin", "brick_concrete"), BRICK_CONCRETE)
    }
}