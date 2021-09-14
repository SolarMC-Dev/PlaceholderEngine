import gg.solarmc.placeholder.papibridge.PAPIBridgePlugin;
import org.bukkit.plugin.LaunchablePlugin;

module gg.solarmc.placeholder.papibridge {
    requires gg.solarmc.placeholder;
    requires me.clip.placeholderapi;
    requires org.bukkit;
    requires static org.jetbrains.annotations;
    provides LaunchablePlugin with PAPIBridgePlugin;
}