package com.hbmkotlin

import com.hbmkotlin.creativetabs.CreativeTabs
import net.fabricmc.api.ModInitializer

class hbmkotlin : ModInitializer {

    override fun onInitialize() {
        println("Hello from Rt194646!")
        CreativeTabs.register()
        println("Hbm initialized")
        }
    }

