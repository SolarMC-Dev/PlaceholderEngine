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

package gg.solarmc.placeholder.registry;

import java.util.List;

/**
 * An identifier for a placeholder. Identifiers are composed of several parts.
 *
 */
public interface PlaceholderIdentifier {

    /**
     * Gets the leading identifier part. This is simply the first element
     * in {@link #parts()}
     *
     * @return the identifier prefix part
     */
    default CharSequence prefix() {
        return parts().get(0);
    }

    /**
     * An immutable list of identifier parts. This list may never be empty
     *
     * @return the identifier parts
     */
    List<? extends CharSequence> parts();

}
