import gg.solarmc.placeholder.placeholder.AdditionalTokenExtrapolator;
import gg.solarmc.placeholder.platform.bukkit.EntityTokenExtrapolator;
import gg.solarmc.placeholder.platform.bukkit.LivingEntityTokenExtrapolator;
import gg.solarmc.placeholder.platform.bukkit.OfflinePlayerTokenExtrapolator;
import gg.solarmc.placeholder.platform.bukkit.PlaceholderEngineBukkit;
import org.bukkit.plugin.LaunchablePlugin;

module gg.solarmc.placeholder.platform.bukkit {
    requires gg.solarmc.placeholder;
    requires org.bukkit;
    provides AdditionalTokenExtrapolator with EntityTokenExtrapolator,
            LivingEntityTokenExtrapolator, OfflinePlayerTokenExtrapolator;
    provides LaunchablePlugin with PlaceholderEngineBukkit;
}