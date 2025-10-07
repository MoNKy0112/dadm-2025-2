package com.jomoncaleanoa.tictactoemultiplayer.data

import com.google.firebase.database.*
import com.jomoncaleanoa.tictactoemultiplayer.model.GameState
import com.jomoncaleanoa.tictactoemultiplayer.model.OnlineGame
import com.jomoncaleanoa.tictactoemultiplayer.model.Player

class FirebaseRepository {

    private val db = FirebaseDatabase.getInstance().getReference("games")

    // 🔹 Crear un nuevo juego
    fun createGame(playerX: String, onCreated: (String) -> Unit) {
        val key = db.push().key ?: return
        val newGame = OnlineGame(playerX = playerX)
        db.child(key).setValue(newGame).addOnSuccessListener { onCreated(key) }
    }

    // 🔹 Unirse a un juego existente
    fun joinGame(gameId: String, playerO: String, onJoined: () -> Unit) {
        db.child(gameId).child("playerO").setValue(playerO)
        db.child(gameId).child("gameState").setValue(GameState.PLAYING.name)
            .addOnSuccessListener { onJoined() }
    }

    // 🔹 Escuchar lista de partidas
    fun listenForGames(onGamesChanged: (Map<String, OnlineGame>) -> Unit) {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val games = snapshot.children.mapNotNull {
                    val game = it.getValue(OnlineGame::class.java)
                    if (game != null && game.gameState == GameState.WAITING) it.key!! to game else null
                }.toMap()
                onGamesChanged(games)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // 🔹 Escuchar una partida específica
    fun listenGame(gameId: String, onGameChanged: (OnlineGame) -> Unit) {
        db.child(gameId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(OnlineGame::class.java)?.let { onGameChanged(it) }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // 🔹 Actualizar jugada
    fun makeMove(gameId: String, board: List<List<String>>, nextPlayer: String, gameState: GameState) {
        val updates = mapOf(
            "board" to board,
            "currentPlayer" to nextPlayer,
            "gameState" to gameState.name
        )
        db.child(gameId).updateChildren(updates)
    }

    // 🔹 Reiniciar partida
    fun resetGame(gameId: String) {
        val resetBoard = List(3) { List(3) { Player.NONE.name } }
        val updates = mapOf(
            "board" to resetBoard,
            "currentPlayer" to "X",
            "gameState" to GameState.PLAYING.name
        )
        db.child(gameId).updateChildren(updates)
    }

    // marcar voto de revancha (true/false)
    fun setRematchVote(gameId: String, playerSymbol: String, vote: Boolean) {
        db.child(gameId).child("rematchRequests").child(playerSymbol).setValue(vote)
    }

    // dejar partida (anular / cancelar) y avisar al otro jugador
    fun leaveGame(gameId: String, leaverName: String) {
        val updates = mapOf<String, Any>(
            "gameState" to GameState.CANCELLED.name,
            "cancelledBy" to leaverName
        )
        db.child(gameId).updateChildren(updates)
    }
}
