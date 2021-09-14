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

import gg.solarmc.placeholder.placeholder.EvaluationContext;
import gg.solarmc.placeholder.placeholder.TokenInput;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

record GlobalEvalParameters(TokenInput globalParameter, TokenInput relationalGlobalParameter) {

    EvaluationContext toEvalContext(Tokenizable[] tokenizables) {
        TokenInput[] tokenInputs = new TokenInput[tokenizables.length];
        for (int n = 0; n < tokenizables.length; n++) {
            tokenInputs[n] = tokenizables[n].collectTokenInput(this);
        }
        return toEvalContext(tokenInputs);
    }

    private EvaluationContext toEvalContext(TokenInput[] input) {
        return new EvaluationContext() {

            @Override
            public TokenInput getGlobalParameter() {
                return globalParameter;
            }

            @Override
            public Optional<TokenInput> getRelationalGlobalParameter() {
                return Optional.ofNullable(relationalGlobalParameter);
            }

            @Override
            public int getArgumentCount() {
                return input.length;
            }

            @Override
            public TokenInput getArgument(int index) {
                return input[index];
            }

            @Override
            public Optional<TokenInput> getArgumentIfPresent(int index) {
                if (index < 0) {
                    throw new IllegalArgumentException("Index must be non-negative");
                }
                if (index >= input.length) {
                    return Optional.empty();
                }
                return Optional.of(input[index]);
            }

            @Override
            public List<TokenInput> getArgumentsStartingAt(int index) {
                if (index < 0) {
                    throw new IllegalArgumentException("Index must be non-negative");
                }
                if (index >= input.length) {
                    return List.of();
                }
                return Arrays.asList(Arrays.copyOfRange(input, index, input.length));
            }

        };
    }
}
