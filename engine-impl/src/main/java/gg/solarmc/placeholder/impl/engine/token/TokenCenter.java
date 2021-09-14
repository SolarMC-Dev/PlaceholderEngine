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

package gg.solarmc.placeholder.impl.engine.token;

import gg.solarmc.placeholder.placeholder.AdditionalTokenExtrapolator;
import gg.solarmc.placeholder.placeholder.TokenFactory;
import gg.solarmc.placeholder.placeholder.TokenIdentifier;
import gg.solarmc.placeholder.placeholder.TokenInput;
import gg.solarmc.placeholder.placeholder.TokenOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

public final class TokenCenter {

    private final TokenFactory tokenFactory;
    private final Set<AdditionalTokenExtrapolator> extrapolators = new CopyOnWriteArraySet<>();

    TokenCenter(TokenFactory tokenFactory) {
        this.tokenFactory = Objects.requireNonNull(tokenFactory, "tokenFactory");
    }

    public boolean addTokenExtrapolator(AdditionalTokenExtrapolator extrapolator) {
        return extrapolators.add(Objects.requireNonNull(extrapolator, "extrapolator"));
    }

    public boolean removeTokenExtrapolator(AdditionalTokenExtrapolator extrapolator) {
        return extrapolators.remove(Objects.requireNonNull(extrapolator, "extrapolator"));
    }

    private TokenOutput amplifyOutput(TokenOutput baseOutput) {
        record AmplifyingTokenOutput(TokenOutput delegate, TokenCenter tokenCenter)
                implements TokenOutput {

            @Override
            public <I> void addOutput(TokenIdentifier<I> identifier, I value) {
                delegate.addOutput(identifier, value);
                tokenCenter.extrapolators.forEach((extrapolator) -> {
                    extrapolator.accumulateAdditionalTokens(identifier, value, delegate);
                });
            }

            @Override
            public <I> void addOutput(Class<I> identifier, I value) {
                addOutput(tokenCenter.tokenFactory.createIdentifier(identifier), value);
            }
        }
        return new AmplifyingTokenOutput(baseOutput, this);
    }

    public TokenInput collectTokens(Consumer<TokenOutput> tokenPopulator) {
        TokenCollector baseCollector = new TokenCollector(tokenFactory, new HashMap<>());
        tokenPopulator.accept(amplifyOutput(baseCollector));
        return baseCollector;
    }

    public record Creation(ModuleLayer layer) {

        public TokenCenter create(TokenFactory tokenFactory) {
            List<AdditionalTokenExtrapolator> startWith = new ArrayList<>();
            for (AdditionalTokenExtrapolator extrapolator : ServiceLoader.load(layer, AdditionalTokenExtrapolator.class)) {
                startWith.add(extrapolator);
            }
            var result = new TokenCenter(tokenFactory);
            result.extrapolators.addAll(startWith);
            return result;
        }
    }
}
