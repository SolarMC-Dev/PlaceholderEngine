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

/**
 * Another example of a token extrapolator, which extrapolates CharSequence token values
 * from String token values. <br>
 * <br>
 * This one, in particular, is guaranteed to be added to the {@link PlaceholderEngine}
 * and therefore need not be manually added by API users
 *
 */
public final class CharSequenceTokenExtrapolator implements AdditionalTokenExtrapolator {

    @Override
    public <I> void accumulateAdditionalTokens(TokenIdentifier<I> identifier, I value, TokenOutput output) {
        if (identifier.tokenType().equals(String.class)) {
            output.addOutput(CharSequence.class, (String) value);
        }
    }
}
