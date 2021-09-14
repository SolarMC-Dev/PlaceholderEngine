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

package gg.solarmc.placeholder.impl.engine.parse;

import gg.solarmc.placeholder.evaluation.MalformattedPlaceholderTextException;

record PlaceholderScanner(String text) {

    PlaceholderScanner(CharSequence text) {
        this(text.toString());
    }

    ScanResult scan() {
        assert text.charAt(0) == '%';
        {
            int nextPercent = text.indexOf('%', 1);
            if (nextPercent == -1) {
                throw new MalformattedPlaceholderTextException(
                        "Unable to parse placeholder because no closing \"%\" exists.\n" +
                                "Related text: " + text);
            }
            if (nextPercent == 1) {
                throw new MalformattedPlaceholderTextException(
                        "Unable to parse placeholder because it is empty.\n" +
                                "Related text: " + text);
            }
            if (text.indexOf('(', 1) == -1) {
                return new ScanResult(text.substring(1, nextPercent), 1 + nextPercent);
            }
        }
        StringBuilder content = new StringBuilder();
        int position = 1;
        while (true) {
            if (position >= text.length()) {
                throw new MalformattedPlaceholderTextException(
                        "Unable to parse placeholder text because no closing \"%\" exists.\n" +
                                "Related text: " + text);
            }
            char nextCharacter = text.charAt(position);
            switch (nextCharacter) {
            case '(' -> {
                ScanResult parenthesized = new ParenScanner(new OffsetCharSequence(text, position)).scan();
                int newPosition = position + parenthesized.lengthTakenByContent();
                content.append(text, position, newPosition);
                position = newPosition;
            }
            case '%' -> {
                return new ScanResult(content, 1 + position);
            }
            default -> {
                content.append(nextCharacter);
                position++;
            }
            }
        }
    }
}
