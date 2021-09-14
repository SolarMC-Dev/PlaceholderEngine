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

package gg.solarmc.placeholder.impl.registry;

import gg.solarmc.placeholder.impl.identifier.PlaceholderIdentifierFactory;
import gg.solarmc.placeholder.text.TextTypeExtension;
import gg.solarmc.placeholder.text.TextTypeUnavailableException;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

public record TextTypeRegistry(Map<Class<?>, TextTypeExtension<?>> registeredTypes) {

    public TextTypeRegistry {
        registeredTypes = Map.copyOf(registeredTypes);
    }

    public Set<Class<?>> allTextTypes() {
        return registeredTypes.keySet();
    }

    public <S> TextTypeExtension<S> obtainExtension(Class<S> textType) {
        @SuppressWarnings("unchecked")
        TextTypeExtension<S> textTypeExtension = (TextTypeExtension<S>) registeredTypes.get(textType);
        if (textTypeExtension == null) {
            throw new TextTypeUnavailableException("No registered type exists for " + textType);
        }
        return textTypeExtension;
    }

    public record Creation(ModuleLayer layer, PlaceholderIdentifierFactory identifierCenter) {

        public TextTypeRegistry create() {
            Map<Class<?>, TextTypeExtension<?>> registeredTypes = new HashMap<>();
            for (TextTypeExtension<?> textTypeExtension : ServiceLoader.load(layer, TextTypeExtension.class)) {
                Class<?> textType = textTypeExtension.textType();
                TextTypeExtension<?> previous = registeredTypes.putIfAbsent(textType, textTypeExtension);
                if (previous != null) {
                    var logger = System.getLogger(getClass().getName());
                    logger.log(System.Logger.Level.WARNING,
                            "Duplicate registered text type extension for " + textType);
                }
            }
            return new TextTypeRegistry(registeredTypes);
        }

    }

}
