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

import gg.solarmc.placeholder.PlaceholderEngine;
import gg.solarmc.placeholder.evaluation.PlaceholderEvaluation;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

class EngineToPAPIPlaceholder extends PlaceholderExpansion {

    private final PlaceholderEngine placeholderEngine;

    EngineToPAPIPlaceholder(PlaceholderEngine placeholderEngine) {
        this.placeholderEngine = placeholderEngine;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "placeholderengine";
    }

    @Override
    public @NotNull String getAuthor() {
        return "PlaceholderEngine developers";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0-SNAPSHOT";
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        PlaceholderEvaluation<String> evaluation = placeholderEngine.getStringEvaluation();
        String placeholderText = '%' + params + '%';
        if (offlinePlayer instanceof Player player) {
            return evaluation.prepareText(Player.class, placeholderText).eval(player);
        } else {
            return evaluation.prepareText(OfflinePlayer.class, placeholderText).eval(offlinePlayer);
        }
    }
}
