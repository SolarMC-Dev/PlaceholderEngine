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

package gg.solarmc.placeholder.platform.velocity;

import com.velocitypowered.api.plugin.LaunchablePlugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import gg.solarmc.placeholder.PlaceholderEngine;
import gg.solarmc.placeholder.PlaceholderEngineFactory;
import space.arim.omnibus.registry.RegistryPriorities;

import java.nio.file.Path;

public final class PlaceholderEngineVelocity implements LaunchablePlugin {

    @Override
    public void load(ProxyServer server, Path dataFolder, PluginContainer container) {
        ModuleLayer currentLayer = getClass().getModule().getLayer();
        if (currentLayer == null) {
            throw new IllegalStateException("Must run on the module path");
        }
        PlaceholderEngine engine = PlaceholderEngineFactory.createNewEngine(currentLayer);
        server.getOmnibus().getRegistry().register(
                PlaceholderEngine.class, RegistryPriorities.LOWEST, engine, "default Velocity-platform engine");
    }
}
