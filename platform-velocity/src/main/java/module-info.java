import com.velocitypowered.api.plugin.LaunchablePlugin;
import gg.solarmc.placeholder.placeholder.AdditionalTokenExtrapolator;
import gg.solarmc.placeholder.platform.velocity.CommandSourceTokenExtrapolator;
import gg.solarmc.placeholder.platform.velocity.InboundConnectionTokenExtrapolator;
import gg.solarmc.placeholder.platform.velocity.PlaceholderEngineVelocity;

module gg.solarmc.placeholder.platform.velocity {
    requires com.velocitypowered.api;
    requires gg.solarmc.placeholder;
    provides AdditionalTokenExtrapolator with
            CommandSourceTokenExtrapolator, InboundConnectionTokenExtrapolator;
    provides LaunchablePlugin with PlaceholderEngineVelocity;
}