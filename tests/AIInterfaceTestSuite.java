import com.chess.model.AIInterface;
import com.chess.model.ChessGame;
import com.chess.model.ChessGameInterface;
import org.junit.jupiter.api.Test;

public class AIInterfaceTestSuite {
    @Test
    public void testIfBot1IsBetterThanBot2() {
        // TODO!!!
    }

    // play n games between two bots, return the number of times the first bot won (repeat if there's a stalemate)
    public int playNGames(AIInterface bot1, AIInterface bot2, int n) {
        int wins = 0;
        for (int i = 0; i < n; i++)
            wins += playSingleGame(bot1, bot2) == bot1 ? 1 : 0;
        return wins;
    }

    // play a single game against two bots, return the winner (repeat if there's a stalemate)
    public AIInterface playSingleGame(AIInterface bot1, AIInterface bot2) {
        ChessGameInterface game = new ChessGame();
        bot1.setGame(game);
        bot2.setGame(game);
        int moves = 0;
        while (!game.isCurrentPlayerInCheckmate() && !game.isGameInStalemate()) {
            if (moves % 2 == 0)
                bot1.move();
            else
                bot2.move();
            moves++;
        }
        if (game.isGameInStalemate())
            return playSingleGame(bot1, bot2);
        return moves % 2 == 0 ? bot2 : bot1;
    }
}
