package com.hbmkotlin.creativetabs

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object CreativeTabs {
    // Создание ключей для групп предметов
    val HBM_BLOCKS: RegistryKey<ItemGroup> = RegistryKey.of(
        Registries.ITEM_GROUP.key,
        Identifier("hbmkotlin", "hbm_blocks_tab")
    )

    val HBM_MACHINES: RegistryKey<ItemGroup> = RegistryKey.of(
        Registries.ITEM_GROUP.key,
        Identifier("hbmkotlin", "hbm_machines_tab")
    )

    fun register() {
        // Регистрация вкладки HBM_BLOCKS
        Registry.register(
            Registries.ITEM_GROUP,
            HBM_BLOCKS.value, // Используем значение идентификатора
            FabricItemGroup.builder()
                .icon { ItemStack(Items.DIAMOND) } // Иконка вкладки
                .displayName(Text.translatable("itemGroup.hbmkotlin.hbm_blocks_tab")) // Название вкладки
                .entries { _, entries ->
                    // Добавление предметов
                    entries.add(ItemStack(Items.DIAMOND))
                    entries.add(ItemStack(Items.GOLD_INGOT))
                }
                .build()
        )

        // Регистрация вкладки HBM_MACHINES
        Registry.register(
            Registries.ITEM_GROUP,
            HBM_MACHINES.value,
            FabricItemGroup.builder()
                .icon { ItemStack(Items.APPLE) }
                .displayName(Text.translatable("itemGroup.hbmkotlin.hbm_machines_tab"))
                .entries { _, entries ->
                    entries.add(ItemStack(Items.DIRT))
                }
                .build() // Обязательно вызываем build()
        )
    }
}