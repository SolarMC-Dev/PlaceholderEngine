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

package gg.solarmc.placeholder.impl.engine.token;

import gg.solarmc.placeholder.placeholder.TokenIdentifier;

import java.util.Objects;
import java.util.Optional;

record Identifier<I>(Class<I> type, String name) implements TokenIdentifier<I> {

    Identifier {
        Objects.requireNonNull(type, "type");
    }

    @Override
    public Class<I> tokenType() {
        return type;
    }

    @Override
    public Optional<String> tokenName() {
        return Optional.ofNullable(name);
    }
}
