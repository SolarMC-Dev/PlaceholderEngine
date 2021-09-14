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

public class PlaceholderScannerTest {

    @Test
    public void emptyPlaceholder() {
        assertThrows(MalformattedPlaceholderTextException.class, new PlaceholderScanner("%%")::scan);
    }

    @Test
    public void simplePlaceholder() {
        assertEquals(new ScanResult("simple_placeholder", "simple_placeholder".length() + 2),
                new PlaceholderScanner("%simple_placeholder%").scan());
    }

    @Test
    public void placeholderWithEmptyParentheses() {
        String expectedContent = "placeholder_with_empty_parens()";
        assertEquals(new ScanResult(expectedContent, expectedContent.length() + 2),
                new PlaceholderScanner('%' +  expectedContent + '%').scan());
    }

    @Test
    public void placeholderWithLiteralArgument() {
        String expectedContent = "placeholder_using_literal('literal')";
        assertEquals(new ScanResult(expectedContent, expectedContent.length() + 2),
                new PlaceholderScanner('%' +  expectedContent + '%').scan());
    }

    @Test
    public void placeholderWithLiteralArgumentContainingParentheses() {
        String expectedContent = "placeholder_with_literal_containing_brackets('literal containing ( and )')";
        assertEquals(new ScanResult(expectedContent, expectedContent.length() + 2),
                new PlaceholderScanner('%' +  expectedContent + '%').scan());
    }

    @Test
    public void placeholderWithLiteralArgumentContainingPercent() {
        String expectedContent = "placeholder_with_literal_containing_percent('literal containing %')";
        assertEquals(new ScanResult(expectedContent, expectedContent.length() + 2),
                new PlaceholderScanner('%' +  expectedContent + '%').scan());
    }

    @Test
    public void placeholderWithPlaceholderArgument() {
        String expectedContent = "placeholder_with_placeholder_argument(%placeholder%)";
        assertEquals(new ScanResult(expectedContent, expectedContent.length() + 2),
                new PlaceholderScanner('%' +  expectedContent + '%').scan());
    }

    @Test
    public void placeholderWithLiteralAndPlaceholderArgument() {
        String expectedContent = "placeholder_with_literal_and_placeholder_argument('literal', %placeholder%)";
        assertEquals(new ScanResult(expectedContent, expectedContent.length() + 2),
                new PlaceholderScanner('%' +  expectedContent + '%').scan());
    }

    @Test
    public void placeholderWithPlaceholderAndLiteralArgument() {
        String expectedContent = "placeholder_with_placeholder_and_literal_argument(%placeholder%, 'literal')";
        assertEquals(new ScanResult(expectedContent, expectedContent.length() + 2),
                new PlaceholderScanner('%' +  expectedContent + '%').scan());
    }

    @Test
    public void placeholderWithPlaceholderAndLiteralArgumentContainingPercent() {
        String expectedContent = "placeholder_with_placeholder_and_literal_argument_containing_percent" +
                "(%placeholder%, 'literal containing %')";
        assertEquals(new ScanResult(expectedContent, expectedContent.length() + 2),
                new PlaceholderScanner('%' +  expectedContent + '%').scan());
    }
}
