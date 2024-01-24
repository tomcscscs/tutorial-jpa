package org.edupoll.app.command;

import lombok.Data;

@Data
public class ModifyCommand {
	private Integer targetId;
	private String modifyWriter;
	private String modifyPassword;
	private String modifyTitle;
	private String modifyBody;
	
}
