import java.util.Collections;
import java.util.List;
import com.briansea.cabinet.GameState;
import com.briansea.game.GameDim;
import com.briansea.game.Location;
import com.briansea.game.Move;
import com.briansea.game.connect4.Connect4;
import com.briansea.game.Player;
import com.briansea.game.Team;

public class AlphaBetaPruningTree {
	private Kary_Node root;
	private Player me;

	public AlphaBetaPruningTree(Player x) {
		me = x;
	}
	// Alpha and Beta both start at - ∞ and ∞ respectively
	// Max nodes return alpha to a min node and beta becomes that value
	// Min nodes return beta to a max node and alpha becomes that value
	// Alpha and beta are always passed on to the next node from the parent
	// If alpha is greater than beta at any moment, can prune siblings
	// PreOrder traversal
	// Do not create tree nodes until you need them
	// Simulate making a move and calculating the heuristic
	// When depth is zero, calculate heuristic
	// minMax = true for max Node and false for min Nodes

	/** Represents a single node in a Kary tree */
	public class Kary_Node {
		private LList<Kary_Node> children;
		private GameState boardState;
		private int alpha;
		private int beta;
		private boolean isMaxNode;
		private Move bestMove;
		private Move lastMove;

		//Node with just a game state
		public Kary_Node(GameState gs) {
			boardState = gs;
		}

		//Node with a game state, an alpha and beta, whether it's a min or max node,
		// and the last move made (used in evaluating heuristic)
		public Kary_Node(GameState gs, int alpha, int beta, boolean isMax, Move lastMove) {
			boardState = gs;
			this.alpha = alpha;
			this.beta = beta;
			isMaxNode = isMax;
			this.lastMove = lastMove;
		}

		// Returns current best move
		private Move getBestMove() {
			return bestMove;
		}

		// Evaluating the input game state and returning an integer to represent
		// the value of the state
		private int evalHeuristic(GameState gs) {
			GameDim dimensions = gs.getDimensions();
			int[][] bob = new int[dimensions.height()][dimensions.width()];
			int score = 0;

			// If the someone has one
			if (gs.isGameOver()) {
				if (gs.getWinners().get(0).get(0) == me) {
					return Integer.MAX_VALUE - 1;
				} else {
					return Integer.MIN_VALUE + 1;
				}
			}

			// Populate 2d array of ints
			// 1 means I own the spot
			// -1 means they own the spot
			// 0 means no one owns the spot
			for (int row = 0; row < bob.length; row++) {
				for (int col = 0; col < bob[row].length; col++) {
					Location tempLoc = new Location();
					tempLoc.setX(row);
					tempLoc.setY(col);
					List<Team> teamsHere = gs.getOwner(tempLoc);
					if (teamsHere.size() == 0) {
						bob[row][col] = 0;
					} else {
						Player player = teamsHere.get(0).get(0);
						if (player == me) {
							bob[row][col] = 1;
						} else {
							bob[row][col] = -1;
						}
					}
				}
			}

			//"x" represents my spot
			//"y" represents their spot
			//"_" represents empty spot
			// Check for wins across starting on left
			for (int row = 0; row < bob.length; row++) {
				for (int col = 0; col < bob[row].length - 3; col++) {
					// If I'm doing better
					// _xxx
					if (bob[row][col] == 0 && bob[row][col + 1] == 1 && bob[row][col + 2] == 1
							&& bob[row][col + 3] == 1) {
						score += 500000;
					}
					// x_xx
					if (bob[row][col] == 1 && bob[row][col + 1] == 0 && bob[row][col + 2] == 1
							&& bob[row][col + 3] == 1) {
						score += 500000;
					}
					// xx_x
					if (bob[row][col] == 1 && bob[row][col + 1] == 1 && bob[row][col + 2] == 0
							&& bob[row][col + 3] == 1) {
						score += 500000;
					}
					// xxx_
					if (bob[row][col] == 1 && bob[row][col + 1] == 1 && bob[row][col + 2] == 1
							&& bob[row][col + 3] == 0) {
						score += 500000;
					}
					// Opponent doing better
					// _yyy
					if (bob[row][col] == 0 && bob[row][col + 1] == -1 && bob[row][col + 2] == -1
							&& bob[row][col + 3] == -1) {
						score -= 1000000;
					}
					// y_yy
					if (bob[row][col] == -1 && bob[row][col + 1] == 0 && bob[row][col + 2] == -1
							&& bob[row][col + 3] == -1) {
						score -= 1000000;
					}
					// yy_y
					if (bob[row][col] == -1 && bob[row][col + 1] == -1 && bob[row][col + 2] == 0
							&& bob[row][col + 3] == -1) {
						score -= 1000000;
					}
					// yyy_
					if (bob[row][col] == -1 && bob[row][col + 1] == -1 && bob[row][col + 2] == -1
							&& bob[row][col + 3] == 0) {
						score -= 1000000;
					}
					// Instead of spaces, they are blocked
					// yxxx
					if (bob[row][col] == -1 && bob[row][col + 1] == 1 && bob[row][col + 2] == 1
							&& bob[row][col + 3] == 1) {
						score -= 50;
					}
					// xyxx
					if (bob[row][col] == 1 && bob[row][col + 1] == -1 && bob[row][col + 2] == 1
							&& bob[row][col + 3] == 1) {
						score -= 50;
					}
					// xxyx
					if (bob[row][col] == 1 && bob[row][col + 1] == 1 && bob[row][col + 2] == -1
							&& bob[row][col + 3] == 1) {
						score -= 50;
					}
					// xxxy
					if (bob[row][col] == 1 && bob[row][col + 1] == 1 && bob[row][col + 2] == 1
							&& bob[row][col + 3] == -1) {
						score -= 50;
					}
					// Instead of spaces they are blocked
					// xyyy
					if (bob[row][col] == 1 && bob[row][col + 1] == -1 && bob[row][col + 2] == -1
							&& bob[row][col + 3] == -1) {
						score += 100000000;
					}
					// yxyy
					if (bob[row][col] == -1 && bob[row][col + 1] == 1 && bob[row][col + 2] == -1
							&& bob[row][col + 3] == -1) {
						score += 100000000;
					}
					// yyxy
					if (bob[row][col] == -1 && bob[row][col + 1] == -1 && bob[row][col + 2] == 1
							&& bob[row][col + 3] == -1) {
						score += 100000000;
					}
					// yyyx
					if (bob[row][col] == -1 && bob[row][col + 1] == -1 && bob[row][col + 2] == -1
							&& bob[row][col + 3] == 1) {
						score += 100000000;
					}

				}
			}
			return score;
		}

		public int getGameScore(GameState gs) {
			return 0;
		}

		// This method kicks off the MiniMax algorithm
		public int alphaBeta(int depth) {
			return this.alphabetaHelper(Integer.MIN_VALUE, Integer.MAX_VALUE, depth, true);
		}

		// MinMax algorithm for every node other than the root
		public int alphabetaHelper(int alpha, int beta, int depth, boolean minMax) {
			if (me.exit()) {
				if (minMax) {
					return alpha;
				}
				return beta;
			}
			this.children = new LList<Kary_Node>();
			if (depth == 0) {
				int heuristic = this.evalHeuristic(boardState.copyInstance());
				return heuristic;
			}
			List<Move> validMoves = boardState.getValidMoves();
			Collections.shuffle(validMoves);
			// If it's a max node
			if (minMax) {
				for (Move x : validMoves) {
					GameState copy = boardState.copyInstance();
					if (copy != null) {
						copy.makeMove(x);
						this.children.add(new Kary_Node(copy, alpha, beta, !minMax, x));
						int tempAlpha = this.children.get(children.size() - 1).alphabetaHelper(alpha, beta, depth - 1,
								!minMax);

						if (tempAlpha > alpha) {
							alpha = tempAlpha;
							bestMove = x;
						}
						if (alpha > beta) {
							return alpha;
						}
					}
				}
				return alpha;
			
			// If it's a min node
			} else {
				for (Move x : validMoves) {
					GameState copy = boardState.copyInstance();
					copy.makeMove(x);
					this.children.add(new Kary_Node(copy, alpha, beta, !minMax, x));
					int tempBeta = this.children.get(children.size() - 1).alphabetaHelper(alpha, beta, depth - 1,
							!minMax);
					if (tempBeta < beta) {
						beta = tempBeta;
						bestMove = x;
					}
					if (alpha > beta) {
						return beta;
					}
				}
				return beta;
			}
		}
	}

	// Method that the AI calls that kicks off the MiniMax Algorithm
	public Move findBestMove(GameState gs, int depth) {
		root = new Kary_Node(gs);
		root.isMaxNode = true;
		root.alphaBeta(depth);
		return root.getBestMove();
	}

}
