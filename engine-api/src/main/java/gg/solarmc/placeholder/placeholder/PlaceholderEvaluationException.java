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

import gg.solarmc.placeholder.PlaceholderEngineException;

/**
 * Exception thrown by placeholders when some prerequisite relating to their usage
 * is violated. The reasons a placeholder may decide to throw this exception are myriad.
 *
 */
public final class PlaceholderEvaluationException extends PlaceholderEngineException {

    /**
     * Creates with a message
     *
     * @param message the message
     */
    public PlaceholderEvaluationException(String message) {
        super(message);
    }

    /**
     * Creates with a message and cause
     *
     * @param message the message
     * @param cause the cause
     */
    public PlaceholderEvaluationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates with a cause
     *
     * @param cause the cause
     */
    public PlaceholderEvaluationException(Throwable cause) {
        super(cause);
    }
}
