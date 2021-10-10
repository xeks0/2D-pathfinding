package org.xeks.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Edge {

    @Getter
    @Setter
    private Edge oppositeEdge;
}
