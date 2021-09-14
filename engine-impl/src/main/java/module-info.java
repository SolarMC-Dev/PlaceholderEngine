import gg.solarmc.placeholder.PlaceholderEngineFactory;
import gg.solarmc.placeholder.placeholder.AdditionalTokenExtrapolator;
import gg.solarmc.placeholder.text.TextTypeExtension;
import gg.solarmc.placeholder.impl.provider.PlaceholderEngineProvider;
import gg.solarmc.placeholder.impl.provider.StringTypeExtension;

module gg.solarmc.placeholder.impl {
    requires gg.solarmc.placeholder;
    uses AdditionalTokenExtrapolator;
    uses TextTypeExtension;
    provides PlaceholderEngineFactory with PlaceholderEngineProvider;
    provides TextTypeExtension with StringTypeExtension;
}