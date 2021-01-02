package com.harryabad.chess.engine.board;

import com.google.common.collect.ImmutableMap; //3rd part class imported Guava 18.0
import com.harryabad.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile { // tile class has been made immutable meaning it cannot me mutated

    protected final int tileCoordinate; //protected can only be accessed by subclassed. final means once set, it cannot be set again

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();


    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles() {
        // A map is  a container
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
        //ImmutableMap means once created map no once can change it
        //Uses 3rd party tool called Guava
        //Without using 3rd part, could have also used JDKs Collections.unmodifiableMap(emptyTileMap)
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece){ // only way to create a tile is to use this factory method
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
        // if you want a tile you'll either get one of the cached empty tiles, or create a new tile
    }

    private Tile(final int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied(); // abstract tells us method will be defined in a subclass

    public abstract Piece getPiece(); // get piece of a given tile

    public int getTileCoordinate(){
        return this.tileCoordinate;
    }

    /*
    Create a Subclass for emptyTile
     */
    public static final class EmptyTile extends Tile { //sub classes can be declared in own file and therefore no need to static
        //emptyTile constructor
        private EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        @Override
        public String toString(){
            return "-"; //empty Tile
        }

        @Override
        public boolean isTileOccupied() {
            return false;
            //tile is empty
        }

        @Override
        public Piece getPiece() {
            return null;
            //no piece to return so null
        }
    }

    /*
    Create a Subclass for occupiedTile
    */
    public static final class OccupiedTile extends Tile{
        //Piece will be on an occupied tile so must declare it
        private final Piece pieceOnTile; // private tells us no way to reference this variable outside subclass

        private OccupiedTile(int tileCoordinate, final Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString(){
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString(); //occupied tile
            //black will show up as lowercase, WHITE WILL SHOW UP AS UPPERCASE
        }

        @Override
        public boolean isTileOccupied(){
            return true;
            //piece is on tile
        }

        @Override
        public Piece getPiece(){
            return this.pieceOnTile;
            //returns piece on tile
        }

    }


}
