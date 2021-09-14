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

package gg.solarmc.placeholder.impl.provider;

import gg.solarmc.placeholder.PlaceholderEngine;
import gg.solarmc.placeholder.PlaceholderEngineFactory;
import gg.solarmc.placeholder.impl.PlaceholderEngineImpl;
import gg.solarmc.placeholder.impl.engine.token.TokenCenter;
import gg.solarmc.placeholder.impl.engine.token.SimpleTokenFactory;
import gg.solarmc.placeholder.impl.identifier.PlaceholderIdentifierFactory;
import gg.solarmc.placeholder.impl.registry.PlaceholderRegistryImpl;
import gg.solarmc.placeholder.impl.registry.TextTypeRegistry;
import gg.solarmc.placeholder.placeholder.TokenFactory;

public final class PlaceholderEngineProvider implements PlaceholderEngineFactory {

    @Override
    public PlaceholderEngine createEngine(ModuleLayer layer) {
        PlaceholderIdentifierFactory identifierFactory = new PlaceholderIdentifierFactory();
        TokenFactory tokenFactory = new SimpleTokenFactory();
        TextTypeRegistry textTypeRegistry = new TextTypeRegistry.Creation(layer, identifierFactory).create();
        return new PlaceholderEngineImpl(
                identifierFactory,
                textTypeRegistry,
                new PlaceholderRegistryImpl(identifierFactory, textTypeRegistry),
                tokenFactory,
                new TokenCenter.Creation(layer).create(tokenFactory));
    }
}
