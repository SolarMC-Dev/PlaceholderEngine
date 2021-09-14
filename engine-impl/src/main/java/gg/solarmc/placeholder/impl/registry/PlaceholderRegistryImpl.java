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
import gg.solarmc.placeholder.placeholder.Placeholder;
import gg.solarmc.placeholder.registry.DuplicatePlaceholderException;
import gg.solarmc.placeholder.registry.PlaceholderIdentifier;
import gg.solarmc.placeholder.registry.PlaceholderRegistry;
import gg.solarmc.placeholder.registry.RegisteredPlaceholder;
import gg.solarmc.placeholder.text.TextTypeExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class PlaceholderRegistryImpl implements PlaceholderRegistry {

    private final PlaceholderIdentifierFactory identifierFactory;
    private final TextTypeRegistry textTypeRegistry;

    private final ConcurrentMap<PlaceholderIdentifier, RegisteredPlaceholder<?>> placeholders = new ConcurrentHashMap<>();

    public PlaceholderRegistryImpl(PlaceholderIdentifierFactory identifierFactory, TextTypeRegistry textTypeRegistry) {
        this.identifierFactory = identifierFactory;
        this.textTypeRegistry = textTypeRegistry;
    }

    @Override
    public <S> RegisteredPlaceholder<S> register(String identifier, Class<S> textType, Placeholder<S> placeholder) {
        return register(identifierFactory.parse(identifier), textType, placeholder);
    }

    @Override
    public <S> RegisteredPlaceholder<S> register(PlaceholderIdentifier identifier,
                                                 Class<S> textType, Placeholder<S> placeholder) {
        Objects.requireNonNull(identifier, "identifier");
        identifierFactory.checkValid(identifier);
        RegisteredPlaceholder<S> registeredPlaceholder = new RegisteredPlaceholderImpl<>(
                textTypeRegistry.obtainExtension(textType), identifier, placeholder);
        RegisteredPlaceholder<?> previous = placeholders.putIfAbsent(identifier, registeredPlaceholder);
        if (previous != null) {
            throw new DuplicatePlaceholderException(
                    "Placeholder " + previous + " is already registered at " + identifier);
        }
        return registeredPlaceholder;
    }

    @Override
    public boolean unregister(RegisteredPlaceholder<?> registeredPlaceholder) {
        return placeholders.remove(registeredPlaceholder.identifier(), registeredPlaceholder);
    }

    private record RegisteredPlaceholderImpl<S>(TextTypeExtension<S> textTypeExtension,
                                                PlaceholderIdentifier identifier,
                                                Placeholder<S> placeholder)
            implements RegisteredPlaceholder<S> { }

    @Override
    public Optional<RegisteredPlaceholder<?>> findPlaceholder(PlaceholderIdentifier identifier) {
        return Optional.ofNullable(placeholders.get(identifier));
    }

    @Override
    public Collection<? extends RegisteredPlaceholder<?>> allRegisteredPlaceholders() {
        return Collections.unmodifiableCollection(placeholders.values());
    }
}
