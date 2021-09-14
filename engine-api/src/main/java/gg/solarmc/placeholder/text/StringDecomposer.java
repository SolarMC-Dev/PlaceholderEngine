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

/**
 * A decomposer for strings. The usage of this interface enables
 * the main placeholder engine implementation to handle parsing strings,
 * so that text extensions do not need to concern themselves with parsing.
 *
 * @param <S> the text type
 * @param <I> the input parameter type
 */
public interface StringDecomposer<S, I> {

    /**
     * Decomposes text containing placeholders into composable text so that
     * it may be evaluated at a later time
     *
     * @param text the text to decompose
     * @return the composable text
     */
    ComposableText<S, I> decompose(String text);

    /**
     * Decomposes text containing placeholders into composable text so that
     * it may be evaluated at a later time. <br>
     * <br>
     * Unlike {@link #decompose(String)}, this method will only use string-based
     * placeholders for the purpose of matching placeholders in the string.
     *
     * @param text the text to decompose
     * @return the composable string
     */
    ComposableText<String, I> decomposeToPlainString(String text);

}
