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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuoteScannerTest {

    @Test
    public void emptyQuote() {
        assertEquals(new ScanResult("", 2),
                new QuoteScanner("''").scan());
    }

    @Test
    public void simpleQuote() {
        assertEquals(new ScanResult("quoted", 8),
                new QuoteScanner("'quoted'").scan());
    }

    @Test
    public void quoteWithEscapedQuoteCharacter() {
        String expectedContent = "quote with ' mark";
        assertEquals(new ScanResult(expectedContent, expectedContent.length() + 1 + 2),
                new QuoteScanner("'quote with '' mark'").scan());
    }

    @Test
    public void noClosingQuote() {
        assertThrows(MalformattedPlaceholderTextException.class,
                new QuoteScanner("'unclosed")::scan);
    }

    @Test
    public void quoteOnly() {
        assertThrows(MalformattedPlaceholderTextException.class, new QuoteScanner("'")::scan);
    }
}
