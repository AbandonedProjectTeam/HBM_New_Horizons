package com.hbmkotlin

import com.hbmkotlin.creativetabs.CreativeTabs
import com.hbmkotlin.modItems.Items
import com.hbmkotlin.modblocks.Blocks
import net.fabricmc.api.ModInitializer

class hbmkotlin : ModInitializer {

    override fun onInitialize() {
        println("Hello from Rt194646!")
        Blocks.registerBlocks()
        Items.registerItems()
        CreativeTabs.register()
        Sounds.registerSounds()
        println("Hbm initialized")

        }
    }

