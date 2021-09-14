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
import gg.solarmc.placeholder.placeholder.TokenOutput;

import java.util.Objects;

public final class NoOpTokenOutput implements TokenOutput {

    public static final TokenOutput INSTANCE = new NoOpTokenOutput();

    private NoOpTokenOutput() {}

    @Override
    public <I> void addOutput(TokenIdentifier<I> identifier, I value) {
        identifier.tokenType().cast(value);
        Objects.requireNonNull(value, "value");
    }

    @Override
    public <I> void addOutput(Class<I> identifier, I value) {
        identifier.cast(value);
        Objects.requireNonNull(value, "value");
    }
}
