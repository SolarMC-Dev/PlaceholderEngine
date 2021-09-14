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

package gg.solarmc.placeholder.impl.identifier;

import gg.solarmc.placeholder.registry.MalformattedPlaceholderIdentifierException;
import gg.solarmc.placeholder.registry.PlaceholderIdentifier;

import java.util.List;

public final class PlaceholderIdentifierFactory {

    private String parseFailure(String identifier) {
        return "Identifier must be alphabetical characters separated by single underscores (e.g., abc_def_xyz). " +
                "Received '" + identifier + "'";
    }

    public PlaceholderIdentifier parse(String identifier) {
        List<CharSequence> parts = List.of(identifier.split("_", -1));
        for (CharSequence part : parts) {
            if (part.isEmpty()) {
                throw new MalformattedPlaceholderIdentifierException(
                        parseFailure(identifier) + " which has leading underscores, trailing underscores, or doubled underscores");
            }
            long alphabeticChars = part.chars().filter(Character::isAlphabetic).count();
            if (alphabeticChars != part.length()) {
                throw new MalformattedPlaceholderIdentifierException(
                        parseFailure(identifier) + " which has non-alphabetical characters");
            }
        }
        return new Identifier(parts);
    }

    public void checkValid(PlaceholderIdentifier identifier) {
        if (!(identifier instanceof Identifier)) {
            throw new IllegalArgumentException(
                    "Foreign implementation of PlaceholderIdentifier: " + identifier.getClass());
        }
    }
}
