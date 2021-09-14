import gg.solarmc.placeholder.PlaceholderEngineFactory;
import gg.solarmc.placeholder.placeholder.AdditionalTokenExtrapolator;
import gg.solarmc.placeholder.placeholder.CharSequenceTokenExtrapolator;
import gg.solarmc.placeholder.placeholder.Placeholder;
import gg.solarmc.placeholder.registry.PlaceholderRegistry;

/**
 * Defines the main API for PlaceholderEngine. <br>
 * <br>
 * For registering placeholders and evaluating them, see {@link PlaceholderRegistry}. <br>
 * <br>
 * <b>Nullability</b> <br>
 * No methods return {@code null}. No parameters accept {@code null} values and
 * may throw {@code NullPointerException} otherwise. <br>
 * <br>
 * <b>Thread Safety</b> <br>
 * Unless stated otherwise, interface implementations are expected to be thread safe.
 * Commonly, that which is not thread safe, and is documented as such, are "accumulator"-like
 * objects. <br>
 * <br>
 * <b>Generic Conventions</b> <br>
 * The API adheres to certain conventions for generic parameters. "I" refers to a token type,
 * "S" to a text type, and "P" to a parameter type. "S", in practice, is often String, but not always. <br>
 * <br>
 * <b>Interface Usage</b> <br>
 * In general, API interfaces are designed to be implemented by the engine implementation,
 * but not by API users. The exception to this is where interfaces are explicitly
 * documented otherwise, {@link Placeholder} being a notable example. {@code IllegalArgumentException}
 * may be thrown by the engine implementation if it detects improper implementation
 * of API interfaces.
 */
module gg.solarmc.placeholder {
    exports gg.solarmc.placeholder;
    exports gg.solarmc.placeholder.evaluation;
    exports gg.solarmc.placeholder.placeholder;
    exports gg.solarmc.placeholder.registry;
    exports gg.solarmc.placeholder.text;

    uses PlaceholderEngineFactory;
    provides AdditionalTokenExtrapolator with CharSequenceTokenExtrapolator;
}