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

package gg.solarmc.placeholder.registry;

import gg.solarmc.placeholder.PlaceholderEngine;
import gg.solarmc.placeholder.placeholder.Placeholder;

import java.util.Collection;
import java.util.Optional;

/**
 * The center for registering all placeholders, including those using different text types
 *
 */
public interface PlaceholderRegistry {

    /**
     * Registers a placeholder at the given identifier. <br>
     * <br>
     * This is a helper method for {@link #register(PlaceholderIdentifier, Class, Placeholder)}
     * by using {@link PlaceholderEngine#parseIdentifier(String)}.
     *
     * @param <S> the text type of the placeholder
     * @param identifier the placeholder identifier, which is parsed
     * @param textType the text type of the placeholder
     * @param placeholder the placeholder
     * @return a placeholder which may be unregistered
     * @throws MalformattedPlaceholderIdentifierException if the identifier cannot be parsed
     * @throws DuplicatePlaceholderException if there is already a placeholder registered at the identifier
     */
    <S> RegisteredPlaceholder<S> register(String identifier, Class<S> textType, Placeholder<S> placeholder);

    /**
     * Registers a placeholder at the given identifier
     *
     * @param <S> the text type of the placeholder
     * @param identifier the placeholder identifier
     * @param textType the text type of the placeholder
     * @param placeholder the placeholder
     * @return a placeholder which may be unregistered
     * @throws DuplicatePlaceholderException if there is already a placeholder registered at the identifier
     */
    <S> RegisteredPlaceholder<S> register(PlaceholderIdentifier identifier, Class<S> textType, Placeholder<S> placeholder);

    /**
     * Unregisters a placeholder
     *
     * @param registeredPlaceholder the registered placeholder
     * @return true if unregistered, false if not registered
     */
    boolean unregister(RegisteredPlaceholder<?> registeredPlaceholder);

    /**
     * Finds a placeholder registered at the given identifier
     *
     * @param identifier the placeholder identifier
     * @return the placeholder, or an empty optional if there is none
     */
    Optional<RegisteredPlaceholder<?>> findPlaceholder(PlaceholderIdentifier identifier);

    /**
     * Gets all registered placeholders
     *
     * @return an unmodifiable collection of all registered placeholders. This may be a view or
     * it may be an immutable copy.
     */
    Collection<? extends RegisteredPlaceholder<?>> allRegisteredPlaceholders();

}
