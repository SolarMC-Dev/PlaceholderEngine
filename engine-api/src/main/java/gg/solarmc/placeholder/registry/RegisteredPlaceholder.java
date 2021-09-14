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

package gg.solarmc.placeholder.registry;

import gg.solarmc.placeholder.placeholder.Placeholder;
import gg.solarmc.placeholder.text.TextTypeExtension;

/**
 * A registered placeholder
 *
 * @param <S> the text type used by the placeholder
 */
public interface RegisteredPlaceholder<S> {

    /**
     * The text type used by the placeholder
     *
     * @return the text type
     */
    TextTypeExtension<S> textTypeExtension();

    /**
     * The identifier this placeholder was registered under
     *
     * @return the indentifier
     */
    PlaceholderIdentifier identifier();

    /**
     * The placeholder itself
     *
     * @return the placeholder
     */
    Placeholder<S> placeholder();

    /**
     * Implementations are encouraged to provide a useful toString()
     *
     */
    @Override
    String toString();
}
