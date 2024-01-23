package org.edupoll.app.command;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WriteCommand {
	private String writer;
	private String password;
	private String title;
	private String body;
}
