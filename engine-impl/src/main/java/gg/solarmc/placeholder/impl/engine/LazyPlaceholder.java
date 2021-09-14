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

package gg.solarmc.placeholder.impl.engine;

import gg.solarmc.placeholder.evaluation.MissingPlaceholderException;
import gg.solarmc.placeholder.registry.PlaceholderIdentifier;
import gg.solarmc.placeholder.registry.PlaceholderRegistry;
import gg.solarmc.placeholder.registry.RegisteredPlaceholder;

import java.util.Objects;

record LazyPlaceholder(PlaceholderIdentifier identifier, PlaceholderRegistry placeholderRegistry) {

    LazyPlaceholder {
        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(placeholderRegistry, "placeholderRegistry");
    }

    RegisteredPlaceholder<?> findPlaceholder() {
        return placeholderRegistry.findPlaceholder(identifier)
                .orElseThrow(() -> new MissingPlaceholderException("Placeholder does not exist at " + identifier));
    }
}
