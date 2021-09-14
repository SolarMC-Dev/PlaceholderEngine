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

record QuoteScanner(String text) {

    QuoteScanner(CharSequence text) {
        this(text.toString());
    }

    private static final char QUOTE = '\'';

    ScanResult scan() {
        assert text.charAt(0) == QUOTE;
        StringBuilder content = new StringBuilder();
        int indexToScanFrom = 1;
        while (true) {
            int nextQuote = text.indexOf(QUOTE, indexToScanFrom);
            if (nextQuote == -1) {
                throw new MalformattedPlaceholderTextException(
                        "Unable to parse literal text because no closing \"'\" exists.\n" +
                                "Related text: " + text);
            }
            // Escaped quote using ''
            if (nextQuote != text.length() - 1
                    && text.charAt(nextQuote + 1) == QUOTE) {
                // Add the preceding text
                content.append(text, indexToScanFrom, nextQuote);
                // Add the escaped quote
                content.append(QUOTE);
                indexToScanFrom = nextQuote + 2;
                continue;
            }
            content.append(text, indexToScanFrom, nextQuote);
            return new ScanResult(content, 1 + nextQuote);
        }
    }
}
