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
 * Factory for obtaining token identifiers. See {@link TokenIdentifier}
 *
 */
public interface TokenFactory {

    /**
     * Creates a token identifier from a simple type. This is the most common of
     * token identifiers.
     *
     * @param type the token type
     * @param <I> the type
     * @return an unqualified token identifier
     */
    <I> TokenIdentifier<I> createIdentifier(Class<I> type);

    /**
     * Creates a token identifier from a type and a name.
     *
     * @param type the token type
     * @param name the name
     * @param <I> the type
     * @return a token identifier with a name
     */
    <I> TokenIdentifier<I> createIdentifierWithName(Class<I> type, String name);


}
