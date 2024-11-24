package com.client.ui

import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class CustomScreen :  Screen(Text.translatable("screen.hbmkotlin.custom")) {
    private val backgroundTexture = Identifier("hbmkotlin", "textures/gui/custom_background.png")
    private var animationTick = 0

    override fun init() {
        super.init()

        val closeButton = ButtonWidget.builder(Text.translatable("button.hbmkotlin.close")) {
            closeScreen()
        }.dimensions(
            this.width / 2 - 100,
            this.height - 40,
            200,
            20
        ).build()

        addDrawableChild(closeButton)
    }

    private fun closeScreen() {
        this.client?.setScreen(null)
    }

    override fun tick() {
        animationTick++
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        context.drawTexture(
            backgroundTexture,
            0,
            0,
            0F,
            0F,
            this.width,
            this.height,
            256,
            256
        )
        val color = (animationTick * 10 % 255) shl 16 or 0x00FF00

        val text = Text.of("This menu is not ready yet, you can close it)")
        val textWidth = textRenderer.getWidth(text)

        context.drawText(
            textRenderer,
            text,
            (this.width - textWidth) / 2,
            20,
            color,
            false
        )

        super.render(context, mouseX, mouseY, delta)
    }
}