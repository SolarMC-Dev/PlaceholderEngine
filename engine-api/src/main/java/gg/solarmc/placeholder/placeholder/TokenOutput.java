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

/**
 * An accumulator for token values. This might also be called "parameter output." <br>
 * <br>
 * An instance exists at placeholder evaluation time and is passed to
 * {@link Placeholder#evaluate(EvaluationContext, TokenOutput)}. The placeholder
 * uses this object to provide results. These results are available to the next placeholder. <br>
 * <br>
 * Not thread safe. It is intended that placeholder implementations accumulate all of their output
 * in the {@code evaluate} method without switching thread contexts.
 *
 */
public interface TokenOutput {

    /**
     * Adds the given token value to the output. If the calling placeholder is used
     * as an input to another placeholder, the token value will be available to the other placeholder
     * in the {@link TokenInput}. <br>
     * <br>
     * If a token value already exists for the given identifier, the existing token value
     * remains in place and this method call is disregarded.
     *
     * @param identifier the token identifier
     * @param value the value
     * @param <I> the token type
     */
    <I> void addOutput(TokenIdentifier<I> identifier, I value);

    /**
     * Adds the given token value to the output. If the calling placeholder is used
     * as an input to another placeholder, the token value will be available to the other placeholder
     * in the {@link TokenInput} <br>
     * <br>
     * If a token value already exists for the given identifier, the existing token value
     * remains in place and this method call is disregarded.
     *
     * @param identifier the token identifier, which is made into a {@code TokenIdentifier} as if by
     *                   {@link TokenFactory#createIdentifier(Class)}
     * @param value the value
     * @param <I> the token type
     */
    <I> void addOutput(Class<I> identifier, I value);

}
