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

package gg.solarmc.placeholder.impl.engine.parse;

import gg.solarmc.placeholder.evaluation.MalformattedPlaceholderTextException;

public record PlaceholderParser(String placeholderText, PlaceholderVisitor visitor) {

    MalformattedPlaceholderTextException wrapError(MalformattedPlaceholderTextException cause) {
        String message = cause.getMessage();
        return new MalformattedPlaceholderTextException("""
                Unable to parse placeholder text: {text}.
                Please check your syntax to ensure it looks like %placeholder(arg1, arg2, ...)%
                Reason: {message}
                """
                .replace("{text}", placeholderText).replace("{message}", message),
                cause);
    }

    MalformattedPlaceholderTextException parseError(String message) {
        return new MalformattedPlaceholderTextException(message);
    }

    public void parse() {
        try {
            doParse();
        } catch (MalformattedPlaceholderTextException ex) {
            throw wrapError(ex);
        }
    }

    private void doParse() {
        int indexOfParen = placeholderText.indexOf('(');
        if (indexOfParen == -1) {
            visitor.visitIdentifier(placeholderText);
            return;
        }
        if (placeholderText.charAt(placeholderText.length() - 1) != ')') {
            throw parseError("Unclosed placeholder function, expected \")\" at the end of the function");
        }
        String identifier = placeholderText.substring(0, indexOfParen);
        if (identifier.isEmpty()) {
            // Lone brackets, '()'
            throw parseError("Placeholder identifier must not be empty");
        }
        visitor.visitIdentifier(identifier);
        if (indexOfParen == placeholderText.length() - 2) {
            // Empty arguments, e.g. 'placeholder()'
            return;
        }
        parseFunctionArguments(placeholderText.substring(indexOfParen + 1, placeholderText.length() - 1));
    }

    private enum Expect {
        ARGUMENT,
        TRANSITION_OR_END
    }
    private void parseFunctionArguments(String arguments) {
        Expect expect = Expect.ARGUMENT;
        int lastIndex = arguments.length() - 1;
        int position = 0;
        while (position < arguments.length()) {
            char character = arguments.charAt(position);
            if (expect == Expect.TRANSITION_OR_END) {
                if (character != ',') {
                    throw parseError(
                            "Expected comma between arguments but received " + character + " (char #" + position + ")");
                }
                if (position == lastIndex) {
                    throw parseError("Unexpected trailing comma in arguments");
                }
                int nextPosition = position + 1;
                if (arguments.charAt(nextPosition) != ' ') {
                    throw parseError("Expected space following comma but received " + arguments.charAt(nextPosition)+ " (char #" + nextPosition + ")");
                }
                if (nextPosition == lastIndex) {
                    throw parseError("Unexpected trailing comma and space in arguments");
                }
                expect = Expect.ARGUMENT;
                position += 2;
                continue;
            }
            switch (character) {
            case '\'' -> {
                ScanResult quote = new QuoteScanner(new OffsetCharSequence(arguments, position)).scan();
                visitor.visitLiteralArgument(quote.content());
                expect = Expect.TRANSITION_OR_END;
                position += quote.lengthTakenByContent();
            }
            case '%' -> {
                ScanResult placeholder = new PlaceholderScanner(new OffsetCharSequence(arguments, position)).scan();
                visitor.visitPlaceholderArgument(placeholder.content());
                expect = Expect.TRANSITION_OR_END;
                position += placeholder.lengthTakenByContent();
            }
            default -> throw parseError(
                    "Unexpected character '" + character + "' at position " + position + " in placeholder arguments");
            }
        }
        if (expect != Expect.TRANSITION_OR_END) {
            throw parseError("Unexpected end of placeholder arguments. Expected another argument");
        }
    }

}
