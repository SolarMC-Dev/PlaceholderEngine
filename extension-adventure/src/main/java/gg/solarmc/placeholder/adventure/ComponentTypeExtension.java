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
import gg.solarmc.placeholder.text.PrecomposedText;
import gg.solarmc.placeholder.text.StringDecomposer;
import gg.solarmc.placeholder.text.TextTypeExtension;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public final class ComponentTypeExtension implements TextTypeExtension<Component> {

    @Override
    public Class<Component> textType() {
        return Component.class;
    }

    @Override
    public <I> ComposableText<Component, I> prepareText(Component sourceText, StringDecomposer<Component, I> decomposer) {
        /*
        To implement preparation effectively and elegantly, we push down the original parent component
        to become the first child component in the evaluated component.
        An empty component with the same styling as the original parent component is used
        as the new parent component.
        The other child components are prepared recursively. Hover event content, where applicable,
        is also prepared recursively.
         */
        List<ComposableText<Component, I>> composableParts = new ArrayList<>(1 + sourceText.children().size());
        if (sourceText instanceof TextComponent textComponent) {
            composableParts.add(decomposer.decompose(textComponent.content()));
        } else {
            composableParts.add(new PrecomposedText<>(sourceText));
        }
        for (Component child : sourceText.children()) {
            composableParts.add(prepareText(child, decomposer));
        }
        ComposableText<Style, I> composableStyle = prepareStyleEvents(sourceText.style(), decomposer);
        return new ComposableComponent<>(composableStyle, composableParts);
    }

    private <I> ComposableText<Style, I> prepareStyleEvents(Style style, StringDecomposer<Component, I> decomposer) {
        Style strippedStyle = style.hoverEvent(null).clickEvent(null).insertion(null);
        if (strippedStyle.equals(style)) {
            // This style has no events
            return new PrecomposedText<>(style);
        }
        ComposableText<ClickEvent, I> composableClickEvent;
        {
            ClickEvent clickEvent = style.clickEvent();
            if (clickEvent == null) {
                composableClickEvent = null;
            } else {
                ComposableText<String, I> composableValue = decomposer.decomposeToPlainString(clickEvent.value());
                composableClickEvent = new ComposableClickEvent<>(clickEvent.action(), composableValue);
            }
        }
        ComposableText<String, I> composableInsertion;
        {
            String insertion = style.insertion();
            composableInsertion = (insertion == null) ? null : decomposer.decomposeToPlainString(insertion);
        }
        return new ComposableStyle<>(
                strippedStyle,
                prepareHoverEvent(style.hoverEvent(), decomposer),
                composableClickEvent,
                composableInsertion);
    }

    private <V, I> ComposableText<HoverEvent<V>, I> prepareHoverEvent(HoverEvent<V> hoverEvent,
                                                                      StringDecomposer<Component, I> decomposer) {
        if (hoverEvent == null) {
            return null;
        }
        HoverEvent.Action<V> action = hoverEvent.action();
        if (action != HoverEvent.Action.SHOW_TEXT) {
            return new PrecomposedText<>(hoverEvent);
        }
        Component value = (Component) hoverEvent.value();
        @SuppressWarnings("unchecked")
        ComposableText<V, I> composableValue = (ComposableText<V, I>) prepareText(value, decomposer);
        return new ComposableHoverEvent<>(action, composableValue);
    }

    @Override
    public Component combine(Component...parts) {
        return Component.empty().children(List.of(parts));
    }

    @Override
    public Component combineWithPlainStrings(String prefix, Component body, String suffix) {
        return combine(Component.text(prefix), body, Component.text(suffix));
    }

    @Override
    public Component fromPlainString(String string) {
        return Component.text(string);
    }

    @Override
    public String toPlainString(Component text) {
        return PlainTextComponentSerializer.plainText().serialize(text);
    }
}
