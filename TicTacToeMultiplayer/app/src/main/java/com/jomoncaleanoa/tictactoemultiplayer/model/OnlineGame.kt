package com.jomoncaleanoa.tictactoemultiplayer.model

data class OnlineGame(
    val playerX: String = "",
    val playerO: String? = null,
    val board: List<List<Player>> = List(3) { List(3) { Player.NONE } },
    val currentPlayer: String = "X",
    val gameState: GameState = GameState.WAITING,
    val rematchRequests: Map<String, Boolean> = mapOf("X" to false, "O" to false),
    val cancelledBy: String? = null
)
{
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "playerX" to playerX,
            "playerO" to playerO,
            "board" to board.map { row -> row.map { it.name } },
            "currentPlayer" to currentPlayer,
            "gameState" to gameState.name,
            "rematchRequests" to rematchRequests,
            "cancelledBy" to cancelledBy
        )
    }

    companion object {
        // 🔹 Reconstruir desde Firebase
        fun fromMap(map: Map<String, Any?>): OnlineGame {
            val board = (map["board"] as? List<List<String>>)?.map { row ->
                row.map { cell ->
                    when (cell) {
                        "X" -> Player.X
                        "O" -> Player.O
                        else -> Player.NONE
                    }
                }
            } ?: List(3) { List(3) { Player.NONE } }

            val rematchRequests = (map["rematchRequests"] as? Map<String, Boolean>) ?: mapOf("X" to false, "O" to false)

            return OnlineGame(
                playerX = map["playerX"] as? String ?: "",
                playerO = map["playerO"] as? String,
                board = board,
                currentPlayer = map["currentPlayer"] as? String ?: "X",
                gameState = when (map["gameState"] as? String) {
                    "X_WON" -> GameState.X_WON
                    "O_WON" -> GameState.O_WON
                    "DRAW" -> GameState.DRAW
                    "PLAYING" -> GameState.PLAYING
                    else -> GameState.WAITING
                },
                rematchRequests = rematchRequests,
                cancelledBy = map["cancelledBy"] as? String
            )
        }
    }
}
