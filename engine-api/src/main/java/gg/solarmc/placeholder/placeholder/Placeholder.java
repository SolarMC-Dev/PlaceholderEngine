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

package gg.solarmc.placeholder.placeholder;

import gg.solarmc.placeholder.PlaceholderEngine;

/**
 * A user-implemented interface defining a usable placeholder. It is up to the user to
 * choose the text type so long as it is supported by the {@link PlaceholderEngine}. <br>
 * <br>
 * Placeholders are typically used by a server administrator in a configuration context,
 * but usages can also be embedded in string literals in code. <br>
 * <br>
 * Every placeholder may be used as a function. Arguments to the placeholder are given
 * in the {@link EvaluationContext}.
 *
 * @param <S> the text type
 */
public interface Placeholder<S> {

    /**
     * Obtains the value of the placeholder in this context
     *
     * @param evaluationContext the evaluation context, which includes the input arguments
     *                          to this placeholder
     * @param parameterOutput the token output of this placeholder, where additional output
     *                        of this placeholder for use in dependent placeholders may be provided
     * @return the placeholder value itself. The placeholder value is always and automatically
     * added to the {@code parameterOutput} in unqualified form
     */
    S evaluate(EvaluationContext evaluationContext, TokenOutput parameterOutput);

}
