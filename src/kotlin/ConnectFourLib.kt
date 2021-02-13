import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.system.exitProcess

val YELLOW_PIECE: Byte = 1
val RED_PIECE: Byte = 2
val ZERO: Byte = 0
val WIN_CONDITION = 3 // actual win condition - 1
class Game(val board: Board){
    companion object{
        fun initGame(rows: Int, colums: Int): Game{
            //generate win conditions, check if board size is legal/possible. choose turn
            return Game(Board(rows, colums))
        }
    }
    var turn = Random.nextBoolean()
    var gameID = UUID.randomUUID()

    fun pushMove(pos: Int): Boolean{
        if (board.isLegalStartPos(pos)){
            board.putMove(pos, if (!turn) YELLOW_PIECE else RED_PIECE)
            return board.checkForWin(if (!turn) YELLOW_PIECE else RED_PIECE).also {
                turn = !turn
            }
        }else{
            println("illegal start")
            return false
        }
    }
    fun startGame(){
        while (true){
            board.printBoard()
            board.genLegalMoves().forEach { i: Int -> println(i)  }
            val input = Scanner(System.`in`)
            val move = input.nextInt()
            if(pushMove(move)){
                println("play again?")
                var decision = input.nextInt()
                if (decision == 1){
                    reset()
                    startGame()
                }
                exitProcess(0)
            }else{
//                turn = !turn
            }

        }
    }
    private fun reset(){
        Random.nextBoolean()
        board.clearBoard()
    }
}
class Board(val rows: Int, val colums: Int){
    //todo, detect draw
    private var board: ByteArray = ByteArray(rows * colums)
    private val LAST_ROW = ((rows * colums) - (colums + 1))
    private val SQUARES = rows * colums
    val legalMoves = (0 until colums).toMutableList()
    fun overrideBoard(newBoard: ByteArray){
        board = newBoard
    }
    fun clearBoard(){
        for (cell in board.indices){
            board[cell] = ZERO
        }
    }
    fun genLegalMoves(): MutableList<Int> {
        var temp = -1
        for (startPos in legalMoves){
            if (board[startPos] != ZERO){
                temp = startPos
            }
        }
        if (temp != -1){
            legalMoves.remove(temp)
        }
        return legalMoves
    }

    fun putMove(droppingFrom: Int, color: Byte){
        if (droppingFrom > colums){
            return
        }else{
            for (posistion in droppingFrom..SQUARES step colums){
                if (board[posistion] == YELLOW_PIECE || board[posistion] == RED_PIECE){
                    if ((posistion - colums) < 0) return
                    board[posistion - colums] = color
                    if (posistion < colums) genLegalMoves()
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
    private fun getSum(cells:List<Int>): Int{
        var sum = 0
        for (cell in cells){
            if (board[cell] == ZERO) return -1 //todo, check if cell == opposiing color
            sum += board[cell]
        }
        return sum
    }
    private fun checkSum(sum: Int, color: Byte): Boolean {
        return if (color == YELLOW_PIECE) sum == ((WIN_CONDITION + 1) * YELLOW_PIECE) else sum == ((WIN_CONDITION + 1) * RED_PIECE)
    }

    fun checkForWin(color: Byte): Boolean {
//        printBoard()
        fun checkHorz(cell: Int): Boolean{
            if (cell + WIN_CONDITION <= getRow(cell)){
                if(checkSum(getSum((cell..cell + WIN_CONDITION).toList()), color)) return true
            }
            if (cell - WIN_CONDITION > getRow(cell) - colums){
                if(checkSum(getSum(((cell downTo cell - WIN_CONDITION).toList())), color)) return true
            }
            return false
        }
        fun checkVert(cell: Int): Boolean{
            if (cell + (colums * WIN_CONDITION) < SQUARES){
                if(checkSum(getSum((cell..(cell + WIN_CONDITION * colums) step colums).toList()), color)) return true
            }
            if (cell - (colums * WIN_CONDITION) >= 0){
                if(checkSum(getSum((cell downTo (cell - WIN_CONDITION * colums) step colums).toList()), color)) return true
            }
            return false
        }
        fun checkDiagonal(cell: Int): Boolean{
            //check if at leat two cells in any diagonal direction
//                if (cell )
            if (cell < SQUARES / 2) {
                if (cell + WIN_CONDITION <= getRow(cell)) {
                    //digaonally down to the right
                    if(checkSum(getSum((cell..cell + (WIN_CONDITION * colums + WIN_CONDITION) step colums + 1).toList()), color)){
                        return true
                    }
                }
                if (cell - WIN_CONDITION >= getRow(cell) - colums) {
                    if(checkSum(getSum((cell..cell + (WIN_CONDITION * colums - WIN_CONDITION) step colums - 1).toList()), color)){
                        return true
                    }
                }
            }
            return false
        }
        fun checkDraw(): Boolean{
            //todo, will only check for a completely filled board, not for a board with no possible moves
            for (i in 0 until colums){
                if (board[i] == ZERO){
                    return false
                }
            }
            return true
        }
        for (cell in board.indices){
            if (board[cell] != ZERO){
                if (checkHorz(cell)){
                    println("horizontal Win")
                    return true
                }else if (checkVert(cell)){
                    println("vertical win")
                    return true
                }else if (checkDiagonal(cell)){
                    println("diagonal win")
                    return true
                }else if (checkDraw()){
                    println("draw")
                    return true
                }
            }
        }
        return false
        //this should adapt to varying win conditions. ex 4 in a row. 8 in a row etc
    }
    fun isLegalStartPos(pos: Int): Boolean{
        return legalMoves.contains(pos)
    }
    fun printBoard(){
        board.forEachIndexed { index, byte ->
            if (index % colums == 0){
                print("\n " + byte)
            }else{
                print(" ")
                print(byte)
            }
        }
        println("\n-----------------------------")
    }
}
fun fillHorizontal(range: IntProgression): ArrayList<Int> {
    val commands = ArrayList<Int>()
    for (i in range){
        commands.add(i)
        commands.add(i)
    }
    return commands
}
fun main() {
    val game = Game.initGame(6, 7)
    game.startGame()
}
