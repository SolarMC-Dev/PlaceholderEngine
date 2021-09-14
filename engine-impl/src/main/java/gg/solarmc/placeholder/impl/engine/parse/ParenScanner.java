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

record ParenScanner(CharSequence text) {

    ScanResult scan() {
        assert text.charAt(0) == '(';
        StringBuilder content = new StringBuilder();
        int position = 1;
        while (true) {
            if (position >= text.length()) {
                throw new MalformattedPlaceholderTextException(
                        "Unable to parse parenthesized text because no closing \")\" exists.\n" +
                                "Related text: " + text);
            }
            char nextCharacter = text.charAt(position);
            switch (nextCharacter) {
                case '\'' -> {
                    ScanResult quote = new QuoteScanner(new OffsetCharSequence(text, position)).scan();
                    int newPosition = position + quote.lengthTakenByContent();
                    content.append(text, position, newPosition);
                    position = newPosition;
                }
                case ')' -> {
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
