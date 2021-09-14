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

public class ParenScannerTest {

    @Test
    public void emptyBrackets() {
        assertEquals(new ScanResult("", 2),
                new ParenScanner("()").scan());
    }

    @Test
    public void simpleParentheses() {
        assertEquals(new ScanResult("parenthesized", "(parenthesized)".length()),
                new ParenScanner("(parenthesized)").scan());
    }

    @Test
    public void parenthesesWithQuote() {
        String expectedContent = "'text with ( parentheses )'";
        assertEquals(new ScanResult(expectedContent, expectedContent.length() + 2),
                new ParenScanner("('text with ( parentheses )')").scan());
    }

    @Test
    public void noClosingParentheses() {
        assertThrows(MalformattedPlaceholderTextException.class,
                new ParenScanner("(unclosed")::scan);
    }

    @Test
    public void noClosingParenthesesOutsideQuote() {
        assertThrows(MalformattedPlaceholderTextException.class,
                new ParenScanner("('unclosed but contains ) inside quote'")::scan);
    }

    @Test
    public void parenthesesOnly() {
        assertThrows(MalformattedPlaceholderTextException.class, new ParenScanner("(")::scan);
    }
}
