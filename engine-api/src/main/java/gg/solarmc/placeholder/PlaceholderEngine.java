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

package gg.solarmc.placeholder;

import gg.solarmc.placeholder.evaluation.PlaceholderEvaluation;
import gg.solarmc.placeholder.placeholder.AdditionalTokenExtrapolator;
import gg.solarmc.placeholder.placeholder.Placeholder;
import gg.solarmc.placeholder.registry.MalformattedPlaceholderIdentifierException;
import gg.solarmc.placeholder.registry.PlaceholderIdentifier;
import gg.solarmc.placeholder.registry.PlaceholderRegistry;
import gg.solarmc.placeholder.text.TextTypeUnavailableException;

import java.util.Set;

/**
 * The main access point for everything related to placeholders.
 *
 */
public interface PlaceholderEngine {

    /**
     * Parses a placeholder identifier
     *
     * @param identifier the identifier string
     * @return the parsed identifier
     * @throws MalformattedPlaceholderIdentifierException if the identifier cannot be parsed
     */
    PlaceholderIdentifier parseIdentifier(String identifier);

    /**
     * Gets all available text types
     *
     * @return an immutable set of all available text types
     */
    Set<Class<?>> allTextTypes();

    /**
     * Gets the placeholder registry
     *
     * @return the placeholder registry
     */
    PlaceholderRegistry getRegistry();

    /**
     * Gets the placeholder evaluation for a certain text type. Different evaluations allow other kinds
     * of text-like objects to be used as the return value of {@link Placeholder} evaluations. <br>
     * <br>
     * Note that, unlike a registry, some placeholder evaluations may include placeholders registered
     * under other text types. This is because some text types are often 'super text types' of another.
     *
     * @param textType the text type class
     * @param <S> the text type
     * @return placeholder evaluation
     * @throws TextTypeUnavailableException if the given text type is not available
     */
    <S> PlaceholderEvaluation<S> getEvaluation(Class<S> textType);

    /**
     * String based evaluation is guaranteed to be available
     *
     * @return string based placeholder evaluation
     */
    PlaceholderEvaluation<String> getStringEvaluation();

    /**
     * Adds the given token extrapolator
     *
     * @param extrapolator the extrapolator to add
     * @return true if added, false if the given extrapolator is already present
     */
    boolean addTokenExtrapolator(AdditionalTokenExtrapolator extrapolator);

    /**
     * Removes the given token extrapolator
     *
     * @param extrapolator the extrapolator to remove
     * @return true if removed, false if the given extrapolator is not present
     */
    boolean removeTokenExtrapolator(AdditionalTokenExtrapolator extrapolator);

}
