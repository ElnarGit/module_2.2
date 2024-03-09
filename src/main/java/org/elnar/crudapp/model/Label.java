package org.elnar.crudapp.model;

import lombok.*;
import org.elnar.crudapp.enums.LabelStatus;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class Label {
	private Long id;
	private String name;
	private LabelStatus labelStatus;
}