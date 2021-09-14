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

import java.util.List;
import java.util.Optional;

/**
 * Context for {@link Placeholder#evaluate(EvaluationContext, TokenOutput)}
 * which includes all available input arguments.
 *
 */
public interface EvaluationContext {

    /**
     * Gets the global parameter for the entire evaluatable text
     *
     * @return the global argument
     */
    TokenInput getGlobalParameter();

    /**
     * Gets the relational global parameter for the entire evaluatable text
     *
     * @return the relational global argument, if present
     */
    Optional<TokenInput> getRelationalGlobalParameter();

    /**
     * Gets the total amount of arguments
     *
     * @return the total argument amount
     */
    int getArgumentCount();

    /**
     * Gets a parameter argument at the specified index
     *
     * @param index the index at which to find the argument
     * @return the argument's token input
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= getArgumentCount()}
     */
    TokenInput getArgument(int index);

    /**
     * Gets a parameter argument at the specified index if it is present
     *
     * @param index the index at which to find the argument
     * @return the argument's token input if present, an empty optional if there is no such index
     * @throws IllegalArgumentException if {@code index < 0}
     */
    Optional<TokenInput> getArgumentIfPresent(int index);

    /**
     * Gets all arguments at and after the specified index. <br>
     * <br>
     * If the specified index does not exist, an empty list is returned. <br>
     * <br>
     * The returned list should be treated as immutable, though the implementation
     * is free to yield mutable copies in practice.
     *
     * @param index the starting index at which to began listing arguments
     * @return all the arguments at and after the specified index, in order
     * @throws IllegalArgumentException if {@code index < 0}
     */
    List<TokenInput> getArgumentsStartingAt(int index);

}
