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

import java.util.stream.IntStream;

record OffsetCharSequence(CharSequence text, int offset)
        implements CharSequence {

    OffsetCharSequence {
        if (offset > text.length()) {
            throw new IllegalArgumentException("Offset must be <= text length");
        }
    }

    @Override
    public int length() {
        return text.length() - offset;
    }

    @Override
    public char charAt(int index) {
        return text.charAt(offset + index);
    }

    @Override
    public boolean isEmpty() {
        return text.length() == offset;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return text.subSequence(start + offset, end + offset);
    }

    @Override
    public String toString() {
        return text.subSequence(offset, text.length()).toString();
    }

    @Override
    public IntStream chars() {
        return text.subSequence(offset, text.length()).chars();
    }

    @Override
    public IntStream codePoints() {
        return text.subSequence(offset, text.length()).codePoints();
    }
}
