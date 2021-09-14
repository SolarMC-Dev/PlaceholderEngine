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

package gg.solarmc.placeholder.impl.engine;

import gg.solarmc.placeholder.impl.engine.parse.PlaceholderParser;
import gg.solarmc.placeholder.impl.engine.parse.PlaceholderVisitor;
import gg.solarmc.placeholder.impl.engine.parse.TextParser;
import gg.solarmc.placeholder.impl.engine.parse.TextVisitor;
import gg.solarmc.placeholder.impl.engine.token.TokenCenter;
import gg.solarmc.placeholder.impl.identifier.PlaceholderIdentifierFactory;
import gg.solarmc.placeholder.impl.registry.TextTypeRegistry;
import gg.solarmc.placeholder.placeholder.TokenFactory;
import gg.solarmc.placeholder.placeholder.TokenInput;
import gg.solarmc.placeholder.registry.PlaceholderIdentifier;
import gg.solarmc.placeholder.registry.PlaceholderRegistry;
import gg.solarmc.placeholder.text.ComposableText;
import gg.solarmc.placeholder.text.PrecomposedText;
import gg.solarmc.placeholder.text.StringDecomposer;
import gg.solarmc.placeholder.text.TextTypeExtension;

import java.util.ArrayList;
import java.util.List;

public record Decomposer<S>(TextTypeExtension<S> textTypeExtension,
                            PlaceholderIdentifierFactory identifierFactory,
                            TextTypeRegistry textTypeRegistry,
                            PlaceholderRegistry placeholderRegistry,
                            TokenFactory tokenFactory,
                            TokenCenter tokenCenter)
        implements StringDecomposer<S, GlobalEvalParameters> {

    private ComposableText<S, GlobalEvalParameters> precomposed(String text) {
        return new PrecomposedText<>(textTypeExtension.fromPlainString(text));
    }

    @Override
    public ComposableText<S, GlobalEvalParameters> decompose(String text) {
        if (text.isEmpty()) {
            return precomposed("");
        }
        List<ComposableText<S, GlobalEvalParameters>> parts = new ArrayList<>();
        new TextParser(text, new TextVisitor() {
            @Override
            public void visitPlainText(CharSequence plainText) {
                if (!plainText.isEmpty()) {
                    parts.add(precomposed(plainText.toString()));
                }
            }

            @Override
            public void visitPlaceholderText(CharSequence placeholderText) {
                assert !placeholderText.isEmpty();
                parts.add(createPlaceholderText(placeholderText.toString()));
            }
        }).parse();
        if (parts.size() == 1) {
            return parts.get(0);
        }
        @SuppressWarnings("unchecked")
        ComposableText<S, GlobalEvalParameters>[] partsArray = parts.toArray(ComposableText[]::new);
        return new CombinedComposableText<>(textTypeExtension, partsArray);
    }

    @Override
    public ComposableText<String, GlobalEvalParameters> decomposeToPlainString(String text) {
        // Use the exact same implementation as #decompose, except use the String extension
        return new Decomposer<String>(
                textTypeRegistry.obtainExtension(String.class),
                identifierFactory,
                textTypeRegistry,
                placeholderRegistry,
                tokenFactory,
                tokenCenter).decompose(text);
    }

    private ComposableText<S, GlobalEvalParameters> createPlaceholderText(String placeholderText) {
        PlaceholderParseResult result = parsePlaceholder(placeholderText);
        return new FlexiblePlaceholderText<>(
                textTypeExtension, result.lazyPlaceholder(), result.tokenizables());
    }

    private Tokenizable createPlaceholderTokenizable(String placeholderText) {
        PlaceholderParseResult result = parsePlaceholder(placeholderText);
        return new FlexibleTokenizable(
                result.lazyPlaceholder(), tokenCenter, result.tokenizables());
    }

    private PlaceholderParseResult parsePlaceholder(String placeholderText) {
        PlaceholderParseResult visitor = new PlaceholderParseResult();
        new PlaceholderParser(placeholderText, visitor).parse();
        return visitor;
    }

    private class PlaceholderParseResult implements PlaceholderVisitor {

        private PlaceholderIdentifier identifier;
        private final List<Tokenizable> arguments = new ArrayList<>();

        @Override
        public void visitIdentifier(CharSequence identifier) {
            assert !identifier.isEmpty();
            this.identifier = identifierFactory.parse(identifier.toString());
        }

        @Override
        public void visitLiteralArgument(CharSequence literal) {
            record LiteralTokenizable(TokenCenter tokenCenter, String literal) implements Tokenizable {

                @Override
                public TokenInput collectTokenInput(GlobalEvalParameters globalEvalParameters) {
                    return tokenCenter.collectTokens((tokenOutput -> {
                        tokenOutput.addOutput(String.class, literal);
                    }));
                }
            }
            arguments.add(new LiteralTokenizable(tokenCenter, literal.toString()));
        }

        @Override
        public void visitPlaceholderArgument(CharSequence placeholderText) {
            assert !placeholderText.isEmpty();
            arguments.add(createPlaceholderTokenizable(placeholderText.toString()));
        }

        LazyPlaceholder lazyPlaceholder() {
            return new LazyPlaceholder(identifier, placeholderRegistry);
        }

        Tokenizable[] tokenizables() {
            return arguments.isEmpty() ? Tokenizable.EMPTY_ARRAY : arguments.toArray(Tokenizable[]::new);
        }
    }

}
