package org.elnar.crudapp.model;

import lombok.*;
import org.elnar.crudapp.enums.WriterStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Writer {
	private Long id;
	private String firstname;
	private String lastname;
	private List<Post> posts;
	private WriterStatus writerStatus;
}
