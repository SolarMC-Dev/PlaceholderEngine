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

package gg.solarmc.placeholder.papibridge;

import gg.solarmc.placeholder.placeholder.EvaluationContext;
import gg.solarmc.placeholder.placeholder.Placeholder;
import gg.solarmc.placeholder.placeholder.PlaceholderEvaluationException;
import gg.solarmc.placeholder.placeholder.TokenInput;
import gg.solarmc.placeholder.placeholder.TokenOutput;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Optional;

final class PAPIToEnginePlaceholder implements Placeholder<String> {

    private static final String USAGE = "Cannot evaluate this placeholder without a string argument " +
            "specifying the PAPI placeholder. For example, %papi('my_papi_placeholder')%";

    @Override
    public String evaluate(EvaluationContext evaluationContext, TokenOutput parameterOutput) {
        if (PlaceholderAPIPlugin.getInstance() == null) {
            return "PlaceholderAPI not available";
        }
        String placeholderText;
        {
            TokenInput papiPlaceholderArgument;
            try {
                papiPlaceholderArgument = evaluationContext.getArgument(0);
            } catch (@SuppressWarnings("ProhibitedExceptionCaught") IndexOutOfBoundsException ex) {
                throw new PlaceholderEvaluationException(USAGE, ex);
            }
            String papiPlaceholder = papiPlaceholderArgument.findToken(String.class)
                    .orElseThrow(() -> new PlaceholderEvaluationException(USAGE));
            placeholderText = '%' + papiPlaceholder + '%';
        }
        TokenInput globalParameter = evaluationContext.getGlobalParameter();
        Optional<Player> optPlayer = globalParameter.findToken(Player.class);
        if (optPlayer.isPresent()) {
            return PlaceholderAPI.setPlaceholders(optPlayer.get(), placeholderText);
        }
        OfflinePlayer offlinePlayer = globalParameter.findToken(OfflinePlayer.class)
                .orElseThrow(() -> new PlaceholderEvaluationException(
                        "Cannot evaluate this placeholder without a Player or OfflinePlayer in the global parameter"));
        return PlaceholderAPI.setPlaceholders(offlinePlayer, placeholderText);
    }
}
