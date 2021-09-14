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
 * A token representing some information which will be known at evaluation time. <br>
 * <br>
 * In practice, most tokens are unqualified and therefore have no token name. Where
 * there is a token name, {@link #tokenName()} will yield it.
 *
 * @param <I> the token type
 */
public interface TokenIdentifier<I> {

    /**
     * The type of the token
     *
     * @return the token type
     */
    Class<I> tokenType();

    /**
     * The token name, if one has been set
     *
     * @return the token name if present
     */
    Optional<String> tokenName();

}
