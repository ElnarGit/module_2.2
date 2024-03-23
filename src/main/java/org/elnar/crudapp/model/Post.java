package org.elnar.crudapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elnar.crudapp.enums.PostStatus;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	private Long id;
	private String content;
	private Date created;
	private Date updated;
	private List<Label> labels;
	private PostStatus postStatus;
	private Writer writer;
}
