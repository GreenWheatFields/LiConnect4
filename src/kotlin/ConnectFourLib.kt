import kotlin.random.Random

val YELLOW_PIECE: Byte = 1
val RED_PIECE: Byte = 2
val ZERO: Byte = 0
val WIN_CONDITION = 3 // 4 including the cell looking from
class ConnectFour {
    // [0, 0, 0]
    // [0, 0, 0]
    // [0, 0, 0]
    companion object{
        fun initGame(rows: Int, colums: Int): Game{
            return Game(Board(rows, colums))
        }
    }
    class Game(val board: Board){
        var turn: Boolean = false
        fun pushMove(pos: Int){
            if (board.isLegalStartPos(pos)){
                board.putMove(pos, if (!turn) YELLOW_PIECE else RED_PIECE)

                turn = !turn
            }else{
                println("illegal start")
            }
        }
    }
    class Board(val rows: Int, val colums: Int){
        private val board = ByteArray(rows * colums)
        private val LAST_ROW = ((rows * colums) - (colums + 1))
        private val SQUARES = rows * colums

        fun putMove(droppingFrom: Int, color: Byte){
            if (droppingFrom > colums){
                return
            }else{
                for (posistion in droppingFrom..SQUARES step colums){
                    if (board[posistion] == YELLOW_PIECE || board[posistion] == RED_PIECE){
                        if ((posistion - colums) < 0) return
                        board[posistion - colums] = color
                        checkForWin()
                        return
                    }else if (posistion > LAST_ROW){
                        if (posistion == SQUARES){ // catches startPos = 0
                            board[posistion - colums] = color
                            return
                        }
                        board[posistion] = color
                        return
                    }
                }
            }
        }
        private fun getRow(cell: Int): Int{
            return ((cell/ colums) + 1) * colums - 1
        }
        private fun getSum(cells:Array<Int>): Int{
            var sum = 0
            for (cell in cells){
                if (board[cell] == ZERO) return -1 //todo, check if cell == opposiing color
                sum += board[cell]
            }
            return sum
        }

        private fun checkForWin(){
            fun checkHorz(cell: Int): Boolean{
                if (cell + WIN_CONDITION <= getRow(cell)){
                    println(getSum(arrayOf(cell, cell + 1, cell + 2)))
                    //sum right values
                }
                if (cell - WIN_CONDITION > getRow(cell) - colums){
                    getSum(arrayOf(cell, cell - 1, cell - 2))
                }
                return false
            }
            for (cell in board.indices){
                if (board[cell] != ZERO){
                    if (checkHorz(cell)){
                        ;
                    }
                }
            }
            printBoard()
            //this should adapt to varying win conditions. ex 4 in a row. 8 in a row etc
        }
        fun isLegalStartPos(pos: Int): Boolean{
            return pos < colums
        }
        fun printBoard(){
            board.forEachIndexed { index, byte ->
                if (index % colums == 0){
                    print("\n" + byte)
                }else{
                    print(byte)
                }
            }
            println("\n-----------------------------")
        }
    }
}
fun main() {
    val game = ConnectFour.initGame(6,7)
//    repeat(10){
//        game.pushMove(3)
//    }

}