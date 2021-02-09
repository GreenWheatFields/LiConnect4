val YELLOW_PIECE: Byte = 1
val RED_PIECE: Byte = 2

class ConnectFour {
    // [0, 0, 0]
    // [0, 0, 0]
    // [0, 0, 0]
    class Game(val board: Board){
    }
    class Board(val rows: Int, val colums: Int){
        private val board = ByteArray(rows * colums)
        private val LAST_ROW = ((rows * colums) - colums)

        fun putMove(droppingFrom: Int = 2){
            if (droppingFrom > rows){
                return
            }else{
                for (posistion in droppingFrom..((rows * colums) - droppingFrom + 1) step colums){
                    if (board[droppingFrom] == YELLOW_PIECE || board[droppingFrom] == RED_PIECE){
                        if (posistion > colums){
                            board[posistion - colums] = YELLOW_PIECE
                            return
                        }else{
                            println()
                            //first move // illegal move
                        }
                    }else if (posistion > LAST_ROW){
                        board[posistion] = YELLOW_PIECE
                    }
                }
            }
            printBoard()
        }
        fun printBoard(){
            board.forEachIndexed { index, byte ->
                if (index % rows == 0){
                    print("\n" + byte)
                }else{
                    print(byte)
                }
            }
        }
    }
}
fun main() {
    ConnectFour.Board(3,3).putMove()
}