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

import gg.solarmc.placeholder.PlaceholderEngine;

import java.util.ServiceLoader;

/**
 * Adds additional tokens when a certain token is added. <br>
 * <br>
 * When a {@link Placeholder} adds a token to the {@link TokenOutput}, available
 * instances of this interface are then used to provide further tokens. This is intended
 * to allow certain tokens to imply other tokens. <br>
 * <br>
 * For example, a subclass token value may imply a superclass token value is also available.
 * See {@link SuperclassTokenExtrapolator} as an implementation of this example. <br>
 * <br>
 * <b>Implementing this interface for types outside your namespace</b> <br>
 * In general, to determine whose responsibility it may be to add a token extrapolator for
 * a type, whichever party is responsible for that type's namespace is also responsible fo
 * adding the token extrapolator. <br>
 * <br>
 * Otherwise, any party may implement the token extrapolator. <br>
 * <br>
 * <b>Discovery of implementations</b> <br>
 * Instances of this interface are loaded via the {@link ServiceLoader}. Additionally,
 * they may be manually added using {@link PlaceholderEngine#addTokenExtrapolator}
 *
 */
public interface AdditionalTokenExtrapolator {

    /**
     * Extracts additional tokens from the given token value and places them in
     * the parameter output.
     *
     * @param identifier the token identifier which was added by the placeholder
     * @param value the token value
     * @param output the token output
     * @param <I> the token type
     */
    <I> void accumulateAdditionalTokens(TokenIdentifier<I> identifier, I value,
                                        TokenOutput output);

}
