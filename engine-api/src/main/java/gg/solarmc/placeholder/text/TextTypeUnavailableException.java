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

package gg.solarmc.placeholder.text;

import gg.solarmc.placeholder.PlaceholderEngineException;

/**
 * Thrown to indicate a requested text type is not available for use
 *
 */
public final class TextTypeUnavailableException extends PlaceholderEngineException {

    /**
     * Creates with a message
     *
     * @param message the message
     */
    public TextTypeUnavailableException(String message) {
        super(message);
    }

    /**
     * Creates with a message and cause
     *
     * @param message the message
     * @param cause the cause
     */
    public TextTypeUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates with a cause
     *
     * @param cause the cause
     */
    public TextTypeUnavailableException(Throwable cause) {
        super(cause);
    }

}
