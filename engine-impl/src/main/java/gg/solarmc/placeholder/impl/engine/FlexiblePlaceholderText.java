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

package gg.solarmc.placeholder.impl.engine;

import gg.solarmc.placeholder.impl.engine.token.NoOpTokenOutput;
import gg.solarmc.placeholder.placeholder.EvaluationContext;
import gg.solarmc.placeholder.registry.RegisteredPlaceholder;
import gg.solarmc.placeholder.text.ComposableText;
import gg.solarmc.placeholder.text.TextTypeExtension;

record FlexiblePlaceholderText<S>(TextTypeExtension<S> targetTextTypeExtension,
                                  LazyPlaceholder lazyPlaceholder,
                                  Tokenizable[] tokenizables)
        implements ComposableText<S, GlobalEvalParameters> {

    @Override
    public S compose(GlobalEvalParameters input) {
        return fromActualPlaceholder(input, lazyPlaceholder.findPlaceholder());
    }

    // R - Actual placeholder's text type
    private <R> S fromActualPlaceholder(GlobalEvalParameters globalEvalParameters,
                                        RegisteredPlaceholder<R> placeholder) {
        EvaluationContext evalContext = globalEvalParameters.toEvalContext(tokenizables);
        R evaluation = placeholder.placeholder().evaluate(evalContext, NoOpTokenOutput.INSTANCE);
        TextTypeExtension<R> placeholderTextTypeExtension = placeholder.textTypeExtension();
        if (targetTextTypeExtension == placeholderTextTypeExtension) { // Safe identity-equality
            // R = S
            @SuppressWarnings("unchecked")
            S casted = (S) evaluation;
            return casted;
        }
        // R -> String -> S
        return targetTextTypeExtension.fromPlainString(placeholderTextTypeExtension.toPlainString(evaluation));
    }

}
