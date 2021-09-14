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

package gg.solarmc.placeholder.evaluation;

import gg.solarmc.placeholder.PlaceholderEngineException;

/**
 * Thrown to indicate that a placeholder does not exist but is required for evaluation
 *
 */
public final class MissingPlaceholderException extends PlaceholderEngineException {

    /**
     * Creates with a message
     *
     * @param message the message
     */
    public MissingPlaceholderException(String message) {
        super(message);
    }

    /**
     * Creates with a message and cause
     *
     * @param message the message
     * @param cause the cause
     */
    public MissingPlaceholderException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates with a cause
     *
     * @param cause the cause
     */
    public MissingPlaceholderException(Throwable cause) {
        super(cause);
    }
    
}
