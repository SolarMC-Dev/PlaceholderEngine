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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import space.arim.api.util.testing.mockito.CharSequenceMatcher;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class TextParserTest {

    private final TextVisitor visitor;

    public TextParserTest(@Mock TextVisitor visitor) {
        this.visitor = visitor;
    }
    
    private void parse(String text) {
        new TextParser(text, visitor).parse();
    }

    private CharSequence match(String text) {
        return argThat(new CharSequenceMatcher(text));
    }

    @Test
    public void emptyText() {
        parse("");
        verify(visitor).visitPlainText(match(""));
    }

    @Test
    public void simpleText() {
        parse("simple");
        verify(visitor).visitPlainText(match("simple"));
    }

    @Test
    public void escapedPercentages() {
        parse("escaped %% s");
        verify(visitor).visitPlainText(match("escaped % s"));
    }

    @Test
    public void escapedTrailingPercentages() {
        parse("escaped %%");
        verify(visitor).visitPlainText(match("escaped %"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"simple_placeholder", "placeholder_args()",
            "placeholder_args('literal')", "placeholder_args(%other_placeholder%)",
            "placeholder_args('literal', %other_placeholder%)", "placeholder_args(%other_placeholder%, 'literal')"})
    public void placeholderOnly(String placeholder) {
        parse('%' + placeholder + '%');
        verify(visitor, times(2)).visitPlainText(match(""));
        verify(visitor).visitPlaceholderText(placeholder);
    }

    @ParameterizedTest
    @ValueSource(strings = {"simple_placeholder", "placeholder_args()",
            "placeholder_args('literal')", "placeholder_args(%other_placeholder%)",
            "placeholder_args('literal', %other_placeholder%)", "placeholder_args(%other_placeholder%, 'literal')"})
    public void simplePlaceholder(String placeholder) {
        parse("text with %" + placeholder + '%');
        verify(visitor).visitPlainText(match("text with "));
        verify(visitor).visitPlaceholderText(placeholder);
        verify(visitor).visitPlainText(match(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"simple_placeholder", "placeholder_args()",
            "placeholder_args('literal')", "placeholder_args(%other_placeholder%)",
            "placeholder_args('literal', %other_placeholder%)", "placeholder_args(%other_placeholder%, 'literal')"})
    public void placeholderWithinText(String placeholder) {
        parse("placeholder %" + placeholder + "% surrounded");
        verify(visitor).visitPlainText(match("placeholder "));
        verify(visitor).visitPlaceholderText(placeholder);
        verify(visitor).visitPlainText(match(" surrounded"));
    }

    @AfterEach
    public void verifyNoMoreCalls() {
        verifyNoMoreInteractions(visitor);
    }
    
}
