import java.util.ArrayList;  



public class MoveChooser {
    private static int [][] squareValues = {
        { 120,-20, 20, 5, 5,20,-20,120  },
        {-20, -40, -5, -5, -5, -5, -40, -20},
        {20, -5, 15, 3, 3, 15, -5, 20},
        {5, -5, 3, 3, 3, 3, -5, 5},
        {5, -5, 3, 3, 3, 3, -5, 5},
        {20, -5, 15, 3, 3, 15, -5, 20},
        {-20, -40, -5, -5, -5, -5, -40, -20},
        { 120,-20,20, 5, 5,20,-20,120 },

    };


    public static Move chooseMove(BoardState boardState){

	    int searchDepth= Othello.searchDepth;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        Move bestMove = null;
        int bestValue = 0;
    
        ArrayList<Move> moves= boardState.getLegalMoves();
        
        if(moves.isEmpty()){
            return null;
	}
        int index = 0;
        for(Move move: moves){
            BoardState bstate = boardState.deepCopy();
            bstate.makeLegalMove(move.x, move.y);

            int value = minMax(bstate, searchDepth, false, alpha, beta);

            if(index == 0){
                bestMove = move;
                bestValue = value;
                index++;
            }
            if(value > beta){
                return bestMove;
            }
            if(value > alpha){
                alpha = value;
            }
            if(bestValue < value){
                bestMove = move;
                bestValue = value;
            }
        }

        return bestMove;


    }

    public static int minMax(BoardState boardstate, int depth, boolean isMax, int alpha, int beta){
        ArrayList<Move> moves = boardstate.getLegalMoves();
        
        int value = 0;

        if (depth == 0 || boardstate.gameOver()){
            return boardPosition(boardstate);
        }

        if(moves.isEmpty()){
            BoardState bstate = boardstate.deepCopy();
            bstate.colour *= -1;

            return minMax(bstate, depth-1, !isMax, alpha, beta);
        }


        for(int i = 0; i < moves.size(); i++){
            Move move = moves.get(i);

            BoardState boardstate1 = boardstate.deepCopy();
            boardstate1.makeLegalMove(move.x, move.y);

            int val = minMax(boardstate1, depth-1, !isMax, alpha, beta);
          
            if(i==0){
                value = val;
            }

            if(isMax){
                if(val > beta){
                    return val;
                }

                if(val > alpha){
                    alpha = val;
                }

                value = Math.max(value, val);
            }

            else{
                if(val < beta){
                    beta = val;
                }

                if(val < alpha){
                    return val;
                }

                value = Math.min(value, val); 
            }
        }
        return value;
        
    }

    public static int boardPosition(BoardState bstate){
        int white = 0;
        int black = 0;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                int color = bstate.getContents(i, j);

                if(color == 1){
                    white += squareValues[i][j];
                }

                if(color == -1){
                    black += squareValues[i][j];
                }
            }
        }

        return white - black;

    }
   
 

}

