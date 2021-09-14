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

package gg.solarmc.placeholder.impl.engine;

import gg.solarmc.placeholder.text.ComposableText;
import gg.solarmc.placeholder.text.TextTypeExtension;

import java.lang.reflect.Array;

record CombinedComposableText<S, I>(TextTypeExtension<S> textTypeExtension,
                                    ComposableText<S, I>[] delegates)
        implements ComposableText<S, I> {

    @Override
    public S compose(I input) {
        @SuppressWarnings("unchecked")
        S[] parts = (S[]) Array.newInstance(textTypeExtension.textType(), delegates.length);
        for (int n = 0; n < delegates.length; n++) {
            parts[n] = delegates[n].compose(input);
        }
        return textTypeExtension.combine(parts);
    }
}
