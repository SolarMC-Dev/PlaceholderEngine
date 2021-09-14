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
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;

record ComposableStyle<I>(Style originalStyle,
                          ComposableText<? extends HoverEvent<?>, I> composableHoverEvent,
                          ComposableText<ClickEvent, I> composableClickEvent,
                          ComposableText<String, I> composableInsertion)
        implements ComposableText<Style, I> {

    ComposableStyle {
        assert originalStyle.hoverEvent() == null;
        assert originalStyle.clickEvent() == null;
        assert originalStyle.insertion() == null;
    }

    @Override
    public Style compose(I input) {
        Style.Builder builder = originalStyle.toBuilder();
        if (composableHoverEvent != null) {
            builder.hoverEvent(composableHoverEvent.compose(input));
        }
        if (composableClickEvent != null) {
            builder.clickEvent(composableClickEvent.compose(input));
        }
        if (composableInsertion != null) {
            builder.insertion(composableInsertion.compose(input));
        }
        return builder.build();
    }
}
