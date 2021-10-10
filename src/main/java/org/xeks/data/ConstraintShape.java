package org.xeks.data;

import lombok.Getter;
import lombok.Setter;
import org.xeks.Vector;

public class ConstraintShape {

    public static int INC = 0;

    static {
        ConstraintShape.INC = 0;
    }

    public ConstraintShape() {
        staticConstructor(this);
    }

    public static void staticConstructor(ConstraintShape _this) {
        _this.id = ConstraintShape.INC;
        ConstraintShape.INC++;
        _this.setSegments(new Vector<>());
    }

    @Setter
    @Getter
    public Vector<ConstraintSegment> segments;

    @Getter
    @Setter
    public int id;

    public void dispose() {
        while ((this.segments.length > 0)) {
            this.segments.pop().dispose();
        }
        this.segments = null;
    }
}
