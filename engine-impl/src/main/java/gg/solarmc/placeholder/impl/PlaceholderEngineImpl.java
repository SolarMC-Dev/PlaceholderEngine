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

package gg.solarmc.placeholder.impl;

import gg.solarmc.placeholder.PlaceholderEngine;
import gg.solarmc.placeholder.evaluation.PlaceholderEvaluation;
import gg.solarmc.placeholder.impl.engine.Decomposer;
import gg.solarmc.placeholder.impl.engine.Evaluation;
import gg.solarmc.placeholder.impl.engine.token.TokenCenter;
import gg.solarmc.placeholder.impl.identifier.PlaceholderIdentifierFactory;
import gg.solarmc.placeholder.impl.registry.TextTypeRegistry;
import gg.solarmc.placeholder.placeholder.AdditionalTokenExtrapolator;
import gg.solarmc.placeholder.placeholder.TokenFactory;
import gg.solarmc.placeholder.registry.PlaceholderIdentifier;
import gg.solarmc.placeholder.registry.PlaceholderRegistry;
import gg.solarmc.placeholder.text.TextTypeExtension;

import java.util.Objects;
import java.util.Set;

public record PlaceholderEngineImpl(
        PlaceholderIdentifierFactory identifierFactory,
        TextTypeRegistry textTypeRegistry,
        PlaceholderRegistry placeholderRegistry,
        TokenFactory tokenFactory,
        TokenCenter tokenCenter) implements PlaceholderEngine {

    public PlaceholderEngineImpl {
        Objects.requireNonNull(identifierFactory, "identifierCenter");
        Objects.requireNonNull(textTypeRegistry, "textTypeRegistry");
        Objects.requireNonNull(placeholderRegistry, "placeholderRegistry");
        Objects.requireNonNull(tokenFactory, "tokenFactory");
        Objects.requireNonNull(tokenCenter, "tokenCenter");
    }

    @Override
    public PlaceholderIdentifier parseIdentifier(String identifier) {
        return identifierFactory.parse(identifier);
    }

    @Override
    public Set<Class<?>> allTextTypes() {
        return textTypeRegistry.allTextTypes();
    }

    @Override
    public PlaceholderRegistry getRegistry() {
        return placeholderRegistry;
    }

    @Override
    public <S> PlaceholderEvaluation<S> getEvaluation(Class<S> textType) {
        TextTypeExtension<S> textTypeExtension = textTypeRegistry.obtainExtension(textType);
        return new Evaluation<>(
                textTypeExtension,
                new Decomposer<>(textTypeExtension, identifierFactory, textTypeRegistry,
                        placeholderRegistry, tokenFactory, tokenCenter),
                tokenCenter);
    }

    @Override
    public PlaceholderEvaluation<String> getStringEvaluation() {
        return getEvaluation(String.class);
    }

    @Override
    public boolean addTokenExtrapolator(AdditionalTokenExtrapolator extrapolator) {
        return tokenCenter.addTokenExtrapolator(extrapolator);
    }

    @Override
    public boolean removeTokenExtrapolator(AdditionalTokenExtrapolator extrapolator) {
        return tokenCenter.removeTokenExtrapolator(extrapolator);
    }
}
