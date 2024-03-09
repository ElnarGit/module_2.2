package org.elnar.crudapp.model;

import lombok.*;
import org.elnar.crudapp.enums.PostStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Post {
	private Long id;
	private String content;
	private Date created;
	private Date updated;
	private List<Label> labels;
	private PostStatus postStatus;
	private Writer writerId;
	
	public void addLabel(Label label){
		if (labels == null) {
			labels = new ArrayList<>();
		}
		labels.add(label);
	}
}
