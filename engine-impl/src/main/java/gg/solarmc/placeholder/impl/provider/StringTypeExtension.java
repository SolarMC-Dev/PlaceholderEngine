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

package gg.solarmc.placeholder.impl.provider;

import gg.solarmc.placeholder.text.ComposableText;
import gg.solarmc.placeholder.text.StringDecomposer;
import gg.solarmc.placeholder.text.TextTypeExtension;

public final class StringTypeExtension implements TextTypeExtension<String> {

    @Override
    public Class<String> textType() {
        return String.class;
    }

    @Override
    public <I> ComposableText<String, I> prepareText(String sourceText, StringDecomposer<String, I> decomposer) {
        return decomposer.decompose(sourceText);
    }

    @Override
    public String combine(String[] parts) {
        int sumLength = 0;
        for (String part : parts) {
            sumLength += part.length();
        }
        StringBuilder builder = new StringBuilder(sumLength);
        for (String part : parts) {
            builder.append(part);
        }
        return builder.toString();
    }

    @Override
    public String combineWithPlainStrings(String prefix, String body, String suffix) {
        return prefix + body + suffix;
    }

    @Override
    public String fromPlainString(String string) {
        return string;
    }

    @Override
    public String toPlainString(String text) {
        return text;
    }
}
