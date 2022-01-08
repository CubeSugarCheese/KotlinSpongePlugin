package org.example

import com.google.inject.Inject
import net.kyori.adventure.identity.Identity
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.LinearComponents
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextDecoration
import org.apache.logging.log4j.Logger
import org.spongepowered.api.Server
import org.spongepowered.api.command.Command
import org.spongepowered.api.command.Command.Parameterized
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.command.parameter.Parameter
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent
import org.spongepowered.api.event.lifecycle.StartingEngineEvent
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent
import org.spongepowered.plugin.PluginContainer
import org.spongepowered.plugin.builtin.jvm.Plugin

/**
 * An example Sponge plugin.
 *
 *
 * All methods are optional -- some common event registrations are included as a jumping-off point.
 */
@Plugin("example")
class Example @Inject internal constructor(private val container: PluginContainer, private val logger: Logger) {
    @Listener
    fun onConstructPlugin(event: ConstructPluginEvent) {
        // Perform any one-time setup
        logger.info("Constructing example plugin")
        logger.debug(event.toString())
    }

    @Listener
    fun onServerStarting(event: StartingEngineEvent<Server>) {
        // Any setup per-game instance. This can run multiple times when
        // using the integrated (singleplayer) server.
        logger.debug(event.toString())
    }

    @Listener
    fun onServerStopping(event: StoppingEngineEvent<Server>) {
        // Any tear down per-game instance. This can run multiple times when
        // using the integrated (singleplayer) server.
        logger.debug(event.toString())
    }

    @Listener
    fun onRegisterCommands(event: RegisterCommandEvent<Parameterized>) {
        // Register a simple command
        // When possible, all commands should be registered within a command register event
        val nameParam = Parameter.string().key("name").build()
        event.register(
            container,
            Command.builder()
                .addParameter(nameParam)
                .permission("example.command.greet")
                .executor { ctx: CommandContext ->
                    val name = ctx.requireOne(nameParam)
                    ctx.sendMessage(
                        Identity.nil(), LinearComponents.linear(
                            NamedTextColor.AQUA,
                            Component.text("Hello "),
                            Component.text(
                                name,
                                Style.style(TextDecoration.BOLD)
                            ),
                            Component.text("!")
                        )
                    )
                    CommandResult.success()
                }
                .build(),
            "greet",
            "wave")
    }
}

