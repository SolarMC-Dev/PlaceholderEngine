import gg.solarmc.placeholder.adventure.AudienceTokenExtrapolator;
import gg.solarmc.placeholder.adventure.ComponentTypeExtension;
import gg.solarmc.placeholder.placeholder.AdditionalTokenExtrapolator;
import gg.solarmc.placeholder.text.TextTypeExtension;

module gg.solarmc.placeholder.adventure {
    requires gg.solarmc.placeholder;
    requires net.kyori.adventure;
    requires net.kyori.adventure.text.serializer.plain;
    provides AdditionalTokenExtrapolator with AudienceTokenExtrapolator;
    provides TextTypeExtension with ComponentTypeExtension;
}