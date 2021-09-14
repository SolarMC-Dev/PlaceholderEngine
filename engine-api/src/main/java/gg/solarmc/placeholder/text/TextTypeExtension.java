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

package gg.solarmc.placeholder.text;

import java.util.ServiceLoader;

/**
 * Provider for additional text types, instances of which are obtained
 * using the {@link ServiceLoader}. <br>
 * <br>
 * It is a prerequisite for any text type that the type in question be
 * immutable, specifically that the properties of an instance of it cannot
 * be mutated.
 *
 * @param <S> the text type
 */
public interface TextTypeExtension<S> {

    /**
     * Gets the text type itself. This should be a constant
     *
     * @return the text type itself
     */
    Class<S> textType();

    /**
     * Prepares composable text
     *
     * @param <I> the input parameter
     * @param sourceText the source text in which to replace placeholders
     * @param decomposer a string decomposer used to parse strings
     * @return the composable text
     */
    <I> ComposableText<S, I> prepareText(S sourceText, StringDecomposer<S, I> decomposer);

    /**
     * Combines text content with other text, in linear fashion in the same
     * order as the input array
     *
     * @param parts the content to concatenate
     * @return the resulting text
     */
    S combine(S[] parts);

    /**
     * Combines plain string content with text from the type supported by
     * this extension
     *
     * @param prefix the string content to place before the main text
     * @param body the main text
     * @param suffix the string content to place after the main text
     * @return the resulting text
     */
    S combineWithPlainStrings(String prefix, S body, String suffix);

    /**
     * Converts a plain string into texxt from the type supported by this extension
     *
     * @param string the plain string
     * @return the equivalent plain text
     */
    S fromPlainString(String string);

    /**
     * Converts text from the type supported by this extension into
     * a plain string. <br>
     * <br>
     * This method is intended for purposes where a string is necessary to
     * comply with API requests though placeholders may return text in the type
     * supported by this extension. <br>
     * <br>
     * The returned string should not have any special formatting intended to
     * indicate it came from another format.
     *
     * @param text the main text
     * @return the plain string
     */
    String toPlainString(S text);

}
