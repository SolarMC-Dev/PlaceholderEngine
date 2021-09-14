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

import java.util.Objects;

/**
 * A token extrapolator which adds a token for the superclass when a token value
 * for a subclass is added.
 *
 * @param <S> the superclass type
 */
public final class SuperclassTokenExtrapolator<S> implements AdditionalTokenExtrapolator {

    private final Class<S> superClass;

    /**
     * Creates from a superclass type
     *
     * @param superClass the super class type
     */
    public SuperclassTokenExtrapolator(Class<S> superClass) {
        this.superClass = Objects.requireNonNull(superClass, "superClass");
    }

    @Override
    public <I> void accumulateAdditionalTokens(TokenIdentifier<I> identifier, I value, TokenOutput output) {
        Class<I> subClass = identifier.tokenType();
        if (superClass.isAssignableFrom(subClass) && !superClass.equals(subClass)) {
            output.addOutput(superClass, superClass.cast(value));
        }
    }
}
