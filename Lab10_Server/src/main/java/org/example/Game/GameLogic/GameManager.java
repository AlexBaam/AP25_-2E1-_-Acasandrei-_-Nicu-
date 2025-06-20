package org.example.Game.GameLogic;

import org.example.Game.Players.AIHexPlayer;
import org.example.Game.Players.Player;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager {
    private final Map<String, PlayGame> games = new ConcurrentHashMap<>();

    public String createGame(Player redPlayer) {
        String gameId = UUID.randomUUID().toString().substring(0, 5); // Ptr a genera un room ID scurt
        PlayGame game = new PlayGame(redPlayer, (Player) null, 11); // Blue va fi adaugat la join
        games.put(gameId, game);
        return gameId;
    }

    public boolean joinGame(String gameId, Player bluePlayer) {
        PlayGame game = games.get(gameId);
        if ((game == null) || (game.getBluePlayer() != null)) {
            return false; // jocul nu exista sau deja are 2 jucatori
        }

        game.setBluePlayer(bluePlayer);
        return true;
    }

    public boolean submitMove(String gameId, Player player, int row, int column) {
        PlayGame game = games.get(gameId);
        if ((game == null) || (game.hasEnded())) {
            return false;
        }

        synchronized (game) {
            return game.makeMove(player, row, column);
        }
    }

    public PlayGame getGame(String gameId) {
        return games.get(gameId);
    }

    public Collection<PlayGame> getGames() {
        return games.values();
    }

    public String createAIGame(Player red) {
        String gameId = UUID.randomUUID().toString().substring(0, 5);
        AIHexPlayer hexAI = new AIHexPlayer();
        PlayGame game = new PlayGame(red, hexAI, 11);
        games.put(gameId, game);
        return gameId;
    }
}
