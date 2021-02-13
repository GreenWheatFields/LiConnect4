import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.random.Random

class GameManager {
    //takes in moves and sends them to the appropriate game
    val gameDirectory = HashMap<UUID, Game>()
    fun routeMove(int: Int){

    }
}

fun main() {
    GameManager()
}
//fun test1() {
//    val gameIds = ArrayList<UUID>()
//    for (i in 0..100_000) {
//        Game.initGame(6, 7).also {
//            gameIds.add(it.gameID)
//            gameDirectory.put(it.gameID, it)
//        }
//    }
//    repeat(100) {
//        for (game in gameIds) {
//            var temp = gameDirectory[game]
//            if (temp != null){
//                var move = Random.nextInt(temp.board.legalMoves.size)
//                if(temp.pushMove(move)){
//                    println("ending game")
//                    gameDirectory.remove(game)
//                }
//            }
//        }
//
//    }