/*
 * PlaceholderEngine
 * Copyright © 2021 SolarMC Developers
 *
 * PlaceholderEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlaceholderEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with PlaceholderEngine. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU Lesser General Public License.
 */

package gg.solarmc.placeholder.papibridge;

import gg.solarmc.placeholder.PlaceholderEngine;
import gg.solarmc.placeholder.registry.RegisteredPlaceholder;
import org.bukkit.plugin.LaunchablePlugin;
import org.bukkit.plugin.Plugin;
import space.arim.omnibus.Omnibus;

import java.nio.file.Path;

public final class PAPIBridgePlugin implements LaunchablePlugin {

    private Omnibus omnibus;
    private RegisteredPlaceholder<?> papiToEnginePlaceholder;
    private EngineToPAPIPlaceholder engineToPapiPlaceholder;

    @Override
    public void onLaunch(Plugin plugin, Path dataFolder) {
        omnibus = plugin.getServer().getOmnibus();
    }

    private PlaceholderEngine getPlaceholderEngine() {
        return omnibus.getRegistry().getProvider(PlaceholderEngine.class)
                .orElseThrow(() -> new IllegalStateException("PlaceholderEngine not provided for"));
    }

    @Override
    public void onEnable() {
        PlaceholderEngine placeholderEngine = getPlaceholderEngine();
        papiToEnginePlaceholder = placeholderEngine.getRegistry().register(
                "papi", String.class, new PAPIToEnginePlaceholder());

        engineToPapiPlaceholder = new EngineToPAPIPlaceholder(placeholderEngine);
        engineToPapiPlaceholder.register();
    }

    @Override
    public void close() {
        getPlaceholderEngine().getRegistry().unregister(papiToEnginePlaceholder);

        if (engineToPapiPlaceholder.getPlaceholderAPI() != null) {
            engineToPapiPlaceholder.unregister();
        }
    }
}
