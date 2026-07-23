package com.example.miniproyecto_batalla_naval.util;

import com.example.miniproyecto_batalla_naval.model.ships.Orientation;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * Utility class responsible for creating all graphical elements
 * used throughout the Battleship game.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public final class ShipShapeFactory {

    /**
     * Default size, in pixels, used for every board cell.
     */
    public static final double CELL_SIZE = 36;

    /**
     * Represents the different graphical segments
     * that compose a ship.
     */
    public enum SegmentType {

        /** Represents a ship consisting of a single cell. */
        SINGLE,

        /** Represents the front segment of a ship. */
        HEAD,

        /** Represents the middle segment of a ship. */
        BODY,

        /** Represents the rear segment of a ship. */
        TAIL
    }

    /**
     * Prevents instantiation of this utility class.
     */
    private ShipShapeFactory() {
    }

    /**
     * Creates the graphical representation of a ship segment
     * according to its type and orientation.
     *
     * @param segment the type of ship segment to create
     * @param orientation the orientation of the ship
     * @return a JavaFX group representing the requested ship segment
     */
    public static Group createShipSegment(
            SegmentType segment,
            Orientation orientation) {

        Group group = new Group();

        boolean horizontal = orientation == Orientation.HORIZONTAL;

        switch (segment) {

            case SINGLE -> {

                Rectangle r = new Rectangle(24,24);

                r.setArcWidth(12);
                r.setArcHeight(12);

                r.setX(6);
                r.setY(6);

                r.setFill(Color.SLATEGRAY);
                r.setStroke(Color.DARKSLATEGRAY);

                group.getChildren().add(r);
            }

            case BODY -> {

                Rectangle body;

                if (horizontal) {
                    body = new Rectangle(32,18);
                    body.setX(2);
                    body.setY(9);
                } else {
                    body = new Rectangle(18,32);
                    body.setX(9);
                    body.setY(2);
                }

                body.setArcWidth(10);
                body.setArcHeight(10);
                body.setFill(Color.SLATEGRAY);
                body.setStroke(Color.DARKSLATEGRAY);

                group.getChildren().add(body);
            }

            case HEAD -> {

                if (horizontal) {

                    Rectangle body = new Rectangle(24,18);
                    body.setX(10);
                    body.setY(9);

                    Polygon tip = new Polygon(
                            2.0,18.0,
                            10.0,9.0,
                            10.0,27.0
                    );

                    body.setFill(Color.SLATEGRAY);
                    body.setStroke(Color.DARKSLATEGRAY);
                    tip.setFill(Color.SLATEGRAY);
                    tip.setStroke(Color.DARKSLATEGRAY);

                    group.getChildren().addAll(body,tip);

                } else {

                    Rectangle body = new Rectangle(18,24);
                    body.setX(9);
                    body.setY(10);

                    Polygon tip = new Polygon(
                            18.0,2.0,
                            9.0,10.0,
                            27.0,10.0
                    );

                    body.setFill(Color.SLATEGRAY);
                    body.setStroke(Color.DARKSLATEGRAY);
                    tip.setFill(Color.SLATEGRAY);
                    tip.setStroke(Color.DARKSLATEGRAY);

                    group.getChildren().addAll(body,tip);
                }
            }

            case TAIL -> {

                if (horizontal) {

                    Rectangle body = new Rectangle(24,18);
                    body.setX(2);
                    body.setY(9);

                    Polygon tip = new Polygon(
                            34.0,9.0,
                            34.0,27.0,
                            26.0,18.0
                    );

                    body.setFill(Color.SLATEGRAY);
                    body.setStroke(Color.DARKSLATEGRAY);
                    tip.setFill(Color.SLATEGRAY);
                    tip.setStroke(Color.DARKSLATEGRAY);

                    group.getChildren().addAll(body,tip);

                } else {

                    Rectangle body = new Rectangle(18,24);
                    body.setX(9);
                    body.setY(2);

                    Polygon tip = new Polygon(
                            9.0,26.0,
                            27.0,26.0,
                            18.0,34.0
                    );

                    body.setFill(Color.SLATEGRAY);
                    body.setStroke(Color.DARKSLATEGRAY);
                    tip.setFill(Color.SLATEGRAY);
                    tip.setStroke(Color.DARKSLATEGRAY);

                    group.getChildren().addAll(body,tip);
                }
            }
        }

        return group;
    }

    /**
     * Creates the graphical marker used to represent
     * a shot that landed on water.
     *
     * @return a JavaFX group representing a water mark
     */
    public static Group createWaterMark() {

        Line l1 = new Line(6,6,CELL_SIZE-6,CELL_SIZE-6);
        Line l2 = new Line(CELL_SIZE-6,6,6,CELL_SIZE-6);

        l1.setStroke(Color.CRIMSON);
        l2.setStroke(Color.CRIMSON);

        l1.setStrokeWidth(3);
        l2.setStrokeWidth(3);

        return new Group(l1,l2);
    }

    /**
     * Creates the graphical marker used to represent
     * a successful hit on a ship.
     *
     * @return a JavaFX group representing a hit mark
     */
    public static Group createHitMark() {

        Circle c = new Circle(
                CELL_SIZE/2,
                CELL_SIZE/2,
                CELL_SIZE/4);

        c.setFill(Color.ORANGE);
        c.setStroke(Color.BLACK);

        return new Group(c);
    }

    /**
     * Creates the graphical marker used to represent
     * a sunk ship.
     *
     * @return a JavaFX group representing a sunk mark
     */
    public static Group createSunkMark() {

        Circle c = new Circle(
                CELL_SIZE/2,
                CELL_SIZE/2,
                CELL_SIZE/3);

        c.setFill(Color.RED);
        c.setStroke(Color.BLACK);

        Line l1 = new Line(8,8,CELL_SIZE-8,CELL_SIZE-8);
        Line l2 = new Line(CELL_SIZE-8,8,8,CELL_SIZE-8);

        l1.setStroke(Color.BLACK);
        l2.setStroke(Color.BLACK);

        l1.setStrokeWidth(2);
        l2.setStrokeWidth(2);

        return new Group(c,l1,l2);
    }

    /**
     * Creates the graphical preview displayed while
     * placing a ship on the board.
     *
     * @param valid indicates whether the placement is valid
     * @return a rectangle representing the placement preview
     */
    public static Rectangle createGhostCell(boolean valid) {
        Rectangle rectangle = new Rectangle(CELL_SIZE - 4, CELL_SIZE - 4);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        rectangle.setX(2);
        rectangle.setY(2);
        rectangle.setFill(valid ? Color.rgb(46, 204, 113, 0.55) : Color.rgb(231, 76, 60, 0.55));
        rectangle.setStroke(valid ? Color.rgb(39, 174, 96, 0.9) : Color.rgb(192, 57, 43, 0.9));
        rectangle.setStrokeWidth(2);
        return rectangle;
    }

}