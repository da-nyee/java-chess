package chess.dao;

import chess.dao.setting.DBConnection;
import chess.dto.ChessRequestDto;
import chess.dto.MoveRequestDto;
import chess.dto.PieceRequestDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao extends DBConnection {
    public void initializePieceStatus(final PieceRequestDto pieceRequestDto) throws SQLException {
        String query = "INSERT INTO piece (piece_name, piece_position) VALUE (?, ?)";
        PreparedStatement psmt = getConnection().prepareStatement(query);
        psmt.setString(1, pieceRequestDto.getPieceName());
        psmt.setString(2, pieceRequestDto.getPiecePosition());
        psmt.executeUpdate();
    }

    public List<ChessRequestDto> showAllPieces() throws SQLException {
        List<ChessRequestDto> pieces = new ArrayList<>();
        ResultSet rs = readPieceStatus();
        while (rs.next()) {
            pieces.add(new ChessRequestDto(
                    rs.getLong("id"),
                    rs.getString("piece_name"),
                    rs.getString("piece_position")
            ));
        }
        return pieces;
    }

    private ResultSet readPieceStatus() throws SQLException {
        String query = "SELECT * FROM piece";
        PreparedStatement psmt = getConnection().prepareStatement(query);
        return psmt.executeQuery();
    }

    public void movePiece(final MoveRequestDto moveRequestDto) throws SQLException {
        String query = "UPDATE piece SET piece_position=? WHERE piece_position=?";
        PreparedStatement psmt = getConnection().prepareStatement(query);
        psmt.setString(1, moveRequestDto.getTarget());
        psmt.setString(2, moveRequestDto.getSource());
        psmt.executeUpdate();
    }

    public void removeAllPieces() throws SQLException {
        String query = "DELETE FROM piece";
        PreparedStatement psmt = getConnection().prepareStatement(query);
        psmt.executeUpdate();
    }

    public void removePiece(final MoveRequestDto moveRequestDto) throws SQLException {
        String query = "DELETE FROM piece WHERE piece_position=?";
        PreparedStatement psmt = getConnection().prepareStatement(query);
        psmt.setString(1, moveRequestDto.getTarget());
        psmt.executeUpdate();
    }
}
