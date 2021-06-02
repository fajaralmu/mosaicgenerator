package com.fajar.imagemosaic.models;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class WebResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8029263602345176048L;
	private String message;
	@Default
	private String code = "00";
	private String imageData;
	@Default
	private Date date =new Date();
	
	private Double percentage;
}
