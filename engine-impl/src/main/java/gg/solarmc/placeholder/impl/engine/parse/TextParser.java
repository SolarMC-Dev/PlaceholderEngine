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

public record TextParser(String text, TextVisitor visitor) {

    public void parse() {
        int position = 0;
        StringBuilder plainTextBuilder = new StringBuilder();
        while (position != text.length()) {
            char character = text.charAt(position);
            if (character == '%') {
                if (position + 1 < text.length() && text.charAt(position + 1) == '%') {
                    // Escaped % symbol. Scan ahead of it next time
                    position += 2;
                    plainTextBuilder.append('%');
                    continue;
                }
                visitor.visitPlainText(plainTextBuilder);
                plainTextBuilder = new StringBuilder();

                ScanResult placeholder = new PlaceholderScanner(new OffsetCharSequence(text, position)).scan();
                visitor.visitPlaceholderText(placeholder.content());
                position += placeholder.lengthTakenByContent();
                continue;
            }
            position++;
            plainTextBuilder.append(character);
        }
        visitor.visitPlainText(plainTextBuilder);
    }
}
