package com.vds.trainer.controller;

import com.vds.trainer.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 *  A controller layer of a micro service.
 *  Last ones are called when request handling starts happening.
 *  Requests come on correspond url that linked by RequestMapping annotation with an appropriate declared method below.
 **/
@RestController
public class TrainerController {

	@Autowired
	private TrainerService trainerService;


}
