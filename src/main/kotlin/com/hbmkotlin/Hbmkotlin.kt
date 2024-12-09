package com.hbmkotlin

import com.hbmkotlin.creativetabs.CreativeTabs
import com.hbmkotlin.Sounds
import net.fabricmc.api.ModInitializer

class hbmkotlin : ModInitializer {

    override fun onInitialize() {
        println("Hello from Rt194646!")
        CreativeTabs.register()
        Sounds.registerSounds()
        println("Hbm initialized")
        }
    }

