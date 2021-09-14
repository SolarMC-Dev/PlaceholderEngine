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

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OffsetCharSequenceTest {

    private void testCharSequence(CharSequence text,
                                                 char... chars) {
        int length = chars.length;
        assertEquals(length, text.length(), "length");
        for (int n = 0; n < length; n++) {
            assertEquals(chars[n], text.charAt(n), "charAt");
        }
        assertEquals(length == 0, text.isEmpty(), "isEmpty");
        assertEquals(new String(chars), text.toString(), "toString");
        for (int startIndex = 0; startIndex < length; startIndex++) {
            for (int endIndex = length; endIndex >= startIndex; endIndex--) {
                assertEquals(
                        new String(Arrays.copyOfRange(chars, startIndex, endIndex)),
                        text.subSequence(startIndex, endIndex).toString(),
                        "subSequence success");
            }
        }
        assertArrayEquals(new String(chars).chars().toArray(), text.chars().toArray(), "chars");
        assertArrayEquals(new String(chars).codePoints().toArray(), text.codePoints().toArray(), "codePoints");
    }

    @Test
    public void emptySequence() {
        testCharSequence(new OffsetCharSequence("", 0));
    }

    @Test
    public void noOffset() {
        testCharSequence(new OffsetCharSequence("abc", 0), 'a', 'b', 'c');
    }

    @Test
    public void oneOffset() {
        testCharSequence(new OffsetCharSequence("abc", 1), 'b', 'c');
    }

    @Test
    public void threeOffset() {
        testCharSequence(new OffsetCharSequence("abcdef", 3), 'd', 'e', 'f');
    }

    @Test
    public void offsetEntireString() {
        testCharSequence(new OffsetCharSequence("abcdef", 6));
    }

}
