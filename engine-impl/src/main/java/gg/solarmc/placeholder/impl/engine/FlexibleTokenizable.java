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

import gg.solarmc.placeholder.impl.engine.token.TokenCenter;
import gg.solarmc.placeholder.placeholder.EvaluationContext;
import gg.solarmc.placeholder.placeholder.TokenInput;
import gg.solarmc.placeholder.registry.RegisteredPlaceholder;

record FlexibleTokenizable(LazyPlaceholder lazyPlaceholder, TokenCenter tokenCenter, Tokenizable[] tokenizables)
        implements Tokenizable {

    @Override
    public TokenInput collectTokenInput(GlobalEvalParameters globalEvalParameters) {
        return fromActualPlaceholder(lazyPlaceholder.findPlaceholder(), globalEvalParameters);
    }

    private <S> TokenInput fromActualPlaceholder(RegisteredPlaceholder<S> placeholder,
                                                 GlobalEvalParameters globalEvalParameters) {
        EvaluationContext evalContext = globalEvalParameters.toEvalContext(tokenizables);
        return tokenCenter.collectTokens((tokenOutput) -> {
            S evaluation = placeholder.placeholder().evaluate(evalContext, tokenOutput);
            tokenOutput.addOutput(placeholder.textTypeExtension().textType(), evaluation);
        });
    }
}
