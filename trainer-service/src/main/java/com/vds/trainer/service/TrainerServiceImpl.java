package com.vds.trainer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

import java.io.UnsupportedEncodingException;

/**
 *  Service layer implementation to work with Trainer entities.
 **/
@Service
public class TrainerServiceImpl implements TrainerService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public String getTensorflowVersion(){
		return TensorFlow.version();
	}

	public Graph prepareGraph() throws UnsupportedEncodingException {
		Graph graph = new Graph();
		// Construct the computation graph with a single operation, a constant
		// named "MyConst" with a value "value".
		try (Tensor<?> tensor = Tensor.create(getTensorflowVersion().getBytes(UTF_ENCODING_TYPE))) {
			// The Java API doesn't yet include convenience functions for adding operations.
			graph.opBuilder("Const", "MyConst")
					.setAttr("dtype", tensor.dataType())
					.setAttr("value", tensor)
					.build();
		}
		return graph;
	}

	public void runSession(Graph graph) throws UnsupportedEncodingException {
		Session session = new Session(graph);
		// Generally, there may be multiple output tensors,
		// all of them must be closed to prevent resource leaks.
		Tensor<?> output = session.runner()
				.fetch("MyConst")
				.run()
				.get(0);
		System.out.println(new String(output.bytesValue(), UTF_ENCODING_TYPE));
	}




}
