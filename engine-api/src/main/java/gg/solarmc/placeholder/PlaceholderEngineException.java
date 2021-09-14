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

import gg.solarmc.placeholder.evaluation.MalformattedPlaceholderTextException;
import gg.solarmc.placeholder.evaluation.MissingPlaceholderException;
import gg.solarmc.placeholder.placeholder.PlaceholderEvaluationException;
import gg.solarmc.placeholder.registry.DuplicatePlaceholderException;
import gg.solarmc.placeholder.registry.MalformattedPlaceholderIdentifierException;
import gg.solarmc.placeholder.text.TextTypeUnavailableException;

/**
 * Base class for exceptions thrown by the engine, mostly relating to problems which may be
 * outside the control of the caller.
 *
 */
public sealed class PlaceholderEngineException extends RuntimeException
        permits MalformattedPlaceholderTextException, MissingPlaceholderException, PlaceholderEvaluationException,
        DuplicatePlaceholderException, MalformattedPlaceholderIdentifierException, TextTypeUnavailableException {

    /**
     * Creates with a message
     *
     * @param message the message
     */
    public PlaceholderEngineException(String message) {
        super(message);
    }

    /**
     * Creates with a message and cause
     *
     * @param message the message
     * @param cause the cause
     */
    public PlaceholderEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates with a cause
     *
     * @param cause the cause
     */
    public PlaceholderEngineException(Throwable cause) {
        super(cause);
    }

}
