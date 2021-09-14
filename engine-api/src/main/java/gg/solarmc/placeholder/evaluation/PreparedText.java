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

import gg.solarmc.placeholder.placeholder.EvaluationContext;

/**
 * Text which is ready to be evaluated
 *
 * @param <S> the text type
 * @param <P> the parameter type
 */
public interface PreparedText<S, P> {

    /**
     * Evaluates
     *
     * @param globalParameter the global parameter, which will be available to placeholders
     *                  via {@link EvaluationContext#getGlobalParameter()}
     * @return the evaluated text
     * @throws MissingPlaceholderException if the placeholders involved in the creation of
     * this prepared text do not exist
     */
    S eval(P globalParameter);

}
