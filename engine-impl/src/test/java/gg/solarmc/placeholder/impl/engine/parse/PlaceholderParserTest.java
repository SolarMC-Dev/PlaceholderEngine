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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import space.arim.api.util.testing.mockito.CharSequenceMatcher;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class PlaceholderParserTest {
    
    private final PlaceholderVisitor visitor;

    public PlaceholderParserTest(@Mock PlaceholderVisitor visitor) {
        this.visitor = visitor;
    }

    private void parse(String text) {
        new PlaceholderParser(text, visitor).parse();
    }

    private CharSequence match(String text) {
        return argThat(new CharSequenceMatcher(text));
    }

    @Test
    public void simplePlaceholder() {
        parse("simple_placeholder");
        verify(visitor).visitIdentifier(match("simple_placeholder"));
    }

    @Test
    public void placeholderWithEmptyParentheses() {
        parse("placeholder_with_empty_parens()");
        verify(visitor).visitIdentifier(match("placeholder_with_empty_parens"));
    }

    @Test
    public void placeholderWithLiteralArgument() {
        parse("placeholder_using_literal('literal')");
        verify(visitor).visitIdentifier(match("placeholder_using_literal"));
        verify(visitor).visitLiteralArgument(match("literal"));
    }

    @Test
    public void placeholderWithLiteralArgumentContainingParentheses() {
        parse("placeholder_with_literal_containing_parens('literal containing ( and )')");
        verify(visitor).visitIdentifier(match("placeholder_with_literal_containing_parens"));
        verify(visitor).visitLiteralArgument(match("literal containing ( and )"));
    }

    @Test
    public void placeholderWithLiteralArgumentContainingPercent() {
        parse("placeholder_with_literal_containing_percent('literal containing %')");
        verify(visitor).visitIdentifier(match("placeholder_with_literal_containing_percent"));
        verify(visitor).visitLiteralArgument(match("literal containing %"));
    }

    @Test
    public void placeholderWithPlaceholderArgument() {
        parse("placeholder_with_placeholder_argument(%placeholder%)");
        verify(visitor).visitIdentifier(match("placeholder_with_placeholder_argument"));
        verify(visitor).visitPlaceholderArgument(match("placeholder"));
    }

    @Test
    public void placeholderWithLiteralAndPlaceholderArgument() {
        parse("placeholder_with_literal_and_placeholder_argument('literal', %placeholder%)");
        verify(visitor).visitIdentifier(match("placeholder_with_literal_and_placeholder_argument"));
        verify(visitor).visitLiteralArgument(match("literal"));
        verify(visitor).visitPlaceholderArgument(match("placeholder"));
    }

    @Test
    public void placeholderWithPlaceholderAndLiteralArgument() {
        parse("placeholder_with_placeholder_and_literal_argument(%placeholder%, 'literal')");
        verify(visitor).visitIdentifier(match("placeholder_with_placeholder_and_literal_argument"));
        verify(visitor).visitPlaceholderArgument(match("placeholder"));
        verify(visitor).visitLiteralArgument(match("literal"));
    }

    @Test
    public void placeholderWithPlaceholderAndLiteralArgumentContainingPercent() {
        parse("placeholder_with_placeholder_and_literal_argument_containing_percent" +
                "(%placeholder%, 'literal containing %')");
        verify(visitor).visitIdentifier(match("placeholder_with_placeholder_and_literal_argument_containing_percent"));
        verify(visitor).visitPlaceholderArgument(match("placeholder"));
        verify(visitor).visitLiteralArgument(match("literal containing %"));
    }

    @Test
    public void missingIdentifier() {
        assertThrows(MalformattedPlaceholderTextException.class, () -> parse("()"));
    }
    
    @AfterEach
    public void verifyNoMoreCalls() {
        verifyNoMoreInteractions(visitor);
    }
}
