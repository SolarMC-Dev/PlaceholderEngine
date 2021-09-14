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

import gg.solarmc.placeholder.evaluation.PlaceholderEvaluation;
import gg.solarmc.placeholder.evaluation.PreparedRelationalText;
import gg.solarmc.placeholder.evaluation.PreparedText;
import gg.solarmc.placeholder.impl.engine.token.TokenCenter;
import gg.solarmc.placeholder.placeholder.TokenInput;
import gg.solarmc.placeholder.text.ComposableText;
import gg.solarmc.placeholder.text.StringDecomposer;
import gg.solarmc.placeholder.text.TextTypeExtension;

public record Evaluation<S>(TextTypeExtension<S> textTypeExtension,
                            StringDecomposer<S, GlobalEvalParameters> stringDecomposer,
                            TokenCenter tokenCenter)
        implements PlaceholderEvaluation<S> {

    private <I> TokenInput newTokenInputWith(Class<I> identifier, I value) {
        return tokenCenter.collectTokens((tokenOutput) -> tokenOutput.addOutput(identifier, value));
    }

    private ComposableText<S, GlobalEvalParameters> decompose(S text) {
        return textTypeExtension.prepareText(text, stringDecomposer);
    }

    @Override
    public <P> PreparedText<S, P> prepareText(Class<P> globalParameterType, S text) {
        ComposableText<S, GlobalEvalParameters> composable = decompose(text);
        class PreparedTextImpl implements PreparedText<S, P> {

            @Override
            public S eval(P globalParameter) {
                GlobalEvalParameters globalEvalState = new GlobalEvalParameters(
                        newTokenInputWith(globalParameterType, globalParameter),
                        null);
                return composable.compose(globalEvalState);
            }
        }
        return new PreparedTextImpl();
    }

    @Override
    public <PM, PR> PreparedRelationalText<S, PM, PR> prepareRelationalText(Class<PM> globalParameterType,
                                                                            Class<PR> globalRelationalParameterType,
                                                                            S text) {
        ComposableText<S, GlobalEvalParameters> composable = decompose(text);
        class PreparedRelationalTextImpl implements PreparedRelationalText<S, PM, PR> {
            @Override
            public S eval(PM globalParameter, PR globalRelationalParameter) {
                GlobalEvalParameters globalEvalState = new GlobalEvalParameters(
                        newTokenInputWith(globalParameterType, globalParameter),
                        newTokenInputWith(globalRelationalParameterType, globalRelationalParameter));
                return composable.compose(globalEvalState);
            }
        }
        return new PreparedRelationalTextImpl();
    }
}
