package org.xeks.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode
public class Face {

    public static int INC = 0;

    public Face() {
        staticConstructor(this);
    }

    public static void staticConstructor(Face _this) {
        _this.id = Face.INC;
        Face.INC++;
        _this.colorDebug = -1;
    }

    @Getter
    @Setter
    public int id;

    @Setter
    @Getter
    public boolean isReal;

    @Setter
    @Getter
    public Edge edge;

    @Setter
    @Getter
    public int colorDebug;

    public void set_datas(Edge edge) {
        this.isReal = true;
        this.edge = edge;
    }

    public void setDatas(Edge edge, boolean isReal) {
        this.isReal = isReal;
        this.edge = edge;
    }

    public void dispose() {
        this.edge = null;
    }

    @Override
    public String toString() {
        return "Face{" +
                "id=" + id +
                ", isReal=" + isReal +
                '}';
    }
}
