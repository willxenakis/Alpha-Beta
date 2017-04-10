import com.briansea.game.Player;

import java.util.List;

import com.briansea.cabinet.GameState;
import com.briansea.cabinet.Plugin;
import com.briansea.cabinet.PluginInfo;
import com.briansea.game.Move;

public class WilliamXAI extends Player{
		/**
		 * Every plugin must provide this static method
		 * @return a filled object describing the plugin
		 */
		public static PluginInfo getInfo() {
			PluginInfo pi = new PluginInfo() {

				@Override
				public String name() {
					return "WilliamXAI";
				}

				@Override
				public String description() {
					return "This is an AI created by me, William Xenakis.";
				}

				@Override
				public Class<? extends Plugin> type() {
					return Player.class;
				}

				@Override
				public List<Class<? extends GameState>> supportedGames() {
					return null;
				}
				
			};
			return pi;
		}
		/**
		 * Your AI should call the super constructor and pass the AI's name
		 */
		public WilliamXAI(){
			super("WilliamXAI");
		}
		
		/**
		 * This method indicates which games the AI supports
		 * @return A list of class objects of games supported, or null if all games are supported
		 */
		public static List<Class<? extends GameState>> supportedGames(){
			return null;
		}
		
		/**
		 * This method is called when your AI needs to make a move.  It should fill in 'm'
		 * with the appropriate move.
		 * 
		 * <b>Note:</b>This method may be ended prematurely.  You should use the exit() method to check 
		 * see if you must exit.
		 * 
		 * @param gs the current state of the game
		 * @param m the object to fill in to represent the AIs move
		 * @return the AIs move; This should be m in most cases
		 */
		public Move makeMove(GameState gs, Move m){
			int depth = 2;
			while(!exit()) {
				AlphaBetaPruningTree tree = new AlphaBetaPruningTree(this);
				m.copy(tree.findBestMove(gs, depth));
				depth += 2;
				System.err.println(depth);
			}
			
			System.err.println("EXIT...");
			return m;
		}
	}
