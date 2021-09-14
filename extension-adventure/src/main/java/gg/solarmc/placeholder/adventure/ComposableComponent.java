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

package gg.solarmc.placeholder.adventure;

import gg.solarmc.placeholder.text.ComposableText;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;

import java.util.ArrayList;
import java.util.List;

record ComposableComponent<I>(ComposableText<Style, I> composableStyle,
                              List<ComposableText<Component, I>> composableParts)
        implements ComposableText<Component, I> {

    @Override
    public Component compose(I input) {
        List<Component> parts = new ArrayList<>(composableParts.size());
        for (ComposableText<Component, I> composablePart : composableParts) {
            parts.add(composablePart.compose(input));
        }
        return Component.text("", composableStyle.compose(input)).children(parts);
    }
}
