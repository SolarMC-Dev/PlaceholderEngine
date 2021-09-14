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

package gg.solarmc.placeholder.adventure.test;

import gg.solarmc.placeholder.PlaceholderEngine;
import gg.solarmc.placeholder.PlaceholderEngineFactory;
import gg.solarmc.placeholder.evaluation.PlaceholderEvaluation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComponentTypeExtensionTest {

    private PlaceholderEngine engine;

    @BeforeEach
    public void setEngine() {
        engine = PlaceholderEngineFactory.createNewEngine(getClass().getModule().getLayer());
    }

    @Test
    public void allTextTypes() {
        assertEquals(Set.of(String.class, Component.class), engine.allTextTypes());
    }

    @Test
    public void getEvaluation() {
        assertDoesNotThrow(() -> engine.getEvaluation(Component.class));
    }

    private static String serialize(Component component) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }

    @Test
    public void placeholderlessText() {
        PlaceholderEvaluation<Component> evaluation = engine.getEvaluation(Component.class);
        Component sourceText = Component.text("hello");
        Component evaluated = evaluation.prepareText(Integer.class, sourceText).eval(1);
        assertEquals("hello", serialize(evaluated));
    }

    @Test
    public void stringPlaceholderInsideComponent() {
        PlaceholderEvaluation<Component> evaluation = engine.getEvaluation(Component.class);
        engine.getRegistry().register("placeholder", String.class, new FixedPlaceholder<>("world"));
        Component sourceText = Component.text("hello %placeholder%");
        Component evaluated = evaluation.prepareText(Integer.class, sourceText).eval(1);
        assertEquals("hello world", serialize(evaluated));
    }

    @Test
    public void componentPlaceholderInsideString() {
        PlaceholderEvaluation<String> evaluation = engine.getStringEvaluation();
        engine.getRegistry().register("placeholder", Component.class, 
                new FixedPlaceholder<>(Component.text("world", NamedTextColor.RED)));
        String evaluated = evaluation.prepareText(Integer.class, "hello %placeholder%").eval(1);
        assertEquals("hello world", evaluated);
    }

    @Test
    public void componentPlaceholderInsideComponent() {
        PlaceholderEvaluation<Component> evaluation = engine.getEvaluation(Component.class);
        engine.getRegistry().register("placeholder", Component.class, 
                new FixedPlaceholder<>(Component.text("world", NamedTextColor.RED)));
        Component sourceText = Component.text("hello %placeholder%", NamedTextColor.GREEN);
        Component evaluated = evaluation.prepareText(Integer.class, sourceText).eval(1);
        assertEquals("&ahello &cworld", serialize(evaluated));
    }

}
