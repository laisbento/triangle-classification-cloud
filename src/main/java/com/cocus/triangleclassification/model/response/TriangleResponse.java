package com.cocus.triangleclassification.model.response;

import com.cocus.triangleclassification.model.TriangleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TriangleResponse {
    private TriangleType type;
}
