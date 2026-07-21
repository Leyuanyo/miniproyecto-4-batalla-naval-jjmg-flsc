package com.example.miniproyecto_batalla_naval.util;

import com.example.miniproyecto_batalla_naval.model.ships.Orientation;
import com.example.miniproyecto_batalla_naval.model.ships.ShipType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public final class ShipShapeFactory {

    public static final double CELL_SIZE = 36;

    private ShipShapeFactory() {
    }

    public static Group createShip(ShipType type, Orientation orientation) {
        double length = type.getSize() * CELL_SIZE;

        Rectangle body = new Rectangle(length, CELL_SIZE);
        body.setArcWidth(14);
        body.setArcHeight(14);
        body.setFill(Color.SLATEGRAY);
        body.setStroke(Color.DARKSLATEGRAY);

        Polygon bow = new Polygon(
                0, 0,
                CELL_SIZE * 0.4, CELL_SIZE / 2.0,
                0, CELL_SIZE
        );
        bow.setFill(Color.DARKSLATEGRAY);

        Group group = new Group(body, bow);
        if (orientation == Orientation.VERTICAL) {
            group.setRotate(90);
        }
        return group;
    }

    public static Group createWaterMark() {
        Line line1 = new Line(6, 6, CELL_SIZE - 6, CELL_SIZE - 6);
        Line line2 = new Line(CELL_SIZE - 6, 6, 6, CELL_SIZE - 6);
        line1.setStroke(Color.CRIMSON);
        line2.setStroke(Color.CRIMSON);
        line1.setStrokeWidth(3);
        line2.setStrokeWidth(3);
        return new Group(line1, line2);
    }

    public static Group createHitMark() {
        Circle body = new Circle(CELL_SIZE / 2.0, CELL_SIZE / 2.0, CELL_SIZE / 4.0);
        body.setFill(Color.web("#2b2b2b"));

        Polygon spark = new Polygon(
                CELL_SIZE / 2.0, 4,
                CELL_SIZE / 2.0 + 6, CELL_SIZE / 2.0 - 6,
                CELL_SIZE / 2.0 - 6, CELL_SIZE / 2.0 - 6
        );
        spark.setFill(Color.ORANGE);

        return new Group(body, spark);
    }

    public static Group createSunkMark() {
        Circle body = new Circle(CELL_SIZE / 2.0, CELL_SIZE / 2.0, CELL_SIZE / 2.6);
        body.setFill(Color.web("#1a1a1a"));

        Polygon flame = new Polygon(
                CELL_SIZE / 2.0, 2,
                CELL_SIZE / 2.0 + 10, CELL_SIZE / 2.0,
                CELL_SIZE / 2.0, CELL_SIZE - 2,
                CELL_SIZE / 2.0 - 10, CELL_SIZE / 2.0
        );
        flame.setFill(Color.ORANGERED);

        return new Group(body, flame);
    }
}
