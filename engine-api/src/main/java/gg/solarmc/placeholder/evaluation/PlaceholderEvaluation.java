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

package gg.solarmc.placeholder.evaluation;

/**
 * A center for preparing text to be evaluated at a later time. The separation of
 * this interface from the actual evaluation of placeholders allows for clever
 * implementations to preparse evaluatable text. <br>
 * <br>
 * Note that actual placeholders are discovered lazily at evaluation time. That is,
 * preparing text does not lookup placeholders and therefore will not indicate
 * whether the text to prepare contains invalid placeholders.
 *
 * @param <S> the text type
 */
public interface PlaceholderEvaluation<S> {

    /**
     * Prepares text to be evaluated
     *
     * @param globalParameterType the class of the global parameter type which will be passed
     *                            to {@link PreparedText#eval}
     * @param text the text to prepare
     * @param <P> the global parameter type
     * @return the prepared text
     * @throws MalformattedPlaceholderTextException if the text to prepare could not be parsed
     */
    <P> PreparedText<S, P> prepareText(Class<P> globalParameterType, S text);

    /**
     * Prepares text to be evaluated
     *
     * @param globalParameterType the class of the global parameter type
     * @param globalRelationalParameterType the class of the global relational parameter type
     * @param text the text to prepare
     * @param <PM> the main global parameter type
     * @param <PR> the relational global parameter type
     * @return the prepared text
     * @throws MalformattedPlaceholderTextException if the text to prepare could not be parsed
     */
    <PM, PR> PreparedRelationalText<S, PM, PR> prepareRelationalText(Class<PM> globalParameterType,
                                                                     Class<PR> globalRelationalParameterType,
                                                                     S text);

}
