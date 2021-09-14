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

package gg.solarmc.placeholder.placeholder;

import java.util.Optional;

/**
 * An available repository of token values. <br>
 * <br>
 * Can represent one of the parameter values passed to prepared text (see {@link gg.solarmc.placeholder.evaluation}.
 * Can also represents a single argument as an input to the placeholder. <br>
 * <br>
 * Not thread safe. It is intended that placeholder implementations accumulate all of their output
 * in the {@code evaluate} method without switching thread contexts.
 *
 */
public interface TokenInput {

    /**
     * Finds an available token value based on an identifier
     *
     * @param identifier the token identifier
     * @param <I> the token type
     * @return the value if present, or an empty optional if there is no value for such identifier
     */
    <I> Optional<I> findToken(TokenIdentifier<I> identifier);

    /**
     * Finds an available token value based on an identifier
     *
     * @param identifier the token identifier, which is made into a {@code TokenIdentifier} as if by
     *                   {@link TokenFactory#createIdentifier(Class)}
     * @param <I> the token type
     * @return the value if present, or an empty optional if there is no value for such identifier
     */
    <I> Optional<I> findToken(Class<I> identifier);

}
