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

package gg.solarmc.placeholder;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * Provides a common means of obtaining a new placeholder engine. <br>
 * <br>
 * This interface doubles as a SPI for the placeholder engine implementation.
 *
 */
public interface PlaceholderEngineFactory {

    /**
     * Creates a new engine. The implementation will be located using the given
     * module layer for service loading.
     *
     * @param layer the module layer for service loading purposes
     * @return the created engine
     * @throws NoSuchElementException if no engine implementation is found
     * @throws IllegalStateException if multiple engine implementations are found
     */
    static PlaceholderEngine createNewEngine(ModuleLayer layer) {
        Objects.requireNonNull(layer, "layer");
        PlaceholderEngineFactory chosen = null;
        for (PlaceholderEngineFactory factory : ServiceLoader.load(layer, PlaceholderEngineFactory.class)) {
            if (chosen != null) {
                throw new IllegalStateException(
                        "Multiple PlaceholderEngine implementations found: " + factory + " and " + chosen);
            }
            chosen = factory;
        }
        if (chosen == null) {
            throw new NoSuchElementException("No PlaceholderEngine implementation found");
        }
        return chosen.createEngine(layer);
    }

    /**
     * Creates the engine using the given module layer for service loading
     *
     * @param layer the module layer for service loading purposes
     * @return the created engine
     */
    PlaceholderEngine createEngine(ModuleLayer layer);

}
