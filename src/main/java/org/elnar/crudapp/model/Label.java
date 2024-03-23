package org.elnar.crudapp.model;

import lombok.*;
import org.elnar.crudapp.enums.LabelStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Label {
	private Long id;
	private String name;
	private LabelStatus labelStatus;
}