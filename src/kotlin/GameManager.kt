import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

class GameManager {
    //takes in moves and sends them to the appropriate game
    val gameDirectory = HashMap<UUID, Game>()

    fun test1(){
        val game1 =  Game.initGame(6,7).also {
            gameDirectory.put(it.gameID, it)
        }
        val game2 =  Game.initGame(6,7).also {
            gameDirectory.put(it.gameID, it)
        }
        repeat(10){
            gameDirectory[game1.gameID]!!.pushMove(Random.nextInt(6) + 1)
            println("switching")
            gameDirectory[game2.gameID]!!.pushMove(Random.nextInt(6) + 1)
        }
    }
}

fun main() {
    GameManager().test1()
}